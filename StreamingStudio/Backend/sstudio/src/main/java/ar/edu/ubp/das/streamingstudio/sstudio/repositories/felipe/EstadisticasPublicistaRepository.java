package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
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
public class EstadisticasPublicistaRepository implements IEstadisticasPublicistaRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    private final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    @Override
    public String reportesPublicistas() {
        Map<Integer, List<EstadisticaPublicistaBean>> estadisticasPublicistas = obtenerEstadisticasPublicistas();
        for (Integer id_publicista : estadisticasPublicistas.keySet()) {
            int clics_totales = 0;
            int id_reporte = crearReportePublicista(id_publicista);
            List<EstadisticaPublicistaBean> estadisticas = estadisticasPublicistas.get(id_publicista);
            for (EstadisticaPublicistaBean datos: estadisticas) {
                crearDetalleReporte(id_reporte, datos);
                clics_totales = clics_totales + datos.getCantidad_de_clics();
            }
            finalizarReporte(id_reporte, clics_totales);
            PublicistaBean publicistaBean = obtenerDatosDePublicista(id_publicista);
            enviarReporte(id_reporte, publicistaBean);
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
        return (int) out.get("id_reporte");
    }

    @Override
    public void crearDetalleReporte(int id_reporte, EstadisticaPublicistaBean detalle) {
        String descripcion = "Publicidad " + detalle.getCodigo_publicidad() + ": " + detalle.getCantidad_de_clics() + " clics";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte)
                .addValue("descripcion", descripcion)
                .addValue("cantidad_de_clics", detalle.getCantidad_de_clics());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Detalle_Reporte")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Override
    public void finalizarReporte(int id_reporte, int clics_totales) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte)
                .addValue("total", clics_totales);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Reporte")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Override
    public PublicistaBean obtenerDatosDePublicista(int id_publicista) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_publicista", id_publicista);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicista")
                .withSchemaName("dbo")
                .returningResultSet("publicista", BeanPropertyRowMapper.newInstance(PublicistaBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<PublicistaBean> publicistaBean = (List<PublicistaBean>) out.get("publicista");
        return publicistaBean.getFirst();
    }

    @Override
    public void enviarReporte(int id_reporte, PublicistaBean publicistaBean) {
        /*

        -) CREAR EL REPORTE A ENVIAR.

        -) PEGARLE A LA API DEL PUBLICISTA CARGANDOLE EL REPORTE CREADO.

        */

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Enviar_Reporte")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }
}
