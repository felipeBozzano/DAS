package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.*;

public class FederacionesPendientes {
    static JdbcTemplate jdbcTpl;
    static Map<String,String> respuesta;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void terminarFederacionesPendientes() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<TransaccionBean> federacionesPendientes = consultarFederacionesPendientes();
        for (TransaccionBean federacionPendiente: federacionesPendientes) {
            if (federacionPendiente.getUrl_token() == null)
                continue;

            int id_plataforma = federacionPendiente.getId_plataforma();
            int id_cliente = federacionPendiente.getId_cliente();
            String url_token = federacionPendiente.getUrl_token();
            String codigo_de_transaccion = federacionPendiente.getCodigo_de_transaccion();
            respuesta = finalizarFederacion(id_plataforma, id_cliente, codigo_de_transaccion, url_token);

            imprimirMapa(respuesta);
            System.out.println("-------------------------------------------");
        }
    }

    public static List<TransaccionBean> consultarFederacionesPendientes() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Consultar_Federaciones_Pendientes")
                .withSchemaName("dbo")
                .returningResultSet("transacciones", BeanPropertyRowMapper.newInstance(TransaccionBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<TransaccionBean>) out.get("transacciones");
    }

    public static Map<String, String> finalizarFederacion(int id_plataforma, int id_cliente, String codigo_de_transaccion,
                                                          String url_token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma, jdbcTpl);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();

        if (conexion_plataforma.get("protocolo_api").equals("REST")) {
            body.put("codigo_de_transaccion", codigo_de_transaccion);
            body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
        } else {
            String message = """
                    <ws:obtenerPublicidades xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                    <token_de_partner>%s</token_de_partner>
                    </ws:obtenerPublicidades>""".formatted(conexion_plataforma.get("token_de_servicio"), codigo_de_transaccion);
            body.put("message", message);
            body.put("web_service", "obtenerPublicidades");
        }
        FederacionBean bean = (FederacionBean) conector.execute_post_request(url_token, body, "FederacionBean");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
//                .addValue("token", bean.getToken());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Federacion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        respuesta.put("Mensaje", "Federacion finalizada correctamente");
        respuesta.put("Cliente", String.valueOf(id_cliente));
        respuesta.put("Plataforma", String.valueOf(id_plataforma));
        respuesta.put("Codigo_de_Transaccion", codigo_de_transaccion);
        return respuesta;
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        jdbcTpl = crearJdbcTemplate();
        respuesta = new HashMap<>();
        terminarFederacionesPendientes();
    }
}
