package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoCatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Repository
public class CatalogoRepository implements ICatalogoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;
    private final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    @Override
    public String actualizarCatalogo() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<CatalogoBean> catalogoStreamingStudio = obtenerCatalogo();
        List<PlataformaDeStreamingBean> plataformasActivas = obtenerPlataformasActivas();
        for (PlataformaDeStreamingBean plataforma : plataformasActivas) {

            // Obtenemos el catálogo de la plataforma de streaming
            int id_plataforma = plataforma.getId_plataforma();
            String tokenDePlataforma = obtenerTokenDeServicioDePlataforma(id_plataforma);
            List<ContenidoCatalogoBean> catalogoDePlataforma = obtenerCatalogoDePlataforma(id_plataforma, tokenDePlataforma);

            // Filtramos nuestro catalogo para obtener solo el contenido referente a esta plataforma de streaming
            List<CatalogoBean> catalogoStreamingStudioFiltrado = catalogoStreamingStudio.stream()
                    .filter(contenido -> contenido.getId_plataforma() == id_plataforma)
                    .toList();


            // Filtramos el contenido de nuestra plataforma que está activo
            List<CatalogoBean> contenidoStreamingStudioActivo = catalogoStreamingStudioFiltrado.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() == null || contenido.getFecha_de_alta().after(contenido.getFecha_de_baja()))
                    .toList();

            // Filtramos el contenido de nuestra plataforma que está inactivo
            List<CatalogoBean> contenidoStreamingStudioInactivo = catalogoStreamingStudioFiltrado.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() != null && contenido.getFecha_de_baja().after(contenido.getFecha_de_alta()))
                    .toList();

            // Filtramos el contenido de la plataforma de streaming que esta inactivo
            List<ContenidoCatalogoBean> contenidoPlataformaInactivo = catalogoDePlataforma.stream()
                    .filter(contenido -> !contenido.isValido())
                    .toList();

            // Filtro el contenido de la plataforma de streaming que esta activo
            List<ContenidoCatalogoBean> contenidoPlataformaActivo = catalogoDePlataforma.stream()
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
        return "OK";
    }

    @Override
    public List<CatalogoBean> obtenerCatalogo() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Catalogo_Actual")
                .withSchemaName("dbo")
                .returningResultSet("catalogo", BeanPropertyRowMapper.newInstance(CatalogoBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<CatalogoBean>) out.get("catalogo");
    }

    @Override
    public List<PlataformaDeStreamingBean> obtenerPlataformasActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Plataformas_de_Streaming_Activas")
                .withSchemaName("dbo")
                .returningResultSet("plataforma", BeanPropertyRowMapper.newInstance(PlataformaDeStreamingBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PlataformaDeStreamingBean>) out.get("plataforma");
    }

    @Override
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        ;
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, String>> resultset = (List<Map<String, String>>) out.get("#result-set-1");
        String token = resultset.getFirst().get("token_de_servicio");
        return token;
    }

    @Override
    public List<ContenidoCatalogoBean> obtenerCatalogoDePlataforma(int id_plataforma, String tokenDeServicio) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//        AbstractConnector conector = conectorFactory.crearConector("REST");
