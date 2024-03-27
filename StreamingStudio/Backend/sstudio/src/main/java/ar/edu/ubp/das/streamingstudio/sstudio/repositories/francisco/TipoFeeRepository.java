package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.ubp.das.rest.localidades.beans.LocalidadBean;
import ar.edu.ubp.das.rest.localidades.beans.LocalidadNuevaBean;
import ar.edu.ubp.das.rest.localidades.beans.PaisBean;
import ar.edu.ubp.das.rest.localidades.beans.ProvinciaBean;

public class TipoFeeRepository {

    @Override
    @Transactional
    public int insLocalidad(LocalidadNuevaBean localidad) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("cod_pais", localidad.getCodPais())
                .addValue("cod_provincia", localidad.getCodProvincia())
                .addValue("nom_localidad", localidad.getNomLocalidad())
                .addValue("nro_localidad", null, Types.INTEGER);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("ins_localidad")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        return Integer.valueOf(out.get("nro_localidad").toString());
    }
}
