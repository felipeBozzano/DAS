package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

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
    public Map<String, Map<?, List<?>>> getHome(int id_cliente) {
        List<PublicidadHomeBean> publicidades = getPublicidadesActivas();
        List<ContenidoHomeBean> contenido_mas_visto = getMasVisto(id_cliente);
        List<ContenidoHomeBean> contenido_reciente = getReciente(id_cliente);
        List<ContenidoHomeBean> contenido_destacado = getDestacado(id_cliente);

        Map<Integer, List<?>> publicidades_agrupadas = agruparPublicidad(publicidades);
        Map<String, List<?>> contenido_mas_visto_agrupado = agruparContenido(contenido_mas_visto);
        Map<String, List<?>> contenido_reciente_agrupado = agruparContenido(contenido_reciente);
        Map<String, List<?>> contenido_destacado_agrupado = agruparContenido(contenido_destacado);

        Map<String, Map<?, List<?>>> publicidades_contenido = new HashMap<>();
        publicidades_contenido.put("Publicidades", publicidades_agrupadas);
        publicidades_contenido.put("Mas_Visto", contenido_mas_visto_agrupado);
        publicidades_contenido.put("Reciente", contenido_reciente_agrupado);
        publicidades_contenido.put("Destacado", contenido_destacado_agrupado);

        return publicidades_contenido;
    }

    @Override
    public List<PublicidadHomeBean> getPublicidadesActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Publicidades_Activas")
                .withSchemaName("dbo")
                .returningResultSet("publicidades_activas", BeanPropertyRowMapper.newInstance(PublicidadHomeBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<PublicidadHomeBean> publicidadesActivas = (List<PublicidadHomeBean>)out.get("publicidades_activas");
        return publicidadesActivas;
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
    public Map<String, List<?>> agruparContenido(List<ContenidoHomeBean> listaContenidos) {
        Map<String, List<ContenidoHomeBean>> contenido_agrupado = new HashMap<>();
        for (ContenidoHomeBean contenido : listaContenidos) {
            if (!contenido_agrupado.containsKey(contenido.getId_contenido())) {
                contenido_agrupado.put(contenido.getId_contenido(), new ArrayList<>());
            }
            contenido_agrupado.get(contenido.getId_contenido()).add(contenido);
        }

        Set<String> llaves = contenido_agrupado.keySet();
        Map<String, List<?>> mapa_de_retorno = new HashMap<>();
        for (String llave: llaves) {
            mapa_de_retorno.put(llave, contenido_agrupado.get(llave));
        }

        return mapa_de_retorno;
    }

    @Override
    public Map<Integer, List<?>> agruparPublicidad(List<PublicidadHomeBean> listaPublicidad) {
        Map<Integer, List<PublicidadHomeBean>> publicidades_agrupadas = new HashMap<>();
        for (PublicidadHomeBean publicidad : listaPublicidad) {
            if (!publicidades_agrupadas.containsKey(publicidad.getId_tipo_banner())) {
                publicidades_agrupadas.put(publicidad.getId_tipo_banner(), new ArrayList<>());
            }
            publicidades_agrupadas.get(publicidad.getId_tipo_banner()).add(publicidad);
        }

        Set<Integer> llaves = publicidades_agrupadas.keySet();
        Map<Integer, List<?>> mapa_de_retorno = new HashMap<>();
        for (Integer llave: llaves) {
            mapa_de_retorno.put(llave, publicidades_agrupadas.get(llave));
        }

        return mapa_de_retorno;
    }
}
