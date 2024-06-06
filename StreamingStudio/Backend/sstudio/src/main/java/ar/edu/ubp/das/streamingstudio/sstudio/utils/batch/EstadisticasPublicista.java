package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.MensajeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.EstadisticaPublicistaBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicistaBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;

@Repository
public class EstadisticasPublicista {
    private static JdbcTemplate jdbcTpl;
    private static Map<String,Object> reporte;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void reportesPublicistas() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Integer, List<EstadisticaPublicistaBean>> estadisticasPublicistas = obtenerEstadisticasPublicistas();
        for (Integer id_publicista : estadisticasPublicistas.keySet()) {
            reporte = new HashMap<>();
            reporte.put("fecha", getFecha());

            PublicistaBean publicista = obtenerDatosDePublicista(id_publicista);
            int id_reporte = crearReportePublicista(id_publicista);
            reporte.put("plataforma", publicista.getNombre_de_fantasia());
            reporte.put("razon_social", publicista.getRazon_social());

            Map<String, String> detalle_reporte = new HashMap<>();
            int clics_totales = 0;
            List<EstadisticaPublicistaBean> estadisticas = estadisticasPublicistas.get(id_publicista);
            for (EstadisticaPublicistaBean datos: estadisticas) {
                crearDetalleReporte(id_reporte, datos);
                detalle_reporte.put(String.valueOf(datos.getCodigo_publicidad()), String.valueOf(datos.getCantidad_de_clics()));
                clics_totales += datos.getCantidad_de_clics();
            }
            reporte.put("clics_totales", String.valueOf(clics_totales));
            reporte.put("detalle", detalle_reporte);

            finalizarReporte(id_reporte, clics_totales);
            enviarReporte(id_reporte, publicista);
        }
    }

    private static String getFecha() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaActual.format(formatter);
    }

    public static Map<Integer, List<EstadisticaPublicistaBean>> obtenerEstadisticasPublicistas() {
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

    public static PublicistaBean obtenerDatosDePublicista(int id_publicista) {
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

    public static int crearReportePublicista(int id_publicista) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_publicista", id_publicista);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Reporte_Publicista")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("id_reporte", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        return (int) out.get("id_reporte");
    }

    public static void crearDetalleReporte(int id_reporte, EstadisticaPublicistaBean detalle) {
        String descripcion = "Publicidad " + detalle.getCodigo_publicidad() + ": " + detalle.getCantidad_de_clics() + " clics";
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

    public static void enviarReporte(int id_reporte, PublicistaBean publicista) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractConnector conector = conectorFactory.crearConector(publicista.getProtocolo_api());
        Map<String, String> body = new HashMap<>();

        String total = (String) reporte.get("clics_totales");
        String fecha = (String) reporte.get("fecha");
        StringBuilder detalle = new StringBuilder("Publicista " + reporte.get("plataforma") + " - Razon Social " + reporte.get("razon_social") + "\n");
        Map<String, String> mapa_detalle = (Map<String, String>) reporte.get("detalle");
        for (Map.Entry<String, String> detalle_reporte: mapa_detalle.entrySet()) {
            detalle.append("Publicidad ").append(detalle_reporte.getKey()).append(" - ").append(detalle_reporte.getValue()).append(" clics | ");
        }

        if (publicista.getProtocolo_api().equals("SOAP")) {
            String message = """
                    <ws:obtenerEstadisticas xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                    <token_de_partner>%s</token_de_partner>
                    <total>%s</total>
                    <fecha>%s</fecha>
                    <descripcion>%s</descripcion>
                    </ws:obtenerEstadisticas>""".formatted(publicista.getToken_de_servicio(), total, fecha, detalle.toString());
            body.put("message", message);
            body.put("web_service", "obtenerEstadisticas");
        }
        else {
            body.put("token_de_servicio", publicista.getToken_de_servicio());
            body.put("total", total);
            body.put("fecha", fecha);
            body.put("descripcion", detalle.toString());
        }

        MensajeBean respuesta = (MensajeBean) conector.execute_post_request(publicista.getUrl_api() + "/obtenerEstadisticas", body, "MensajeBean");
        System.out.println("Publicista: " + reporte.get("plataforma") + "\n" + "Codigo: " + respuesta.getCodigoRespuesta() + " - Mensaje: " + respuesta.getMensajeRespuesta());

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_reporte", id_reporte);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Enviar_Reporte")
                .withSchemaName("dbo");
        jdbcCall.execute(in);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        jdbcTpl = crearJdbcTemplate();
        reportesPublicistas();
    }
}
