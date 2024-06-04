package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class CatalogoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public CatalogoBean obtenerCatalogo() {
        List<ContenidoBean> contenidos = obtenerContenido();

        for (ContenidoBean contenido : contenidos) {
            // Obtener los directores asociados al contenido y añadirlos al bean
            List<DirectorBean> directores = obtenerDirectores(contenido.getId_contenido());
            contenido.setDirectores(directores);

            // Obtener los actores asociados al contenido y añadirlos al bean
            List<ActorBean> actores = obtenerActores(contenido.getId_contenido());
            contenido.setActores(actores);

            // Obtener los generos asociados al contenido y añadirlos al bean
            List<GeneroBean> generos = obtenerGeneros(contenido.getId_contenido());
            contenido.setGeneros(generos);
        }

        CatalogoBean catalogo = new CatalogoBean();
        if (contenidos.isEmpty()) {
            catalogo.setCodigoRespuesta("1");
            catalogo.setMensajeRespuesta("Catalogo vacío");
        } else {
            catalogo.setListaContenido(contenidos);
            catalogo.setCodigoRespuesta("200");
            catalogo.setMensajeRespuesta("Catalogo recibido");
        }
        return catalogo;
    }

    public List<ContenidoBean> obtenerContenido() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl).withProcedureName("Obtener_Contenido_Actual").withSchemaName("dbo").returningResultSet("contenido", BeanPropertyRowMapper.newInstance(ContenidoBean.class));
        ;
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoBean> contenido = (List<ContenidoBean>) out.get("contenido");
        return contenido;
    }

    public List<DirectorBean> obtenerDirectores(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Directores")
                .withSchemaName("dbo")
                .returningResultSet("directores", BeanPropertyRowMapper.newInstance(DirectorBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<DirectorBean> directores = (List<DirectorBean>) out.get("directores");
        return directores;
    }

    public List<ActorBean> obtenerActores(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Actores")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Object>> resultset = (List<Map<String, Object>>) out.get("#result-set-1");
        List<ActorBean> actores = new LinkedList<>();
        for(Map<String, Object> actor: resultset) {
            int id_actor = (int) actor.get("id_actor");
            String nombre = (String) actor.get("nombre");
            String apellido = (String) actor.get("apellido");
            ActorBean newActor = new ActorBean(id_actor, nombre, apellido);
            actores.add(newActor);
        }
        return actores;
    }

    public List<GeneroBean> obtenerGeneros(String id_contenido) {
        SqlParameterSource in = new MapSqlParameterSource().
                addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Generos")
                .withSchemaName("dbo")
                .returningResultSet("generos", BeanPropertyRowMapper.newInstance(GeneroBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<GeneroBean> generos = (List<GeneroBean>) out.get("generos");
        return generos;
    }
}
