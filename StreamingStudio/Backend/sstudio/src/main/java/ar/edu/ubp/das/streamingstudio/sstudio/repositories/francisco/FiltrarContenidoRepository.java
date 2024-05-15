package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
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
public class FiltrarContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public ContenidoBean buscarContenidoPorFiltros(int id_cliente, String titulo, boolean reciente, boolean destacado, String clasificacion, boolean masVisto, String genero) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("titulo", titulo)
                .addValue("reciente", reciente)
                .addValue("destacado", destacado)
                .addValue("clasificacion", clasificacion)
                .addValue("masVisto", masVisto)
                .addValue("genero", genero);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Contenido_por_Filtros")
                .withSchemaName("dbo")
                .returningResultSet("usuario", BeanPropertyRowMapper.newInstance(ContenidoBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoBean> lista_usuario = (List<ContenidoBean>) out.get("usuario");
        return lista_usuario.getFirst();
    }
}
