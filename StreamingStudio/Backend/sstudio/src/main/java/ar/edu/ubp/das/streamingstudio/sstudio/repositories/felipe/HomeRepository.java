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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HomeRepository implements IHomeRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public Map<String, List<?>> getHome(int id_cliente) {
        List<PublicidadBean> publicidades = getPublicidadesActivas();
        List<ContenidoBean> contenido_mas_visto = getMasVisto(id_cliente);
        List<ContenidoBean> contenido_reciente = getReciente(id_cliente);
        List<ContenidoBean> contenido_destacado = getDestacado(id_cliente);

        Map<String, List<?>> publicidades_contenido = new HashMap<String, List<?>>();
        publicidades_contenido.put("Publicidades", publicidades);
        publicidades_contenido.put("Mas_Visto", contenido_mas_visto);
        publicidades_contenido.put("Reciente", contenido_reciente);
        publicidades_contenido.put("Destacado", contenido_destacado);

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
}
