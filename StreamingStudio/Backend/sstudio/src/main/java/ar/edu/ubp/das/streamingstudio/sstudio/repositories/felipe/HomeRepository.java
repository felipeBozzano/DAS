package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
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
    public Map<String, Map<Integer, List<?>>> getHome(int id_cliente) {
        List<PublicidadBean> publicidades = getPublicidadesActivas();
        List<ContenidoBean> contenido_mas_visto = getMasVisto(id_cliente);
        List<ContenidoBean> contenido_reciente = getReciente(id_cliente);
        List<ContenidoBean> contenido_destacado = getDestacado(id_cliente);

        Map<Integer, List<?>> publicidades_agrupadas = agruparPublicidad(publicidades);
        Map<Integer, List<?>> contenido_mas_visto_agrupado = agruparContenido(contenido_mas_visto);
        Map<Integer, List<?>> contenido_reciente_agrupado = agruparContenido(contenido_reciente);
        Map<Integer, List<?>> contenido_destacado_agrupado = agruparContenido(contenido_destacado);

        Map<String, Map<Integer, List<?>>> publicidades_contenido = new HashMap<>();
        publicidades_contenido.put("Publicidades", publicidades_agrupadas);
        publicidades_contenido.put("Mas_Visto", contenido_mas_visto_agrupado);
        publicidades_contenido.put("Reciente", contenido_reciente_agrupado);
        publicidades_contenido.put("Destacado", contenido_destacado_agrupado);

        return publicidades_contenido;
    }

    @Override
    public List<PublicidadBean> getPublicidadesActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Publicidades_Activas")
                .withSchemaName("dbo")
                .returningResultSet("publicidades_activas", BeanPropertyRowMapper.newInstance(PublicidadBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PublicidadBean>)out.get("publicidades_activas");
    }

    @Override
    public List<ContenidoBean> getMasVisto(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_mas_Visto")
                .withSchemaName("dbo")
                .returningResultSet("contenido_mas_visto", BeanPropertyRowMapper.newInstance(ContenidoBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ContenidoBean>)out.get("contenido_mas_visto");
    }

    @Override
    public List<ContenidoBean> getReciente(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_Reciente")
                .withSchemaName("dbo")
                .returningResultSet("contenido_reciente", BeanPropertyRowMapper.newInstance(ContenidoBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ContenidoBean>)out.get("contenido_reciente");
    }

    @Override
    public List<ContenidoBean> getDestacado(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_Destacado")
                .withSchemaName("dbo")
                .returningResultSet("contenido_destacado", BeanPropertyRowMapper.newInstance(ContenidoBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ContenidoBean>)out.get("contenido_destacado");
    }

    @Override
    public Map<Integer, List<?>> agruparContenido(List<ContenidoBean> listaContenidos) {
        Map<Integer, List<ContenidoBean>> contenido_agrupado = new HashMap<>();
        for (ContenidoBean contenido : listaContenidos) {
            if (!contenido_agrupado.containsKey(contenido.getId_contenido())) {
                contenido_agrupado.put(contenido.getId_contenido(), new ArrayList<>());
            }
            contenido_agrupado.get(contenido.getId_contenido()).add(contenido);
        }

        Set<Integer> llaves = contenido_agrupado.keySet();
        Map<Integer, List<?>> mapa_de_retorno = new HashMap<>();
        for (Integer llave: llaves) {
            mapa_de_retorno.put(llave, contenido_agrupado.get(llave));
        }

        return mapa_de_retorno;
    }

    @Override
    public Map<Integer, List<?>> agruparPublicidad(List<PublicidadBean> listaPublicidad) {
        Map<Integer, List<PublicidadBean>> publicidades_agrupadas = new HashMap<>();
        for (PublicidadBean publicidad : listaPublicidad) {
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
