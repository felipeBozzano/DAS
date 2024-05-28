package ar.edu.ubp.das.streamingstudio.publicistaubp.repositories;

import ar.edu.ubp.das.streamingstudio.publicistaubp.models.EstadisticasBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EstadisticasRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public Map<String, String> registrarReporteEstadisticas(EstadisticasBean estadisticas) {
        Map<String, String> respuesta;
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("total", estadisticas.getTotal())
                .addValue("descripcion", estadisticas.getDescripcion());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Transaccion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        respuesta = new HashMap<>();
        respuesta.put("mensaje", "OK");
        return respuesta;
    }
}
