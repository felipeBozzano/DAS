package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoCatalogoBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.obtenerInformacionDeConexionAPlataforma;

@Repository
public class Test {
    private static JdbcTemplate jdbcTpl;
    static Map<String,String> respuesta;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();
    public static List<ContenidoCatalogoBean> obtenerCatalogoDePlataforma(int id_plataforma) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<String, String> conexion_plataforma = obtenerInformacionDeConexionAPlataforma(id_plataforma, jdbcTpl);
        AbstractConnector conector = conectorFactory.crearConector(conexion_plataforma.get("protocolo_api"));
        Map<String, String> body = new HashMap<>();
        body.put("token_de_servicio", conexion_plataforma.get("token_de_servicio"));

        List<ContenidoCatalogoBean> catalogo = new ArrayList<>();

        if (id_plataforma == 1) {
            CatalogoBean catalogos = (CatalogoBean) conector.execute_post_request(conexion_plataforma.get("url_api") + "/catalogo", body, "CatalogoBean");

            System.out.println("catalogos: " + catalogos);

            ContenidoCatalogoBean bean_1 = new ContenidoCatalogoBean("150", "Pelicula1",
                    "Descripción de Pelicula1", "url_imagen1.jpg", "S", true,
                    true, true);
            catalogo.add(bean_1);
        } else if (id_plataforma == 2) {
            ContenidoCatalogoBean bean_2 = new ContenidoCatalogoBean("109", "Pelicula6",
                    "Descripción de Pelicula6", "url_imagen9.jpg", "S", true,
                    false, true);
            catalogo.add(bean_2);
        } else {
            ContenidoCatalogoBean bean_3 = new ContenidoCatalogoBean("101", "Serie5",
                    "Descripción de Serie5", "url_imagen1.jpg", "S", false,
                    true, true);
            catalogo.add(bean_3);
        }
        return catalogo;
    }
}
