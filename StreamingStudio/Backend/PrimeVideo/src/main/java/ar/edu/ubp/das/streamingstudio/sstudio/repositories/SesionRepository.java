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
import java.util.UUID;

@Repository
public class SesionRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    public Map<String, String> obtenerSesion(Map<String, String> body) {
        Map<String, String> respuesta = new HashMap<>();

        try {
            String token_de_partner = body.get("token_de_partner");
            String token_de_usuario = body.get("token_de_usuario");

            Map<String, Integer> ids = obtenerIds(token_de_partner, token_de_usuario);
            int id_partner = ids.get("id_partner");
            int id_cliente = ids.get("id_cliente");

            UUID sesion = UUID.randomUUID();
            String sesion_string = sesion.toString();
            crearSesion(id_partner, id_cliente, sesion_string);

            respuesta.put("sesion", sesion_string);
            respuesta.put("codigoRespuesta", "200");
            respuesta.put("mensajeRespuesta", "La sesion fue creada");
            return respuesta;

        } catch (Exception e) {
            respuesta.put("sesion", null);
            respuesta.put("codigoRespuesta", "1");
            respuesta.put("mensajeRespuesta", "La sesion no pudo ser creada");
            return respuesta;
        }
    }

    public Map<String, Integer> obtenerIds(String token_de_partner, String token_de_usuario) {
        SqlParameterSource in_partner = new MapSqlParameterSource()
                .addValue("token", token_de_partner);
        SimpleJdbcCall jdbcCall_partner = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_ID_de_Partner")
                .withSchemaName("dbo");
        Map<String, Object> out_partner = jdbcCall_partner.execute(in_partner);
        List<Map<String,Integer>> lista_id_partner = (List<Map<String,Integer>>) out_partner.get("#result-set-1");
        int id_partner = lista_id_partner.getFirst().get("id_partner");

        SqlParameterSource in_cliente = new MapSqlParameterSource()
                .addValue("token", token_de_usuario);
        SimpleJdbcCall jdbcCall_cliente = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Cliente")
                .withSchemaName("dbo");
        Map<String, Object> out_cliente = jdbcCall_cliente.execute(in_cliente);
        List<Map<String,Integer>> lista_id_cliente = (List<Map<String,Integer>>) out_cliente.get("#result-set-1");
        int id_cliente = lista_id_cliente.getFirst().get("id_cliente");

        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("id_partner", id_partner);
        respuesta.put("id_cliente", id_cliente);
        return respuesta;
    }

    public void crearSesion(int id_partner, int id_cliente, String sesion) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente)
                .addValue("id_partner", id_partner)
                .addValue("sesion", sesion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Sesion")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public boolean usarSesion(Map<String, String> body) {
        String sesion = body.get("token_de_sesion");

        try {
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("sesion", sesion);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Usar_Sesion")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
