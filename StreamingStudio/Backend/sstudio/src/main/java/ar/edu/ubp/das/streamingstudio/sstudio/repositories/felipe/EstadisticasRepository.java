package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPublicistaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Publicista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Types;

@Repository
public class EstadisticasRepository implements IEstadisticasRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public String reportesPublicistas() {
        Map<Integer, List<EstadisticaPublicistaBean>> estadisticasPublicistas = obtenerEstadisticasPublicistas();
        for (Integer id_publicista : estadisticasPublicistas.keySet()) {
            int id_reporte = crearReportePublicista(id_publicista);
            System.out.println(id_reporte);
        }
        return "OK";
    }

    @Override
    public Map<Integer, List<EstadisticaPublicistaBean>> obtenerEstadisticasPublicistas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Estadisticas_para_Publicistas")
                .withSchemaName("dbo")
                .returningResultSet("estadisticas", BeanPropertyRowMapper.newInstance(EstadisticaPublicistaBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<EstadisticaPublicistaBean> estadisticas = (List<EstadisticaPublicistaBean>) out.get("estadisticas");

        Map<Integer, List<EstadisticaPublicistaBean>> estadisticas_agrupadas = new HashMap<>();
        for (EstadisticaPublicistaBean estadistica : estadisticas) {
            if (!estadisticas_agrupadas.containsKey(estadistica.getId_publicista())) {
                estadisticas_agrupadas.put(estadistica.getId_publicista(), new ArrayList<>());
            }
            estadisticas_agrupadas.get(estadistica.getId_publicista()).add(estadistica);
        }

        return estadisticas_agrupadas;
    }

    @Override
    public int crearReportePublicista(int id_publicista) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_publicista", id_publicista);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Reporte_Publicista")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("id_reporte", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        int id_reporte = (int) out.get("id_reporte");
        return id_reporte;
    }

    @Override
    public void crearDetalleReporte(int id_reporte, EstadisticaPublicistaBean detalle) {

    }

    @Override
    public void finalizarReporte(int id_reporte) {

    }

    @Override
    public Publicista obtenerDatosDePublicista(int id_publicista) {
        return null;
    }

    @Override
    public void enviarReporte(int id_reporte) {

    }
}
