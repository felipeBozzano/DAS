package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.CatalogoBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;
import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.obtenerInformacionDeConexionAPlataforma;

@Repository
public class ActualizarCatalogo {
    private static JdbcTemplate jdbcTpl;
    static Map<String, String> respuesta;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static void actualizarCatalogo() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> catalogoStreamingStudio = obtenerCatalogo();
        List<PlataformaDeStreamingBean> plataformasActivas = obtenerPlataformasActivas();
        for (PlataformaDeStreamingBean plataforma : plataformasActivas) {

            // Obtenemos el catálogo de la plataforma de streaming
            int id_plataforma = plataforma.getId_plataforma();
            CatalogoBean catalogoDePlataforma = obtenerCatalogoDePlataforma(id_plataforma);
            if (catalogoDePlataforma.getCodigoRespuesta() == -1 || catalogoDePlataforma.getCodigoRespuesta() == 1)
                continue;

            // Filtramos nuestro catalogo para obtener solo el contenido referente a esta plataforma de streaming
            List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> catalogoStreamingStudioFiltrado = catalogoStreamingStudio.stream()
                    .filter(contenido -> contenido.getId_plataforma() == id_plataforma)
                    .toList();

            // Filtramos el contenido de nuestra plataforma que está activo
            List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoStreamingStudioActivo = catalogoStreamingStudioFiltrado.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() == null || contenido.getFecha_de_alta().after(contenido.getFecha_de_baja()))
                    .toList();

