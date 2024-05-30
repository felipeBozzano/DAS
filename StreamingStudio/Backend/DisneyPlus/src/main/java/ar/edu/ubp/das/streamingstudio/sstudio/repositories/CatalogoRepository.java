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

    public List<CatalogoBean> obtenerCatalogo() {
        List<CatalogoBean> contenidos = obtenerContenido();

        for (CatalogoBean contenido : contenidos) {
            // Obtener los directores asociados al contenido y añadirlos al bean
            List<DirectorBean> directores = obtenerDirectores(contenido.getId_contenido());
            for (DirectorBean director : directores) {
                contenido.setDirectores(director);
            }
            // Obtener los actores asociados al contenido y añadirlos al bean
            List<ActorBean> actores = obtenerActores(contenido.getId_contenido());
            for (ActorBean actor : actores) {
                contenido.setActores(actor);
            }
        }

        return contenidos;
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
