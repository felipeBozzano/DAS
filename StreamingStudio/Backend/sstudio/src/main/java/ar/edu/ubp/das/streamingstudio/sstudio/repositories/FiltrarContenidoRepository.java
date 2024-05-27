package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.InformacionContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PeliculaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.SerieBean;
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
public class FiltrarContenidoRepository implements IFiltrarContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public List<ContenidoHomeBean> buscarContenidoPorFiltros(int id_cliente, @Nullable String titulo, boolean reciente, boolean destacado, @Nullable String clasificacion, boolean masVisto, @Nullable String genero) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("reciente", reciente)
                .addValue("destacado", destacado)
                .addValue("mas_visto", masVisto);

        if (titulo != null && !titulo.isEmpty())
            ((MapSqlParameterSource) in).addValue("titulo", titulo);
        else
            ((MapSqlParameterSource) in).addValue("titulo", null);

        if (clasificacion != null && !clasificacion.isEmpty())
            ((MapSqlParameterSource) in).addValue("clasificacion", clasificacion);
        else
            ((MapSqlParameterSource) in).addValue("clasificacion", null);

        if (genero != null && !genero.isEmpty())
            ((MapSqlParameterSource) in).addValue("genero", genero);
        else
            ((MapSqlParameterSource) in).addValue("genero", null);

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
    public InformacionContenidoBean informacionContenido(String id_contenido, int id_cliente) {
        Map<String, String> infoContenido = obtenerInformacionContenido(id_contenido);
        Map<String, String> genero = obtenerGenero(id_contenido);
        List<Map<String, String>> directores = obtenerDirectores(id_contenido);
        List<Map<String, String>> actores = obtenerActores(id_contenido);
        List<Map<String, String>> plataformas = obtenerInformacionPlataformas(id_contenido, id_cliente);

        InformacionContenidoBean contenidoInfo = new InformacionContenidoBean();
        contenidoInfo.setInfoContenido(infoContenido);
        contenidoInfo.setGenero(genero);
        contenidoInfo.setDirectores(directores);
        contenidoInfo.setActores(actores);
        contenidoInfo.setPlataformas(plataformas);

        return contenidoInfo;
    }

    @Override
    public Map<String, String> obtenerInformacionContenido(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Contenido")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        Map<String, String> informacionContenido = resulset.get(0);
        return informacionContenido;
    }

    @Override
    public Map<String, String> obtenerGenero(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Generos")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        Map<String, String> genero = resulset.get(0);
        return genero;
    }

    @Override
    public List<Map<String, String>> obtenerDirectores(String id_contenido) {
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
    public List<Map<String, String>> obtenerActores(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Actores")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        List<Map<String, String>> actores = resulset;
        return actores;
    }

    @Override
    public List<Map<String, String>> obtenerInformacionPlataformas(String id_contenido, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Plataforma")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resulset = (List<Map<String, String>>) out.get("#result-set-1");
        for (Map<String, String> set : resulset) {
            set.put("id_plataforma", String.valueOf(set.get("id_plataforma")));
        }
        List<Map<String, String>> plataformas = resulset;
        return plataformas;
    }

    public List<SerieBean> obtenerSeries() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Series")
                .withSchemaName("dbo")
                .returningResultSet("series", BeanPropertyRowMapper.newInstance(SerieBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<SerieBean> series = (List<SerieBean>) out.get("series");
        return series;
    }

    public List<PeliculaBean> obtenerPeliculas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Peliculas")
                .withSchemaName("dbo")
                .returningResultSet("peliculas", BeanPropertyRowMapper.newInstance(PeliculaBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<PeliculaBean> peliculas = (List<PeliculaBean>) out.get("peliculas");
        return peliculas;
    }
}
