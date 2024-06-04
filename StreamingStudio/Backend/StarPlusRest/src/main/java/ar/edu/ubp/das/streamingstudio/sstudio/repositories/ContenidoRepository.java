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
public class ContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    private SesionRepository sesionRepository;

    public Map<String, String> obtenerUrlDeContenido(Map<String, String> body) {
        Map<String, String> respuesta = new HashMap<>();
        if (!sesionRepository.usarSesion(body)) {
            respuesta.put("codigoRespuesta", "1");
            respuesta.put("mensajeRespuesta", "Sesion erronea o ya utilizada");
            return respuesta;
        }

        SqlParameterSource in_partner = new MapSqlParameterSource()
                .addValue("id_contenido", body.get("id_contenido"));
        SimpleJdbcCall jdbcCall_partner = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Url_de_Contenido")
                .withSchemaName("dbo");
        Map<String, Object> out_partner = jdbcCall_partner.execute(in_partner);
        List<Map<String,String>> lista_url = (List<Map<String,String>>) out_partner.get("#result-set-1");
        String url = lista_url.getFirst().get("url_reproduccion");

        respuesta.put("codigoRespuesta", "200");
        respuesta.put("mensajeRespuesta", "Sesion correcta");
        respuesta.put("url", url);
        return respuesta;
    }
}
