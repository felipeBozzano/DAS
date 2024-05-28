package ar.edu.ubp.das.streamingstudio.publicistaubp.repositories;

import ar.edu.ubp.das.streamingstudio.publicistaubp.models.PublicidadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PublicidadesRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public List<PublicidadBean> exponerPublicidades() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Transaccion")
                .withSchemaName("dbo")
                .returningResultSet("publicidades", BeanPropertyRowMapper.newInstance(PublicidadBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<PublicidadBean> publicidades = (List<PublicidadBean>) out.get("publicidades");
        if (publicidades.isEmpty())
            publicidades.add(new PublicidadBean());
        return publicidades;
    }
}
