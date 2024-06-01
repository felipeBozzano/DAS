package ar.edu.ubp.das.streamingstudio.publicistaubp.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EstadisticasRepository {
    private Map<String, String> respuesta;

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Autowired
    PartnerRepository partnerRepository;

    @Transactional
    public Map<String, String> registrarReporteEstadisticas(Map<String, String> body) {
        respuesta = new HashMap<>();

        if (!partnerRepository.verificarTokenDePartner(body.get("token_de_servicio"))) {
            respuesta.put("codigoRespuesta", "-1");
            respuesta.put("mensajeRespuesta", "Partner no verificado");
            return respuesta;
        }

        Date fecha_formateada = new Date(2024, 06, 06);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(body.get("fecha"), formatter);
            fecha_formateada = Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("total", Double.parseDouble(body.get("total")))
                .addValue("fecha", fecha_formateada)
                .addValue("descripcion", body.get("descripcion"));
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Registrar_Reporte")
                .withSchemaName("dbo");
        jdbcCall.execute(in);

        respuesta.put("codigoRespuesta", "200");
        respuesta.put("mensajeRespuesta", "Reporte recibido");
        return respuesta;
    }
}
