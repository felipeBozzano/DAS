package ar.edu.ubp.das.streamingstudio.sstudio.utils;

import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.FederarClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FederacionesPendientes {
    static JdbcTemplate jdbcTpl;
    static Map<String,String> respuesta;
    private static final FederarClienteRepository federarCliente = new FederarClienteRepository();

    public static String terminarFederacionesPendientes() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        respuesta = new HashMap<>();
        List<TransaccionBean> federacionesPendientes = consultarFederacionesPendientes();
        for (TransaccionBean federacionPendiente: federacionesPendientes) {
            if (federacionPendiente.getUrl_token() == null)
                continue;
            int id_plataforma = federacionPendiente.getId_plataforma();
            int id_cliente = federacionPendiente.getId_cliente();
            String codigo_de_transaccion = federacionPendiente.getCodigo_de_transaccion();
            respuesta = federarCliente.finalizarFederacion(id_plataforma, id_cliente, codigo_de_transaccion, "-1", false);
        }
        System.out.println(respuesta);
        return "OK";
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

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        terminarFederacionesPendientes();
    }
}
