package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoResponseHomeBean;
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
public class HomeRepository implements IHomeRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public Map<String, Map<String, ContenidoResponseHomeBean>> getHome(int id_cliente) {
        List<ContenidoHomeBean> contenido_mas_visto = getMasVisto(id_cliente);
        List<ContenidoHomeBean> contenido_reciente = getReciente(id_cliente);
        List<ContenidoHomeBean> contenido_destacado = getDestacado(id_cliente);

        Map<String, ContenidoResponseHomeBean> contenido_mas_visto_agrupado = agruparContenido(contenido_mas_visto);
        Map<String, ContenidoResponseHomeBean> contenido_reciente_agrupado = agruparContenido(contenido_reciente);
        Map<String, ContenidoResponseHomeBean> contenido_destacado_agrupado = agruparContenido(contenido_destacado);

        Map<String, Map<String, ContenidoResponseHomeBean>> contenido = new HashMap<>();
        contenido.put("Mas_Visto", contenido_mas_visto_agrupado);
        contenido.put("Reciente", contenido_reciente_agrupado);
        contenido.put("Destacado", contenido_destacado_agrupado);

        return contenido;
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
        List<ContenidoHomeBean> contenidoMasVisto = (List<ContenidoHomeBean>) out.get("contenido_mas_visto");
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
    public Map<String, ContenidoResponseHomeBean> agruparContenido(List<ContenidoHomeBean> listaContenidos) {
        Map<String, ContenidoResponseHomeBean> contenido_agrupado = new HashMap<>();
        for (ContenidoHomeBean contenido : listaContenidos) {
            if (!contenido_agrupado.containsKey(contenido.getId_contenido())) {
                ContenidoResponseHomeBean responseBean = new ContenidoResponseHomeBean();
                responseBean.setId_contenido(contenido.getId_contenido());
                responseBean.setId_plataforma(new ArrayList<>(Collections.singletonList(contenido.getId_plataforma())));
                responseBean.setUrl_imagen(contenido.getUrl_imagen());
                contenido_agrupado.put(contenido.getId_contenido(), responseBean);
            } else {
                ContenidoResponseHomeBean existingContenido = contenido_agrupado.get(contenido.getId_contenido());
                existingContenido.getId_plataforma().add(contenido.getId_plataforma());
            }
        }
        return contenido_agrupado;
    }
}
