package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
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
public class FederacionesPendientes implements IFederacionesPendientes{

    @Autowired
    JdbcTemplate jdbcTpl;

    @Override
    public void terminarFederacionesPendientes() {
        List<TransaccionBean> federacionesPendientes = consultarFederacionesPendientes();
        for (TransaccionBean federacionPendiente: federacionesPendientes) {
            int id_plataforma = federacionPendiente.getId_plataforma();
            int id_cliente = federacionPendiente.getId_cliente();
            String token = obtenerTokenDeServicioDePlataforma(id_plataforma);
            finalizarFederacion(id_plataforma, id_cliente, token);
        }
    }

    @Override
    public List<TransaccionBean> consultarFederacionesPendientes() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Consultar_Federaciones_Pendientes")
                .withSchemaName("dbo")
                .returningResultSet("transacciones", BeanPropertyRowMapper.newInstance(TransaccionBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<TransaccionBean>) out.get("transacciones");
    }

    @Override
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> token = (List<Map<String, String>>) out.get("#result-set-1");
        return null;
    }

    @Override
    public void finalizarFederacion(int id_plataforma, int id_cliente, String token) {

    }
}
