package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public void autorizarCliente(int id_cliente, String token) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("token", token);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Autorizar_Cliente")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }
}
