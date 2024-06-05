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

    @Transactional
    public Boolean createUser(ClienteUsuarioBean cliente) {
        try {
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

            jdbcCall.execute(in);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        Map<String, Integer> resultado = resulset.getFirst();
        return resultado;
    }

}