//        Map<String, String> body = new HashMap<>();
//        body.put("token_de_servicio", obtenerTokenDeServicioDePlataforma(id_plataforma));
//        List<ContenidoCatalogoBean> catalogo = (List<ContenidoCatalogoBean>) conector.execute_post_request("http://localhost:8081/netflix/catalogo", body, "ContenidoCatalogoBean");
//        catalogo

        List<ContenidoCatalogoBean> catalogo = new ArrayList<>();
        if (id_plataforma == 1) {
            ContenidoCatalogoBean bean_1 = new ContenidoCatalogoBean("101", "Pelicula1",
                    "Descripción de Pelicula1", "url_imagen1.jpg", 2, false,
                    false, true);
            catalogo.add(bean_1);
        } else if (id_plataforma == 2) {
            ContenidoCatalogoBean bean_2 = new ContenidoCatalogoBean("109", "Pelicula6",
                    "Descripción de Pelicula6", "url_imagen9.jpg", 2, false,
                    true, true);
            catalogo.add(bean_2);
        } else {
            ContenidoCatalogoBean bean_3 = new ContenidoCatalogoBean("101", "Serie5",
                    "Descripción de Serie5", "url_imagen1.jpg", 2, true,
                    false, true);
            catalogo.add(bean_3);
        }
        return catalogo;
    }

    @Override
    public void darDeBajaContenido(List<CatalogoBean> contenidoStreamingStudioActivo, List<ContenidoCatalogoBean> contenidoPlataformaInactivo) {

        List<CatalogoBean> contenidoADarDeBaja = cruzarContenido(contenidoStreamingStudioActivo, contenidoPlataformaInactivo);

        // Doy de baja dichos contenidos
        for (CatalogoBean contenido : contenidoADarDeBaja) {
            String id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Dar_de_Baja_Item_en_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
        }
    }

    @Override
    public void habilitarContenido(List<CatalogoBean> contenidoStreamingStudioInactivo, List<ContenidoCatalogoBean> contenidoPlataformaActivo) {

        List<CatalogoBean> contenidoAActivar = cruzarContenido(contenidoStreamingStudioInactivo, contenidoPlataformaActivo);

        // Habilito dichos contenidos
        for (CatalogoBean contenido : contenidoAActivar) {
            String id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Activar_Item_en_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
        }
    }

    private static List<CatalogoBean> cruzarContenido(List<CatalogoBean> contenidoStreamingStudio, List<ContenidoCatalogoBean> contenidoPlataforma) {
        // Crear un HashSet con los id_en_plataforma
        Set<String> idContenidoPlataforma = new HashSet<>();
        for (ContenidoCatalogoBean bean : contenidoPlataforma) {
            idContenidoPlataforma.add(bean.getId_contenido());
        }

        // Cruzo los contenidos de ambos catálogos
        List<CatalogoBean> contenidoAActivar = new ArrayList<>();
        for (CatalogoBean bean : contenidoStreamingStudio) {
            if (idContenidoPlataforma.contains(bean.getId_contenido())) {
                contenidoAActivar.add(bean);
            }
        }
        return contenidoAActivar;
    }

    @Override
    public void agregarContenidoNuevo(int id_plataforma, List<CatalogoBean> catalogoStreamingStudioFiltrado, List<ContenidoCatalogoBean> contenidoPlataformaActivo) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaContenidoActivo = new HashSet<>();
        for (ContenidoCatalogoBean bean : contenidoPlataformaActivo) {
            idEnPlataformaContenidoActivo.add(bean.getId_contenido());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaCatalogoActual = new HashSet<>();
        for (CatalogoBean bean : catalogoStreamingStudioFiltrado) {
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
            int clasificacion = contenido.getClasificacion();
            boolean reciente = contenido.isReciente();
            boolean destacado = contenido.isDestacado();

            // Buscamos si existe en la tabla Contenido
            SqlParameterSource in_existe_contenido = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido);
            SimpleJdbcCall jdbcCall_existe_contenido = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Buscar_Contenido")
                    .withSchemaName("dbo");
            Map<String, Object> out_existe_contenido = jdbcCall_existe_contenido.execute(in_existe_contenido);
            List<Map<String, Integer>> resultset = (List<Map<String, Integer>>) out_existe_contenido.get("#result-set-1");
            Integer existe_contenido = resultset.get(0).get("contenido");

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
                Map<String, Object> out_crear_contenido = jdbcCall_crear_contenido.execute(in_crear_contenido);
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
            Map<String, Object> out = jdbcCall.execute(in);
        }
    }

    @Override
    public void actualizarContenido(int id_plataforma, List<CatalogoBean> catalogoStreamingStudioFiltrado, List<ContenidoCatalogoBean> catalogoDePlataforma) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaPlataformaStreaming = new HashSet<>();
        for (ContenidoCatalogoBean bean : catalogoDePlataforma) {
            idEnPlataformaPlataformaStreaming.add(bean.getId_contenido());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaStreamingStudio = new HashSet<>();
        for (CatalogoBean bean : catalogoStreamingStudioFiltrado) {
            idEnPlataformaStreamingStudio.add(bean.getId_contenido());
        }

        // Me quedo con los ids de la plataforma de streaming que coinciden con nuestro catalogo
        idEnPlataformaPlataformaStreaming.retainAll(idEnPlataformaStreamingStudio);

        // Filtro los beans de aquellos ids que están en la plataforma de streaming y en nuestro catalogo
        List<ContenidoCatalogoBean> contenidoAActualizar = catalogoDePlataforma.stream()
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
            Map<String, Object> out = jdbcCall.execute(in);
        }
    }
}
