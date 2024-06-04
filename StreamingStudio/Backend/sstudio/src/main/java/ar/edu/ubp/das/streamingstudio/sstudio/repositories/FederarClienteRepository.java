package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.FederacionDesvinculada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FederarClienteRepository implements IFederarClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;
    private final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();
    private Map<String, String> respuesta;

    @Override
    @Transactional
    public Map<String, String> federarClientePlataforma(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(buscarFederacion(id_plataforma, id_cliente)){
            respuesta = new HashMap<>();
            respuesta.put("mensaje", "El cliente ya esta federado");
        } else {
            Map<String, String> transaccion = verificarFederacionCurso(id_plataforma, id_cliente);
            if (!transaccion.containsKey("codigo_de_transaccion")) {
                respuesta = comenzarFederacion(id_plataforma, id_cliente, tipo_transaccion);
            }
            else {
                respuesta = transaccion;
                respuesta.put("mensaje", "Redirigir al cliente a la plataforma de Streaming");
            }
        }
        return respuesta;
    }

    @Override
    public boolean buscarFederacion(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Federacion")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer federacion = resulset.getFirst().get("federacion");

        return federacion == 1;
    }

    @Override
    public Map<String, String> verificarFederacionCurso(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Federacion_en_Curso")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> resultado = (List<Map<String,String>>) out.get("#result-set-1");
        if (resultado.isEmpty()) {
            resultado.add(new HashMap<>());
        }

        return resultado.getFirst();
    }

    @Override
    public Map<String, String> obtenerInformacionDeConexionAPlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Conexion_a_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> lista_mapa = (List<Map<String,String>>) out.get("#result-set-1");
        Map<String, String> info_plataforma = lista_mapa.getFirst();
        return info_plataforma;
    }

    @Override
    @Transactional
    public Map<String, String> comenzarFederacion(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        respuesta = new HashMap<>();
        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();

        String conexion = conexion_plataforma.get("url_api");
        String url_de_redireccion = "http://localhost:4200/usuario/" + id_cliente + "/finalizar_federacion/" + id_plataforma;

        if (conexion_plataforma.get("protocolo_api").equals("SOAP")) {
            String message = """
                    <ws:crearTransaccion xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                    <tipo_de_transaccion>%s</tipo_de_transaccion>
                    <url_redireccion_ss>%s</url_redireccion_ss>
                    <token_de_partner>%s</token_de_partner>
                    </ws:crearTransaccion>""".formatted(tipo_transaccion, url_de_redireccion, conexion_plataforma.get("token_de_servicio"));
            body.put("message", message);
            body.put("web_service", "crearTransaccion");
        }else{
            body.put("url_de_redireccion", url_de_redireccion);
            body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
            body.put("tipo_de_transaccion", tipo_transaccion);
        }
        FederacionBean bean = (FederacionBean) conector.execute_post_request(conexion + "/obtener_codigo_de_transaccion", body, "FederacionBean");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("codigo_de_transaccion", bean.getCodigo_de_transaccion())
                .addValue("url_login_registro_plataforma", bean.getUrl())
                .addValue("url_redireccion_propia", url_de_redireccion)
                .addValue("tipo_transaccion", tipo_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Comenzar_Federacion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        respuesta.put("mensaje", "Federacion comenzada");
        respuesta.put("url_login_registro_plataforma", bean.getUrl());
        respuesta.put("codigo_de_transaccion", bean.getCodigo_de_transaccion());
        return respuesta;
    }

    @Override
    @Transactional
    public Map<String, String> finalizarFederacion(int id_plataforma, int id_cliente, String codigo_de_transaccion,
                                                   boolean actualizar_info) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma);
        String url_token = conexion_plataforma.get("url_api") + "/obtener_token?codigo_de_transaccion=" + codigo_de_transaccion;

        if (actualizar_info)
            actualizarUrlToken(id_plataforma, id_cliente, codigo_de_transaccion, url_token);

        respuesta = new HashMap<>();
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();

        if (conexion_plataforma.get("protocolo_api").equals("SOAP")) {
            String message = """
                    <ws:obtenerToken xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                        <codigo_de_transaccion>%s</codigo_de_transaccion>
                        <token_de_partner>%s</token_de_partner>
                    </ws:obtenerToken>""".formatted(codigo_de_transaccion, conexion_plataforma.get("token_de_servicio"));
            body.put("message", message);
            body.put("web_service", "obtenerToken");
        }else{
            body.put("codigo_de_transaccion", codigo_de_transaccion);
            body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
        }
        FederacionBean bean = (FederacionBean) conector.execute_post_request(url_token, body, "FederacionBean");
        System.out.println(bean.getToken());
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("token", bean.getToken());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Federacion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        respuesta.put("mensaje", "Federacion finalizada");
        return respuesta;
    }

    @Override
    public void actualizarUrlToken(int id_plataforma, int id_cliente, String codigo_de_transaccion, String url_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("id_plataforma", id_plataforma)
                .addValue("codigo_de_transaccion", codigo_de_transaccion)
                .addValue("url_token", url_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("actualizarUrlToken")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }


    public Map<String, String> desvincular(int id_plataforma ,int id_cliente) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();
        String url = conexion_plataforma.get("url_api") + "/desvincular";

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_token")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> token_de_servicio = (List<Map<String,String>>) out.get("#result-set-1");
        Map<String, String> token_map = token_de_servicio.getFirst();
        String token = token_map.get("token");

        if (conexion_plataforma.get("protocolo_api").equals("SOAP")) {
            String message = """
                    <ws:desvincular xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                        <token_de_servicio>%s</token_de_servicio>
                    </ws:desvincular>""".formatted(token);
            body.put("message", message);
            body.put("web_service", "desvincular");
        }else{
            body.put("token", "");
        }
        FederacionDesvinculada bean = (FederacionDesvinculada) conector.execute_post_request(url, body, "FederacionDesvinculada");

        in = new MapSqlParameterSource()
            .addValue("token", token);
            jdbcCall = new SimpleJdbcCall(jdbcTpl)
            .withProcedureName("desvincular")
            .withSchemaName("dbo");
        jdbcCall.execute(in);
        return body;
    }
}
