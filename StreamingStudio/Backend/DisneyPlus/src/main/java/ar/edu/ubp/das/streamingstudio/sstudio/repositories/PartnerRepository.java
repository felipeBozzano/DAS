package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PartnerRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public boolean verificarTokenDePartner(String token_de_servicio) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("token", token_de_servicio);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Token_de_Partner")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> mapa_token = (List<Map<String,String>>) out.get("#result-set-1");
        String existe_partner = mapa_token.getFirst().get("ExistePartner");

        return existe_partner.equals("true");
    }

    public Map<String, String> respuestaPartnerIncorrecto() {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("codigoRespuesta", "-1");
        respuesta.put("mensajeRespuesta", "Partner no verificado");

        return respuesta;
    }
}
