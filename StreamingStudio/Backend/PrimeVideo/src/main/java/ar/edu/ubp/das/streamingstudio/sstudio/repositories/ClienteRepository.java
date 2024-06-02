package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.AutorizacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

//    @Transactional
//    public void crearTransaccion(String codigo_de_transaccion, String url_de_redireccion, String tipo_de_transaccion) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("codigo_de_transaccion", codigo_de_transaccion)
//                .addValue("url_de_redireccion", url_de_redireccion)
//                .addValue("tipo_de_transaccion", tipo_de_transaccion);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Crear_Transaccion")
//                .withSchemaName("dbo");
//        jdbcCall.execute(in);
//    }
//
//    @Transactional
//    public void crearAutorizacion(int id_cliente, String codigo_de_transaccion, String token) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_cliente", id_cliente)
//                .addValue("codigo_de_transaccion", codigo_de_transaccion)
//                .addValue("token", token);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Crear_Autorizacion")
//                .withSchemaName("dbo");
//        jdbcCall.execute(in);
//    }
//
//    public String obtenerUrlDeRedireccion(String codigo_de_transaccion) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("codigo_de_transaccion", codigo_de_transaccion);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Obtener_codigo_de_redireccion")
//                .withSchemaName("dbo");
//        Map<String, Object> out = jdbcCall.execute(in);
//        List<Map<String,String>> lista_url_de_redireccion = (List<Map<String,String>>) out.get("#result-set-1");
//        String url_de_redireccion = lista_url_de_redireccion.getFirst().get("url_de_redireccion");
//        return url_de_redireccion;
//    }
//
//    public String obtenerToken(int id_cliente, String codigo_de_transaccion) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_cliente", id_cliente)
//                .addValue("codigo_de_transaccion", codigo_de_transaccion);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Obtener_Token")
//                .withSchemaName("dbo");
//        Map<String, Object> out = jdbcCall.execute(in);
//        List<Map<String,String>> mapa_token = (List<Map<String,String>>) out.get("#result-set-1");
//        String token = mapa_token.getFirst().get("token");
//        return token;
//    }
//
//    public AutorizacionBean verificarAutorizacion(String codigo_de_transaccion) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("codigo_de_transaccion", codigo_de_transaccion);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Verificar_Autorizacion")
//                .withSchemaName("dbo")
//                .returningResultSet("autorizacion", BeanPropertyRowMapper.newInstance(AutorizacionBean.class));
//        Map<String, Object> out = jdbcCall.execute(in);
//        List<AutorizacionBean> autorizaciones = (List<AutorizacionBean>) out.get("autorizacion");
//        if (autorizaciones.isEmpty())
//            autorizaciones.add(new AutorizacionBean());
//        return autorizaciones.getFirst();
//    }

    @Transactional
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("usuario", cliente.getUsuario())
                .addValue("contrasena", cliente.getcontrasena())
                .addValue("email", cliente.getEmail())
                .addValue("nombre", cliente.getNombre())
                .addValue("apellido", cliente.getApellido())
                .addValue("valido", true);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);

        List<ClienteUsuarioBean> usuario = (List<ClienteUsuarioBean>)out.get("Crear_Usuario");
        return usuario;
    }

    public int verificarUsuario(String email, String contrasena) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("contrasena", contrasena);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Login_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);

        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        int resultado = resulset.getFirst().get("ExisteUsuario");
        return resultado;
    }
    public Map<String, Integer> informacion_usuario(String email, String contrasena) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("contrasena", contrasena);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Informacion_Usuario")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Map<String, Integer> resultado = resulset.get(0);
        return resultado;
    }

}
