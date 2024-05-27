package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.AutorizacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.VerificacionTransaccionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class AutorizacionRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    private Map<String, String> respuesta;

    @Transactional
    public VerificacionTransaccionBean crearTransaccion(String tipo_de_transaccion, String url_redireccion_ss) {
//        respuesta = new HashMap<>();

        // Crear codigo de transacción y url para redireccionar
        UUID codigo_de_transaccion = UUID.randomUUID();
        String codigo_de_transaccion_string = codigo_de_transaccion.toString();
        String url_de_redireccion;
        if (tipo_de_transaccion.equals("L"))
            url_de_redireccion = "http://localhost:4201/login";
        else
            url_de_redireccion = "http://localhost:4201/register";

        // Crear transacción
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("codigo_de_transaccion", codigo_de_transaccion_string)
                .addValue("url_de_redireccion", url_redireccion_ss)
                .addValue("tipo_de_transaccion", tipo_de_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Transaccion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        // Crear y devolver respuesta
        VerificacionTransaccionBean respuesta = new VerificacionTransaccionBean(true, codigo_de_transaccion_string, url_de_redireccion);
//        respuesta.put("codigoTransaccion", codigo_de_transaccion_string);
//        respuesta.put("url", url_de_redireccion);
        return respuesta;
    }

    public AutorizacionBean verificarAutorizacion(String codigo_de_transaccion) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("codigo_de_transaccion", codigo_de_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Autorizacion")
                .withSchemaName("dbo")
                .returningResultSet("autorizacion", BeanPropertyRowMapper.newInstance(AutorizacionBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<AutorizacionBean> autorizaciones = (List<AutorizacionBean>) out.get("autorizacion");
        if (autorizaciones.isEmpty())
            autorizaciones.add(new AutorizacionBean());
        return autorizaciones.getFirst();
    }

    @Transactional
    public void crearAutorizacion(int id_cliente, String codigo_de_transaccion) {


        UUID token = UUID.randomUUID();
        String token_string = token.toString();

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("codigo_de_transaccion", codigo_de_transaccion)
                .addValue("token", token);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Autorizacion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public String obtenerUrlDeRedireccion(String codigo_de_transaccion) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("codigo_de_transaccion", codigo_de_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_codigo_de_redireccion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> lista_url_de_redireccion = (List<Map<String,String>>) out.get("#result-set-1");
        String url_de_redireccion = lista_url_de_redireccion.getFirst().get("url_de_redireccion");
        return url_de_redireccion;
    }

    public String obtenerToken(String codigo_de_transaccion) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("codigo_de_transaccion", codigo_de_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> mapa_token = (List<Map<String,String>>) out.get("#result-set-1");
        String token = mapa_token.getFirst().get("token");
        return token;
    }
}
