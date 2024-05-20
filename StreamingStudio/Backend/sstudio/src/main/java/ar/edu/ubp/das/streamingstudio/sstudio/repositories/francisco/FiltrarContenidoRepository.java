package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.InformacionContenido;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FiltrarContenidoRepository implements IFiltrarContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public List<ContenidoHomeBean> buscarContenidoPorFiltros(int id_cliente, String titulo, @Nullable boolean reciente, @Nullable boolean destacado, @Nullable String clasificacion, @Nullable boolean masVisto, @Nullable String genero) {
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
                .returningResultSet("contenido", BeanPropertyRowMapper.newInstance(ContenidoHomeBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoHomeBean> contenido = (List<ContenidoHomeBean>) out.get("contenido");
        if (contenido == null) {
            contenido = new ArrayList<>();
            contenido.add(new ContenidoHomeBean());
        }
        return contenido;
    }

    @Override
    public InformacionContenido informacionContenido(String id_contenido, int id_cliente) {
        Map<String,String> infoContenido = obtenerInformacionContenido(id_contenido);
        Map<String,String> genero = obtenerGenero(id_contenido);
        List<Map<String,String>> directores = obtenerDirectores(id_contenido);
        List<Map<String,String>> actores = obtenerActores(id_contenido);
        List<Map<String,String>> plataformas = obtenerInformacionPlataformas(id_contenido, id_cliente);

        InformacionContenido contenidoInfo = new InformacionContenido();
        contenidoInfo.setInfoContenido(infoContenido);
        contenidoInfo.setGenero(genero);
        contenidoInfo.setDirectores(directores);
        contenidoInfo.setActores(actores);
        contenidoInfo.setPlataformas(plataformas);

        return contenidoInfo;
    }

    @Override
    public Map<String,String> obtenerInformacionContenido(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido" , id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Contenido")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        Map<String, String> informacionContenido = resulset.get(0);
        return informacionContenido;
    }

    @Override
    public Map<String,String> obtenerGenero(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido" , id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Generos")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        Map<String, String> genero = resulset.get(0);
        return genero;
    }

    @Override
    public List<Map<String,String>> obtenerDirectores(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Directores")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        List<Map<String, String>> directores = resulset;
        return directores;
    }

    @Override
    public List<Map<String,String>> obtenerActores(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido" , id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Actores")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        List<Map<String, String>> actores = resulset;
        return actores;
    }

    @Override
    public List<Map<String,String>> obtenerInformacionPlataformas(String id_contenido, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido" , id_contenido)
                .addValue("id_cliente" , id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Plataforma")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        List<Map<String, String>> plataformas = resulset;
        return plataformas;
    }
}
