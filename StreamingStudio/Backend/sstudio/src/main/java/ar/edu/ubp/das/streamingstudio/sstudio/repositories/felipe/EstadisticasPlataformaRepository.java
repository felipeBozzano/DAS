package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPlataformaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPublicistaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Publicista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EstadisticasPlataformaRepository implements IEstadisticasPlataformaRepository{

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public String reportesPlataformas() {
        Map<Integer, List<EstadisticaPlataformaBean>> estadisticasPlataformas = obtenerEstadisticasPlataformas();
        for (Integer id_plataforma : estadisticasPlataformas.keySet()) {
            int clics_totales = 0;
            int id_reporte = crearReportePlataforma(id_plataforma);
            List<EstadisticaPlataformaBean> estadisticas = estadisticasPlataformas.get(id_plataforma);
            for (EstadisticaPlataformaBean datos: estadisticas) {
                crearDetalleReporte(id_reporte, datos);
                clics_totales = clics_totales + datos.getCantidad_de_clics();
            }
            finalizarReporte(id_reporte, clics_totales);
            PlataformaDeStreamingBean plataforma = obtenerDatosDePlataforma(id_plataforma);
            enviarReporte(id_reporte, plataforma);
        }
        return "OK";
    }

//    public Map<Integer, List<EstadisticaPlataformaBean>> reportesPlataformas() {
//        Map<Integer, List<EstadisticaPlataformaBean>> estadisticasPlataformas = obtenerEstadisticasPlataformas();
//        return estadisticasPlataformas;
//    }

    @Override
    public Map<Integer, List<EstadisticaPlataformaBean>> obtenerEstadisticasPlataformas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Estadisticas_para_Plataformas")
                .withSchemaName("dbo")
                .returningResultSet("estadisticas", BeanPropertyRowMapper.newInstance(EstadisticaPlataformaBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<EstadisticaPlataformaBean> estadisticas = (List<EstadisticaPlataformaBean>) out.get("estadisticas");

        Map<Integer, List<EstadisticaPlataformaBean>> estadisticas_agrupadas = new HashMap<>();
        for (EstadisticaPlataformaBean estadistica : estadisticas) {
            if (!estadisticas_agrupadas.containsKey(estadistica.getId_plataforma())) {
                estadisticas_agrupadas.put(estadistica.getId_plataforma(), new ArrayList<>());
            }
            estadisticas_agrupadas.get(estadistica.getId_plataforma()).add(estadistica);
        }
        return estadisticas_agrupadas;
    }

    @Override
    public int crearReportePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Reporte_Plataforma")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("id_reporte", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        return (int) out.get("id_reporte");
    }

    @Override
    public void crearDetalleReporte(int id_reporte, EstadisticaPlataformaBean detalle) {
        String descripcion = "Contenido " + detalle.getId_en_plataforma() + ": " + detalle.getCantidad_de_clics() + " clics";
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
    public PlataformaDeStreamingBean obtenerDatosDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Plataforma")
                .withSchemaName("dbo")
                .returningResultSet("plataforma", BeanPropertyRowMapper.newInstance(PlataformaDeStreamingBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<PlataformaDeStreamingBean> plataforma = (List<PlataformaDeStreamingBean>) out.get("plataforma");
        return plataforma.getFirst();
    }

    @Override
    public void enviarReporte(int id_reporte, PlataformaDeStreamingBean plataforma) {
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
