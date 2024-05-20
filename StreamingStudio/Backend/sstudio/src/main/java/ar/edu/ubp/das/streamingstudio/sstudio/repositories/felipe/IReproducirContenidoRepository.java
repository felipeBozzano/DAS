package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.ContenidoUrlBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.SesionBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface IReproducirContenidoRepository {
    Map<String, String> obtener_url_de_contenido(String id_contenido, int id_plataforma, int id_cliente) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    String obtenerTokenDeFederacion(int id_cliente, int id_plataforma);
    Map<String, String> obtenerInformacionDeConexionAPlataforma(int id_plataforma);
    SesionBean obtenerSesion(Map<String, String> conexion_plataforma, String token_de_usuario, AbstractConnector conector) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    ContenidoUrlBean obtenerUrlDeContenido(Map<String, String> conexion_plataforma, String token_de_sesion, String id_contenido, AbstractConnector conector) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
