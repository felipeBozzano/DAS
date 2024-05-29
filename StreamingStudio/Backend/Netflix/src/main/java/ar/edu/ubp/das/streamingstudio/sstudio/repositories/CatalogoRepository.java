package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ActorBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.DirectorBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CatalogoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    private Map<String, String> respuesta;

    public List<Map<String, Object>> obtenerCatalogo() {
        List<Map<String, Object>> respuesta = new ArrayList();
        List<CatalogoBean> contenidos = obtenerContenido();

        for (CatalogoBean contenido : contenidos) {
            // Crear un mapa para almacenar la información del contenido
            Map<String, Object> contenidoMap = new HashMap();
            contenidoMap.put("id_contenido", contenido.getId_contenido());
            contenidoMap.put("titulo", contenido.getTitulo());
            contenidoMap.put("descripcion", contenido.getDescripcion());
            contenidoMap.put("url_imagen", contenido.getUrl_imagen());
            contenidoMap.put("clasificacion", contenido.getClasificacion());
            contenidoMap.put("reciente", contenido.isReciente());
            contenidoMap.put("destacado", contenido.isDestacado());
            contenidoMap.put("valido", contenido.isValido());

            // Obtener los directores asociados al contenido y añadirlos al mapa
            List<DirectorBean> directores = obtenerDirectores(contenido.getId_contenido());
            List<Map<String, Object>> directoresList = new ArrayList<>();
            for (DirectorBean director : directores) {
                Map<String, Object> directorMap = new HashMap<>();
                directorMap.put("id_director", director.getId_director());
                directorMap.put("nombre", director.getNombre());
                directorMap.put("apellido", director.getApellido());
                directoresList.add(directorMap);
            }
            contenidoMap.put("directores", directoresList);

            // Obtener los actores asociados al contenido y añadirlos al mapa
            List<ActorBean> actores = obtenerActores(contenido.getId_contenido());
            List<Map<String, Object>> actoresList = new ArrayList<>();
            for (ActorBean actor : actores) {
                Map<String, Object> actorMap = new HashMap<>();
                actorMap.put("nombre", actor.getNombre());
                actorMap.put("apellido", actor.getApellido());
                actorMap.put("id_actor", actor.getId_actor());
                actoresList.add(actorMap);
            }
            contenidoMap.put("actores", actoresList);

            // Añadir el mapa del contenido a la lista de respuesta
            respuesta.add(contenidoMap);
        }

        return respuesta;
    }


    public List<CatalogoBean> obtenerContenido(){
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_Actual")
                .withSchemaName("dbo")
                .returningResultSet("contenido", BeanPropertyRowMapper.newInstance(CatalogoBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<CatalogoBean> contenido = (List<CatalogoBean>)out.get("contenido");
        return contenido;
    }

    public List<DirectorBean> obtenerDirectores(int id_contenido){
        SqlParameterSource in = new MapSqlParameterSource()
            .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Directores")
                .withSchemaName("dbo")
                .returningResultSet("directores", BeanPropertyRowMapper.newInstance(DirectorBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<DirectorBean> contenido = (List<DirectorBean>)out.get("directores");
        return contenido;
    }

    public List<ActorBean> obtenerActores(int id_contenido){
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_contenido", id_contenido);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Actores")
                .withSchemaName("dbo")
                .returningResultSet("directores", BeanPropertyRowMapper.newInstance(ActorBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<ActorBean> contenido = (List<ActorBean>)out.get("directores");
        return contenido;
    }

}
