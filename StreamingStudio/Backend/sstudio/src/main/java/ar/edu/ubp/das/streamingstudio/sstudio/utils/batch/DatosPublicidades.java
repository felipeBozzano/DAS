package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicistaBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;
import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.obtenerInformacionDeConexionAPublicista;

public class DatosPublicidades {
    private static JdbcTemplate jdbcTpl;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void obtenerPublicidades() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Integer> id_publicistas = obtenerPublicistas();
        List<PublicidadBean> publicidades_propias = obtenerPublicidadesPropias();

        for(int id_publicista: id_publicistas) {
            // Filtro las publicidades activas propias y que corresponden a este publicista
            List<PublicidadBean> publicidades_propias_por_publicista = publicidades_propias.stream()
                    .filter(p -> p.getId_publicista() == id_publicista)
                    .toList();

            // Obtengo los datos de las publicidades del publicista
            PublicistaBean publicista = obtenerInformacionDeConexionAPublicista(id_publicista, jdbcTpl);
            List<PublicidadBean> publicidades_publicista = obtenerPublicidadesDePublicista(publicista);

            // Crear un Map a partir de publicidades_publicista con codigo_publicidad como clave
            Map<String, PublicidadBean> publicidades_publicista_map = publicidades_publicista.stream()
                    .collect(Collectors.toMap(PublicidadBean::getCodigo_publicidad, p -> p));

            // Filtrar lista1 para que solo contenga los elementos que están en lista2
            List<PublicidadBean> publicidades_propias_a_actualizar = publicidades_propias_por_publicista.stream()
                    .filter(p -> publicidades_publicista_map.containsKey(p.getCodigo_publicidad()))
                    .toList();

            // Actualizar los campos de publicidades_propias_filtradas
            publicidades_propias_a_actualizar.forEach(p -> {
                PublicidadBean match = publicidades_publicista_map.get(p.getCodigo_publicidad());
                if (match != null) {
                    p.setUrl_de_imagen(match.getUrl_de_imagen());
                    p.setUrl_de_publicidad(match.getUrl_de_publicidad());
                }
            });

            actualizarPublicidades(publicidades_propias_a_actualizar);
            System.out.println(publicidades_propias_a_actualizar);
        }
    }

    public static List<Integer> obtenerPublicistas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Publicistas_Activos")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,Integer>> lista_publicistas = (List<Map<String,Integer>>) out.get("#result-set-1");

        List<Integer> publicistas = new ArrayList<>();
        for(Map<String,Integer> publicista: lista_publicistas) {
            publicistas.add(publicista.get("id_publicista"));
        }
        return publicistas;
    }

    public static List<PublicidadBean> obtenerPublicidadesPropias() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicidades_Activas")
                .withSchemaName("dbo")
                .returningResultSet("publicidad", BeanPropertyRowMapper.newInstance(PublicidadBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<PublicidadBean> lista_publicistas = (List<PublicidadBean>) out.get("publicidad");
        return lista_publicistas;
    }

    public static List<PublicidadBean> obtenerPublicidadesDePublicista(PublicistaBean publicista) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AbstractConnector conector = conectorFactory.crearConector(publicista.getProtocolo_api());
//            FederacionBean bean = (FederacionBean) conector.execute_post_request(url_token, body, "FederacionBean");


        List<PublicidadBean> publicidades = new ArrayList<>();
        if(Objects.equals(publicista.getNombre_de_fantasia(), "Publicista1")) {
            PublicidadBean publicidad = new PublicidadBean("CP1", "https://www.urlactualizada.com/1.jpg", "https://www.urlactualizada.com/publi1");
            publicidades.add(publicidad);
        }
        if(Objects.equals(publicista.getNombre_de_fantasia(), "Publicista2")) {
            PublicidadBean publicidad = new PublicidadBean("CP5", "https://www.urlactualizada.com/5.jpg", "https://www.urlactualizada.com/publi5");
            publicidades.add(publicidad);
        }
        if(Objects.equals(publicista.getNombre_de_fantasia(), "Publicista3")) {
            PublicidadBean publicidad1 = new PublicidadBean("CP6", "https://www.urlactualizada.com/6.jpg", "https://www.urlactualizada.com/publi6");
            PublicidadBean publicidad2 = new PublicidadBean("CP9", "https://www.urlactualizada.com/9.jpg", "https://www.urlactualizada.com/publi9");
            publicidades.add(publicidad1);
            publicidades.add(publicidad2);
        }

        return publicidades;
    }

    public static void actualizarPublicidades(List<PublicidadBean> publicidades) {
        for(PublicidadBean publicidad: publicidades) {
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_publicidad", publicidad.getId_publicidad())
                    .addValue("url_de_imagen", publicidad.getUrl_de_imagen())
                    .addValue("url_de_publicidad", publicidad.getUrl_de_publicidad());
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Actualizar_Publicidades")
                    .withSchemaName("dbo");

            jdbcCall.execute(in);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        jdbcTpl = crearJdbcTemplate();
        obtenerPublicidades();
    }
}