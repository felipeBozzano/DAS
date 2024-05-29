package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;
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
public class PublicidadesRepository implements IPublicidadesRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public Map<String, Map<Integer, List<PublicidadHomeBean>>> obtenerPublicidadesAgrupadas( ) {
        List<PublicidadHomeBean> publicidades = getPublicidadesActivas();
        Map<Integer, List<PublicidadHomeBean>> publicidades_agrupadas = agruparPublicidad(publicidades);
        Map<String, Map<Integer, List<PublicidadHomeBean>>> publicidades_contenido = new HashMap<>();
        publicidades_contenido.put("Publicidades", publicidades_agrupadas);
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


    public List<PublicidadHomeBean> obtenerPublicidades() {
        List<PublicidadHomeBean> bean = (List<PublicidadHomeBean>) new PublicidadHomeBean();
        return bean;
    }

    @Override
    public Map<Integer, List<PublicidadHomeBean>> agruparPublicidad(List<PublicidadHomeBean> listaPublicidad) {
        Map<Integer, List<PublicidadHomeBean>> publicidades_agrupadas = new HashMap<>();
        for (PublicidadHomeBean publicidad : listaPublicidad) {
            if (!publicidades_agrupadas.containsKey(publicidad.getId_tipo_banner())) {
                publicidades_agrupadas.put(publicidad.getId_tipo_banner(), new ArrayList<>());
            }
            publicidades_agrupadas.get(publicidad.getId_tipo_banner()).add(publicidad);
        }


        return publicidades_agrupadas;
    }
}