            // Filtramos el contenido de nuestra plataforma que está inactivo
            List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoStreamingStudioInactivo = catalogoStreamingStudioFiltrado.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() != null && contenido.getFecha_de_baja().after(contenido.getFecha_de_alta()))
                    .toList();

            // Filtramos el contenido de la plataforma de streaming que esta inactivo
            List<ContenidoCatalogoBean> contenidoPlataformaInactivo = catalogoDePlataforma.getListaContenido().stream()
                    .filter(contenido -> !contenido.isValido())
                    .toList();

            // Filtro el contenido de la plataforma de streaming que esta activo
            List<ContenidoCatalogoBean> contenidoPlataformaActivo = catalogoDePlataforma.getListaContenido().stream()
                    .filter(contenido -> contenido.isValido())
                    .toList();


            if (!contenidoPlataformaInactivo.isEmpty()) {
                // Buscamos el contenido que la plataforma de streaming dio de baja, para darlo de baja en nuestra plataforma
                darDeBajaContenido(contenidoStreamingStudioActivo, contenidoPlataformaInactivo);
            }

            if (!contenidoPlataformaActivo.isEmpty()) {
                // Buscamos el contenido dado de baja en Streaming Studio para revisar si tenemos que habilitarlo
                habilitarContenido(contenidoStreamingStudioInactivo, contenidoPlataformaActivo);

                // Buscamos el contenido de la plataforma de streaming que no está en Streaming Studio y lo cargamos
                agregarContenidoNuevo(id_plataforma, catalogoStreamingStudioFiltrado, contenidoPlataformaActivo);
            }

            // Buscamos el contenido que esté en ambas plataformas y lo actualizamos si cambió
            actualizarContenido(id_plataforma, catalogoStreamingStudioFiltrado, catalogoDePlataforma);
        }
    }

    public static List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> obtenerCatalogo() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Catalogo_Actual")
                .withSchemaName("dbo")
                .returningResultSet("catalogo", BeanPropertyRowMapper.newInstance(ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean>) out.get("catalogo");
    }

    public static List<PlataformaDeStreamingBean> obtenerPlataformasActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Plataformas_de_Streaming_Activas")
                .withSchemaName("dbo")
                .returningResultSet("plataforma", BeanPropertyRowMapper.newInstance(PlataformaDeStreamingBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PlataformaDeStreamingBean>) out.get("plataforma");
    }

    public static CatalogoBean obtenerCatalogoDePlataforma(int id_plataforma) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma, jdbcTpl);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();

        if (conexion_plataforma.get("protocolo_api").equals("SOAP")) {
            String message = """
                    <ws:catalogo xmlns:ws="http://platforms.streamingstudio.das.ubp.edu.ar/" >
                    <token_de_partner>%s</token_de_partner>
                    </ws:catalogo>""".formatted(conexion_plataforma.get("token_de_servicio"));
            body.put("message", message);
            body.put("web_service", "catalogo");
        } else {
            body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));
        }

        CatalogoBean catalogo = (CatalogoBean) conector.execute_post_request(conexion_plataforma.get("url_api") + "/catalogo", body, "CatalogoBean");
        System.out.println("Plataforma: " + id_plataforma + " - Codigo: " + catalogo.getCodigoRespuesta() + " - Mensaje: " + catalogo.getMensajeRespuesta());

        return catalogo;
    }

    public static void darDeBajaContenido(List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoStreamingStudioActivo, List<ContenidoCatalogoBean> contenidoPlataformaInactivo) {

        List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoADarDeBaja = cruzarContenido(contenidoStreamingStudioActivo, contenidoPlataformaInactivo);

        // Doy de baja dichos contenidos
        for (ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean contenido : contenidoADarDeBaja) {
            String id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Dar_de_Baja_Item_en_Catalogo")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    public static void habilitarContenido(List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoStreamingStudioInactivo, List<ContenidoCatalogoBean> contenidoPlataformaActivo) {

        List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoAActivar = cruzarContenido(contenidoStreamingStudioInactivo, contenidoPlataformaActivo);

        // Habilito dichos contenidos
        for (ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean contenido : contenidoAActivar) {
            String id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Activar_Item_en_Catalogo")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    private static List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> cruzarContenido(List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoStreamingStudio, List<ContenidoCatalogoBean> contenidoPlataforma) {
        // Crear un HashSet con los id_en_plataforma
        Set<String> idContenidoPlataforma = new HashSet<>();
        for (ContenidoCatalogoBean bean : contenidoPlataforma) {
            idContenidoPlataforma.add(bean.getId_contenido());
        }

        // Cruzo los contenidos de ambos catálogos
        List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> contenidoAActivar = new ArrayList<>();
        for (ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean bean : contenidoStreamingStudio) {
            if (idContenidoPlataforma.contains(bean.getId_contenido())) {
                contenidoAActivar.add(bean);
            }
        }
        return contenidoAActivar;
    }

    public static void agregarContenidoNuevo(int id_plataforma, List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> catalogoStreamingStudioFiltrado, List<ContenidoCatalogoBean> contenidoPlataformaActivo) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaContenidoActivo = new HashSet<>();
        for (ContenidoCatalogoBean bean : contenidoPlataformaActivo) {
            idEnPlataformaContenidoActivo.add(bean.getId_contenido());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaCatalogoActual = new HashSet<>();
        for (ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean bean : catalogoStreamingStudioFiltrado) {
            idEnPlataformaCatalogoActual.add(bean.getId_contenido());
        }

        // Elimino los ids de nuestro catalogo en la lista de ids de la plataforma de streaming
        idEnPlataformaContenidoActivo.removeAll(idEnPlataformaCatalogoActual);

        // Filtro los beans de aquellos ids que están en la plataforma de streaming y no están en nuestro catalogo
        List<ContenidoCatalogoBean> contenidoAAgregar = contenidoPlataformaActivo.stream()
                .filter(contenido -> idEnPlataformaContenidoActivo.contains(contenido.getId_contenido()))
                .toList();

        // Por cada contenido
        for (ContenidoCatalogoBean contenido : contenidoAAgregar) {
            String id_contenido = contenido.getId_contenido();
            String titulo = contenido.getTitulo();
            String descripcion = contenido.getDescripcion();
            String url_imagen = contenido.getUrl_imagen();
            String clasificacion = contenido.getClasificacion();
            boolean reciente = contenido.isReciente();
            boolean destacado = contenido.isDestacado();

            // Buscamos si existe en la tabla Contenido
            SqlParameterSource in_existe_contenido = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido);
            SimpleJdbcCall jdbcCall_existe_contenido = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Buscar_Contenido")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall_existe_contenido.execute(in_existe_contenido);
            List<Map<String, Integer>> resultset = (List<Map<String, Integer>>) out.get("#result-set-1");
            Integer existe_contenido = resultset.getFirst().get("contenido");

            // Si no existe, lo creamos
            if (existe_contenido == 0) {
                SqlParameterSource in_crear_contenido = new MapSqlParameterSource()
                        .addValue("id_contenido", id_contenido)
                        .addValue("titulo", titulo)
                        .addValue("descripcion", descripcion)
                        .addValue("url_imagen", url_imagen)
                        .addValue("clasificacion", clasificacion);
                SimpleJdbcCall jdbcCall_crear_contenido = new SimpleJdbcCall(jdbcTpl)
                        .withProcedureName("Crear_Contenido")
                        .withSchemaName("dbo");
                jdbcCall_crear_contenido.execute(in_crear_contenido);

                List<DirectorBean> directores = contenido.getDirectores();
                for (DirectorBean directorBean : directores) {
                    SqlParameterSource in_director = new MapSqlParameterSource()
                            .addValue("id_contenido", id_contenido)
                            .addValue("id_director", directorBean.getId_director());
                    SimpleJdbcCall jdbcCall_director = new SimpleJdbcCall(jdbcTpl)
                            .withProcedureName("Asignar_Director")
                            .withSchemaName("dbo");
                    jdbcCall_director.execute(in_director);
                }

                List<ActorBean> actores = contenido.getActores();
                for (ActorBean actor : actores) {
                    SqlParameterSource in_actor = new MapSqlParameterSource()
                            .addValue("id_contenido", id_contenido)
                            .addValue("id_actor", actor.getId_actor());
                    SimpleJdbcCall jdbcCall_actor = new SimpleJdbcCall(jdbcTpl)
                            .withProcedureName("Asignar_Actor")
                            .withSchemaName("dbo");
                    jdbcCall_actor.execute(in_actor);
                }

                List<GeneroBean> generos = contenido.getGeneros();
                for (GeneroBean genero : generos) {
                    SqlParameterSource in_genero = new MapSqlParameterSource()
                            .addValue("id_contenido", id_contenido)
                            .addValue("id_genero", genero.getId_genero());
                    SimpleJdbcCall jdbcCall_genero = new SimpleJdbcCall(jdbcTpl)
                            .withProcedureName("Asignar_Genero")
                            .withSchemaName("dbo");
                    jdbcCall_genero.execute(in_genero);
                }
            }

            // Agregamos dicho contenido a la tabla catálogo
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma)
                    .addValue("reciente", reciente)
                    .addValue("destacado", destacado);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Agregar_Item_al_Catalogo")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    public static void actualizarContenido(int id_plataforma, List<ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean> catalogoStreamingStudioFiltrado, CatalogoBean catalogoDePlataforma) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaPlataformaStreaming = new HashSet<>();
        List<ContenidoCatalogoBean> contenidos = new ArrayList<>(catalogoDePlataforma.getListaContenido());
        for (ContenidoCatalogoBean bean : contenidos) {
            idEnPlataformaPlataformaStreaming.add(bean.getId_contenido());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaStreamingStudio = new HashSet<>();
        for (ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean bean : catalogoStreamingStudioFiltrado) {
            idEnPlataformaStreamingStudio.add(bean.getId_contenido());
        }

        // Me quedo con los ids de la plataforma de streaming que coinciden con nuestro catalogo
        idEnPlataformaPlataformaStreaming.retainAll(idEnPlataformaStreamingStudio);

        // Filtro los beans de aquellos ids que están en la plataforma de streaming y en nuestro catalogo
        List<ContenidoCatalogoBean> contenidoAActualizar = catalogoDePlataforma.getListaContenido().stream()
                .filter(contenido -> idEnPlataformaPlataformaStreaming.contains(contenido.getId_contenido()))
                .toList();

        // Actualizamos el catalogo
        for (ContenidoCatalogoBean contenido : contenidoAActualizar) {
            String id_contenido = contenido.getId_contenido();
            boolean reciente = contenido.isReciente();
            boolean destacado = contenido.isDestacado();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma)
                    .addValue("reciente", reciente)
                    .addValue("destacado", destacado);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Actualizar_Catalogo")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        jdbcTpl = crearJdbcTemplate();
        actualizarCatalogo();
    }
}
