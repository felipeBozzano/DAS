package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class TipoFeeRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public Map<String, Object> getTipoFee(int tipo_de_fee) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_tipo_de_fee", tipo_de_fee);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Tipo_Fee")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        return out;
    }
}
