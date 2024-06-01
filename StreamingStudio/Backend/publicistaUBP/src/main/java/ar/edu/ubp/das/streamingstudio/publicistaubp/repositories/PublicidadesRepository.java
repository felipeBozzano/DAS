package ar.edu.ubp.das.streamingstudio.publicistaubp.repositories;

import ar.edu.ubp.das.streamingstudio.publicistaubp.models.PublicidadBean;
import ar.edu.ubp.das.streamingstudio.publicistaubp.models.PublicidadResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PublicidadesRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    PartnerRepository partnerRepository;

    public PublicidadResponseBean obtenerPublicidades(Map<String, String> body) {

        PublicidadResponseBean responseBean = new PublicidadResponseBean();

        if (!partnerRepository.verificarTokenDePartner(body.get("token_de_servicio"))) {
            responseBean.setCodigoRespuesta(-1);
            responseBean.setMensajeRespuesta("Partner no verificado");
            return responseBean;
        }

        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicidades")
                .withSchemaName("dbo")
                .returningResultSet("publicidades", BeanPropertyRowMapper.newInstance(PublicidadBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        responseBean.setListaPublicidades((List<PublicidadBean>) out.get("publicidades"));

        if (responseBean.getListaPublicidades().isEmpty()) {
            responseBean.setCodigoRespuesta(1);
            responseBean.setMensajeRespuesta("No hay publicidades que actualizar");
        }
        else {
            responseBean.setCodigoRespuesta(200);
            responseBean.setMensajeRespuesta("success");
        }
        return responseBean;
    }
}
