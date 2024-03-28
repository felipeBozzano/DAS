package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
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
@Repository
public class TipoFeeRepository implements ITipoFeeRepository{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    @Transactional
    public List<Tipo_de_Fee> getTipoFee(int tipo_de_fee) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_tipo_de_fee", tipo_de_fee);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Tipo_Fee")
                .withSchemaName("dbo")
                .returningResultSet("tipos_de_fee", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<Tipo_de_Fee>)out.get("tipos_de_fee");
    }
}
