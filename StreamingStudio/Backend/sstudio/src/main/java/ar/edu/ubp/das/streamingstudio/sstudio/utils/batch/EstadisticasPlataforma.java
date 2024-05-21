package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPlataformaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaEstadisticaBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;

@Repository
public class EstadisticasPlataforma {
    private static JdbcTemplate jdbcTpl;
    static Map<String,Object> reporte;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void reportesPlataformas() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Integer, List<EstadisticaPlataformaBean>> estadisticasPlataformas = obtenerEstadisticasPlataformas();
        for (Integer id_plataforma : estadisticasPlataformas.keySet()) {
            reporte = new HashMap<>();

            PlataformaEstadisticaBean plataforma = obtenerDatosDePlataforma(id_plataforma);
            int id_reporte = crearReportePlataforma(id_plataforma);
            reporte.put("plataforma", plataforma.getNombre_de_fantasia());
            reporte.put("razon_social", plataforma.getRazon_social());
            Map<String, String> detalle_reporte = new HashMap<>();

            int clics_totales = 0;
            List<EstadisticaPlataformaBean> estadisticas = estadisticasPlataformas.get(id_plataforma);
            for (EstadisticaPlataformaBean datos: estadisticas) {
                crearDetalleReporte(id_reporte, datos);
                detalle_reporte.put(String.valueOf(datos.getId_contenido()), String.valueOf(datos.getCantidad_de_clics()));
                clics_totales += datos.getCantidad_de_clics();
            }

            reporte.put("clics_totales", String.valueOf(clics_totales));
            reporte.put("detalle", detalle_reporte);
            finalizarReporte(id_reporte, clics_totales);
            enviarReporte(id_reporte, plataforma);
            System.out.println(reporte);
        }
    }

    public static Map<Integer, List<EstadisticaPlataformaBean>> obtenerEstadisticasPlataformas() {
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

    public static PlataformaEstadisticaBean obtenerDatosDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Plataforma")
                .withSchemaName("dbo")
                .returningResultSet("plataforma", BeanPropertyRowMapper.newInstance(PlataformaEstadisticaBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<PlataformaEstadisticaBean> plataforma = (List<PlataformaEstadisticaBean>) out.get("plataforma");
        return plataforma.getFirst();
    }

    public static int crearReportePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Reporte_Plataforma")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("id_reporte", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        return (int) out.get("id_reporte");
    }

    public static void crearDetalleReporte(int id_reporte, EstadisticaPlataformaBean detalle) {
        String descripcion = "Contenido " + detalle.getId_contenido() + ": " + detalle.getCantidad_de_clics() + " clics";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte)
                .addValue("descripcion", descripcion)
                .addValue("cantidad_de_clics", detalle.getCantidad_de_clics());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Detalle_Reporte")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public static void finalizarReporte(int id_reporte, int clics_totales) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte)
                .addValue("total", clics_totales);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Reporte")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public static void enviarReporte(int id_reporte, PlataformaEstadisticaBean plataforma) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractConnector conector = conectorFactory.crearConector(plataforma.getProtocolo_api());

        Map<String, Object> body = new HashMap<>();
        body.put("token_de_servicio", plataforma.getToken_de_servicio());
        body.put("reporte", reporte);

        /*

        -) PEGARLE A LA API DEL PUBLICISTA CARGANDOLE EL REPORTE CREADO.

        */

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Enviar_Reporte")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        jdbcTpl = crearJdbcTemplate();
        reportesPlataformas();
    }
}