package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class HomeRepository implements IHomeRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public Map<String, Map<String, List<ContenidoHomeBean>>> getHome(int id_cliente) {
        List<ContenidoHomeBean> contenido_mas_visto = getMasVisto(id_cliente);
        List<ContenidoHomeBean> contenido_reciente = getReciente(id_cliente);
        List<ContenidoHomeBean> contenido_destacado = getDestacado(id_cliente);

        Map<String, List<ContenidoHomeBean>> contenido_mas_visto_agrupado = agruparContenido(contenido_mas_visto);
        Map<String, List<ContenidoHomeBean>> contenido_reciente_agrupado = agruparContenido(contenido_reciente);
        Map<String, List<ContenidoHomeBean>> contenido_destacado_agrupado = agruparContenido(contenido_destacado);

        Map<String, Map<String, List<ContenidoHomeBean>>> publicidades_contenido = new HashMap<>();
        publicidades_contenido.put("Mas_Visto", contenido_mas_visto_agrupado);
        publicidades_contenido.put("Reciente", contenido_reciente_agrupado);
        publicidades_contenido.put("Destacado", contenido_destacado_agrupado);

        return publicidades_contenido;
    }

    @Override
    public List<ContenidoHomeBean> getMasVisto(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_mas_Visto")
                .withSchemaName("dbo")
                .returningResultSet("contenido_mas_visto", BeanPropertyRowMapper.newInstance(ContenidoHomeBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoHomeBean> contenidoMasVisto = (List<ContenidoHomeBean>)out.get("contenido_mas_visto");
        return contenidoMasVisto;
    }

    @Override
    public List<ContenidoHomeBean> getReciente(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_Reciente")
                .withSchemaName("dbo")
                .returningResultSet("contenido_reciente", BeanPropertyRowMapper.newInstance(ContenidoHomeBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoHomeBean> contenidoReciente = (List<ContenidoHomeBean>) out.get("contenido_reciente");
        return contenidoReciente;
    }

    @Override
    public List<ContenidoHomeBean> getDestacado(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_Destacado")
                .withSchemaName("dbo")
                .returningResultSet("contenido_destacado", BeanPropertyRowMapper.newInstance(ContenidoHomeBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoHomeBean> contenidoDestacado = (List<ContenidoHomeBean>) out.get("contenido_destacado");
        return contenidoDestacado;
    }

    @Override
    public Map<String, List<ContenidoHomeBean>> agruparContenido(List<ContenidoHomeBean> listaContenidos) {
        Map<String, List<ContenidoHomeBean>> contenido_agrupado = new HashMap<>();
        for (ContenidoHomeBean contenido : listaContenidos) {
            if (!contenido_agrupado.containsKey(contenido.getId_contenido())) {
                contenido_agrupado.put(contenido.getId_contenido(), new ArrayList<>());
            }
            contenido_agrupado.get(contenido.getId_contenido()).add(contenido);
        }

        return contenido_agrupado;
    }
}
