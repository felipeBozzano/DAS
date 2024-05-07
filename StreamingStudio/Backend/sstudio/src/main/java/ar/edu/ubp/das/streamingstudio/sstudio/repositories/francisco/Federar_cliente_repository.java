package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.FederacionBean;
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
public class Federar_cliente_repository {

    @Autowired
    private JdbcTemplate jdbcTpl;
    private final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();
    private Map<String, String> respuesta;

    @Transactional
    public Map<String, String> federarClientePlataforma(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(buscarFederacion(id_plataforma, id_cliente)){
            respuesta = new HashMap<>();
            respuesta.put("mensaje", "El cliente ya esta federado");
        } else {
            String codigo_de_transaccion = VerificarFederacionCurso(id_plataforma, id_cliente);
            if (codigo_de_transaccion != null) {
                respuesta = new HashMap<>();
                respuesta.put("mensaje", "Federacion en curso");
                respuesta.put("codigo_de_transaccion", codigo_de_transaccion);
                respuesta.put("url_redireccion", "https://localhost:8080/ss/finalizar_federacion");
            }
            else {
                respuesta = comenzarFederacion(id_plataforma, id_cliente, tipo_transaccion);
            }
        }
        return respuesta;
    }

    @Transactional
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

    @Transactional
    public String VerificarFederacionCurso(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Federacion_en_Curso")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        if (resulset.isEmpty())
            return null;
        else
            return String.valueOf(resulset.getFirst().get("codigo_de_transaccion"));
    }

    @Transactional
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> mapa_token = (List<Map<String,String>>) out.get("#result-set-1");
        String token = mapa_token.getFirst().get("token_de_servicio");
        return token;
    }

    @Transactional
    public Map<String, String> comenzarFederacion(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        respuesta = new HashMap<>();
        AbstractConnector conector = conectorFactory.crearConector("REST");
        Map<String, String> body = new HashMap<>();
        body.put("url", "https://localhost:8080/ss/usuario/{id_cliente}/terminar_federacion/{id_plataforma}");
        body.put("token_de_servicio", obtenerTokenDeServicioDePlataforma(id_plataforma));
        body.put("id_cliente", String.valueOf(id_cliente));
        FederacionBean bean = (FederacionBean) conector.execute_post_request("http://localhost:8081/netflix/federar", body, "FederacionBean");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("codigo_de_transaccion", bean.getCodigoTransaccion())
                .addValue("url_login_registro_plataforma", bean.getUrl())
                .addValue("url_redireccion_propia", "https://localhost:8080/ss/usuario/{id_cliente}/terminar_federacion/{id_plataforma}")
                .addValue("tipo_transaccion", tipo_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Comenzar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);

        respuesta.put("mensaje", "Federacion comenzada");
        respuesta.put("url_redireccion", bean.getUrl());
        respuesta.put("codigo_transaccion", bean.getCodigoTransaccion());
        return respuesta;
    }

    @Transactional
    public Map<String, String> finalizarFederacion(int id_plataforma, int id_cliente, String codigo_de_transaccion, int id_cliente_plataforma) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        respuesta = new HashMap<>();
        AbstractConnector conector = conectorFactory.crearConector("REST");
        Map<String, String> body = new HashMap<>();
        body.put("codigo_transaccion", codigo_de_transaccion);
        body.put("token_de_servicio", obtenerTokenDeServicioDePlataforma(id_plataforma));
        FederacionBean bean = (FederacionBean) conector.execute_post_request("http://localhost:8081/netflix/usuario/{id_cliente_plataforma}/obtener_token", body, "FederacionBean");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("token", bean.getToken());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);

        respuesta.put("mensaje", "Federacion finalizada");
        return respuesta;
    }
}