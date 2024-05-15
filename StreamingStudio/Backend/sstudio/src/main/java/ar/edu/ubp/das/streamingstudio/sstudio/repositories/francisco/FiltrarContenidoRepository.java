package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FiltrarContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public List<ContenidoBean> buscarContenidoPorFiltros(int id_cliente, String titulo, @Nullable boolean reciente, @Nullable boolean destacado, @Nullable String clasificacion, @Nullable boolean masVisto, @Nullable String genero) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        if (titulo != null && !titulo.isEmpty()) {
            ((MapSqlParameterSource) in).addValue("titulo", titulo);
        }

        ((MapSqlParameterSource) in).addValue("reciente", reciente);
        ((MapSqlParameterSource) in).addValue("destacado", destacado);

        if (clasificacion != null && !clasificacion.isEmpty()) {
            ((MapSqlParameterSource) in).addValue("clasificacion", clasificacion);
        }
            ((MapSqlParameterSource) in).addValue("mas_visto", masVisto);
        if (genero != null && !genero.isEmpty()) {
            ((MapSqlParameterSource) in).addValue("genero", genero);
        }
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Contenido_por_Filtros")
                .withSchemaName("dbo")
                .returningResultSet("contenido", BeanPropertyRowMapper.newInstance(ContenidoBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoBean> contenido = (List<ContenidoBean>) out.get("contenido");
        if (contenido == null) {
            contenido = new ArrayList<>();
            contenido.add(new ContenidoBean());
        }
        return contenido;
    }
}
