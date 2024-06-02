package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.ContenidoUrlBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.SesionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReproducirContenidoRepository implements IReproducirContenidoRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;
    private final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    @Override
    public ContenidoUrlBean obtener_url_de_contenido(String id_contenido, int id_plataforma, int id_cliente) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String token_de_usuario = obtenerTokenDeFederacion(id_cliente, id_plataforma);
        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));

        SesionBean sesion = obtenerSesion(conexion_plataforma, token_de_usuario, conector);
        ContenidoUrlBean contenido = obtenerUrlDeContenido(conexion_plataforma, sesion.getSesion(), id_contenido, conector);

        return contenido;
    }

    @Override
    public String obtenerTokenDeFederacion(int id_cliente, int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_token")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> lista_mapa = (List<Map<String, String>>) out.get("#result-set-1");
        String token = lista_mapa.getFirst().get("token");

        return token;
    }

    @Override
    public Map<String, String> obtenerInformacionDeConexionAPlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Conexion_a_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> lista_mapa = (List<Map<String, String>>) out.get("#result-set-1");
        Map<String, String> info_plataforma = lista_mapa.getFirst();

        return info_plataforma;
    }

    @Override
    public SesionBean obtenerSesion(Map<String, String> conexion_plataforma, String token_de_usuario, AbstractConnector conector) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, String> body = new HashMap<>();

        body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
        body.put("token_de_usuario", token_de_usuario);
        SesionBean sesion = (SesionBean) conector.execute_post_request(conexion_plataforma.get("url_api") + "/crear_sesion", body, "SesionBean");

        return sesion;
    }

    @Override
    public ContenidoUrlBean obtenerUrlDeContenido(Map<String, String> conexion_plataforma, String token_de_sesion, String id_contenido, AbstractConnector conector) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, String> body = new HashMap<>();

        body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
        body.put("token_de_sesion", token_de_sesion);
        body.put("id_contenido", id_contenido);
        ContenidoUrlBean contenidoUrl = (ContenidoUrlBean) conector.execute_post_request(conexion_plataforma.get("url_api") + "/obtener_url_de_contenido", body, "ContenidoUrlBean");

        return contenidoUrl;
    }
}
