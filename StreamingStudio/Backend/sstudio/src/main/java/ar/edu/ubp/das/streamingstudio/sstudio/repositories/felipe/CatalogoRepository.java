package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CatalogoRepository implements ICatalogoRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Override
    public void actualizarCatalogo() {
        List<CatalogoBean> catalogoStreamingStudio = obtenerCatalogo();
        List<PlataformaDeStreamingBean> plataformasActivas = obtenerPlataformasActivas();
        for (PlataformaDeStreamingBean plataforma : plataformasActivas) {

            // Obtenemos el catálogo de la plataforma de streaming
            int id_plataforma = plataforma.getId_plataforma();
            String tokenDePlataforma = obtenerTokenDeServicioDePlataforma(id_plataforma);
            List<ContenidoBean> catalogoDePlataforma = obtenerCatalogoDePlataforma(id_plataforma, tokenDePlataforma);

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
            List<ContenidoBean> contenidoPlataformaInactivo = catalogoDePlataforma.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() != null && contenido.getFecha_de_baja().after(contenido.getFecha_de_alta()))
                    .toList();

            // Filtro el contenido de la plataforma de streaming que esta activo
            List<ContenidoBean> contenidoPlataformaActivo = catalogoDePlataforma.stream()
                    .filter(contenido -> contenido.getFecha_de_baja() == null || contenido.getFecha_de_alta().after(contenido.getFecha_de_baja()))
                    .toList();

            // Buscamos el contenido que la plataforma de streaming dio de baja, para darlo de baja en nuestra plataforma
            darDeBajaContenido(contenidoStreamingStudioActivo, contenidoPlataformaInactivo);

            // Buscamos el contenido dado de baja en Streaming Studio para revisar si tenemos que habilitarlo
            habilitarContenido(contenidoStreamingStudioInactivo, contenidoPlataformaActivo);

            // Buscamos el contenido de la plataforma de streaming que no está en Streaming Studio y lo cargamos
            agregarContenidoNuevo(catalogoStreamingStudioFiltrado, contenidoPlataformaActivo);

            // Buscamos el contenido que esté en ambas plataformas y lo actualizamos si cambió
            actualizarContenido(catalogoStreamingStudioFiltrado, catalogoDePlataforma);
        }
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
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        return out.get("token").toString();
    }

    @Override
    public List<ContenidoBean> obtenerCatalogoDePlataforma(int id_plataforma, String tokenDeServicio) {
        return List.of();
    }

    @Override
    public void darDeBajaContenido(List<CatalogoBean> contenidoStreamingStudioActivo, List<ContenidoBean> contenidoPlataformaInactivo) {

        // Crear un HashSet con los id_en_plataforma de contenidoInactivo
        Set<String> idEnPlataformaContenidoInactivo = new HashSet<>();
        for (ContenidoBean bean : contenidoPlataformaInactivo) {
            idEnPlataformaContenidoInactivo.add(bean.getId_en_plataforma());
        }

        // Tomo los contenidos activos de nuestro catalogo que están inactivos en el catálogo de la plataforma
        List<CatalogoBean> contenidoADarDeBaja = new ArrayList<>();
        for (CatalogoBean bean : contenidoStreamingStudioActivo) {
            if (idEnPlataformaContenidoInactivo.contains(bean.getId_en_plataforma())) {
                contenidoADarDeBaja.add(bean);
            }
        }

        // Doy de baja dichos contenidos
        for (CatalogoBean contenido : contenidoADarDeBaja) {
            int id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Dar_de_Baja_Item_en_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
            System.out.println(out);
        }
    }

    @Override
    public void habilitarContenido(List<CatalogoBean> contenidoStreamingStudioInactivo, List<ContenidoBean> contenidoPlataformaActivo) {

        // Crear un HashSet con los id_en_plataforma de contenidoActivo
        Set<String> idEnPlataformaContenidoActivo = new HashSet<>();
        for (ContenidoBean bean : contenidoPlataformaActivo) {
            idEnPlataformaContenidoActivo.add(bean.getId_en_plataforma());
        }

        // Tomo los contenidos inactivos de nuestro catalogo que están activos en el catálogo de la plataforma
        List<CatalogoBean> contenidoAActivar = new ArrayList<>();
        for (CatalogoBean bean : contenidoStreamingStudioInactivo) {
            if (idEnPlataformaContenidoActivo.contains(bean.getId_en_plataforma())) {
                contenidoAActivar.add(bean);
            }
        }

        // Habilito dichos contenidos
        for (CatalogoBean contenido : contenidoAActivar) {
            int id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Activar_Item_en_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
            System.out.println(out);
        }
    }

    @Override
    public void agregarContenidoNuevo(List<CatalogoBean> catalogoStreamingStudioFiltrado, List<ContenidoBean> contenidoPlataformaActivo) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaContenidoActivo = new HashSet<>();
        for (ContenidoBean bean : contenidoPlataformaActivo) {
            idEnPlataformaContenidoActivo.add(bean.getId_en_plataforma());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaCatalogoActual = new HashSet<>();
        for (CatalogoBean bean : catalogoStreamingStudioFiltrado) {
            idEnPlataformaCatalogoActual.add(bean.getId_en_plataforma());
        }

        // Elimino los ids de nuestro catalogo en la lista de ids de la plataforma de streaming
        idEnPlataformaContenidoActivo.removeAll(idEnPlataformaCatalogoActual);

        // Filtro los beans de aquellos ids que están en la plataforma de streaming y no están en nuestro catalogo
        List<ContenidoBean> contenidoAAgregar = contenidoPlataformaActivo.stream()
                .filter(contenido -> idEnPlataformaContenidoActivo.contains(contenido.getId_en_plataforma()))
                .toList();


        // Por cada contenido
        for (ContenidoBean contenido: contenidoAAgregar) {

            int id_contenido = contenido.getId_contenido();
            String titulo = contenido.getTitulo();
            String id_plataforma = contenido.getId_en_plataforma();
            String url_imagen = contenido.getUrl_imagen();
            int clasificacion = contenido.getClasificacion();
            boolean reciente = contenido.getReciente();
            boolean destacado = contenido.getDestacado();
            String id_en_plataforma = contenido.getId_en_plataforma();

            // Buscamos si existe en la tabla Contenido
            SqlParameterSource in_existe_contenido = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido);
            SimpleJdbcCall jdbcCall_existe_contenido = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Buscar_Contenido")
                    .withSchemaName("dbo");
            Map<String, Object> out_existe_contenido = jdbcCall_existe_contenido.execute(in_existe_contenido);
            List<Map<String, Integer>> resultset = (List<Map<String, Integer>>) out_existe_contenido.get("#result-set-1");
            Integer existe_contenido = resultset.get(0).get("contenido");
            System.out.println(existe_contenido);

            // Si no existe, lo creamos
            if (existe_contenido == 0) {
                SqlParameterSource in_crear_contenido = new MapSqlParameterSource()
                        .addValue("id_contenido", id_contenido)
                        .addValue("titulo", titulo)
                        .addValue("id_plataforma", id_plataforma)
                        .addValue("url_imagen", url_imagen)
                        .addValue("clasificacion", clasificacion);
                SimpleJdbcCall jdbcCall_crear_contenido = new SimpleJdbcCall(jdbcTpl)
                        .withProcedureName("Crear_Contenido")
                        .withSchemaName("dbo");
                Map<String, Object> out_crear_contenido = jdbcCall_crear_contenido.execute(in_crear_contenido);
                System.out.println(out_crear_contenido);
            }

            // Agregamos dicho contenido a la tabla catálogo
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma)
                    .addValue("reciente", reciente)
                    .addValue("destacado", destacado)
                    .addValue("id_en_plataforma", id_en_plataforma);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Agregar_Item_al_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
            System.out.println(out);
        }
    }

    @Override
    public void actualizarContenido(List<CatalogoBean> catalogoStreamingStudioFiltrado, List<ContenidoBean> catalogoDePlataforma) {

        // Crear un HashSet con los id_en_plataforma de la plataforma de streaming
        Set<String> idEnPlataformaPlataformaStreaming = new HashSet<>();
        for (ContenidoBean bean : catalogoDePlataforma) {
            idEnPlataformaPlataformaStreaming.add(bean.getId_en_plataforma());
        }

        // Crear un HashSet con los id_en_plataforma de nuestro catalogo actual
        Set<String> idEnPlataformaStreamingStudio = new HashSet<>();
        for (CatalogoBean bean : catalogoStreamingStudioFiltrado) {
            idEnPlataformaStreamingStudio.add(bean.getId_en_plataforma());
        }

        // Me quedo con los ids de la plataforma de streaming que coinciden con nuestro catalogo
        idEnPlataformaPlataformaStreaming.retainAll(idEnPlataformaStreamingStudio);

        // Filtro los beans de aquellos ids que están en la plataforma de streaming y en nuestro catalogo
        List<ContenidoBean> contenidoAActualizar = catalogoDePlataforma.stream()
                .filter(contenido -> idEnPlataformaPlataformaStreaming.contains(contenido.getId_en_plataforma()))
                .toList();

        // Actualizamos el catalogo
        for (ContenidoBean contenido: contenidoAActualizar) {
            int id_contenido = contenido.getId_contenido();
            int id_plataforma = contenido.getId_plataforma();
            boolean reciente = contenido.getReciente();
            boolean destacado = contenido.getDestacado();
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", id_contenido)
                    .addValue("id_plataforma", id_plataforma)
                    .addValue("reciente", reciente)
                    .addValue("destacado", destacado);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Actualizar_Catalogo")
                    .withSchemaName("dbo");
            Map<String, Object> out = jdbcCall.execute(in);
            System.out.println(out);
        }
    }
}
