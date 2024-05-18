package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Types;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;

@Repository
public class EstadisticasPublicista {
    private static JdbcTemplate jdbcTpl;
    static Map<String,Object> reporte;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void reportesPublicistas() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Integer, List<EstadisticaPublicistaBean>> estadisticasPublicistas = obtenerEstadisticasPublicistas();
        for (Integer id_publicista : estadisticasPublicistas.keySet()) {
            reporte = new HashMap<>();

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
            System.out.println(reporte);
        }
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

        Map<String, Object> body = new HashMap<>();
        body.put("token_de_servicio", publicista.getToken_de_servicio());
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
        reportesPublicistas();
    }
}
