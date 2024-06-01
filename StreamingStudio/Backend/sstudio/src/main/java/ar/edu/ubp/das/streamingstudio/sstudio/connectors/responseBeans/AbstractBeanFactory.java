package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoCatalogoBean;
import java.util.HashMap;
import java.util.Map;

public class AbstractBeanFactory {

    private final Map<String, Class<? extends AbstractBean>> beanMap;

    public AbstractBeanFactory() {
        beanMap = new HashMap<>();
        // Asocia el nombre de la clase con el tipo de objeto correspondiente
        beanMap.put("FederacionBean", FederacionBean.class);
        beanMap.put("ContenidoCatalogoBean", ContenidoCatalogoBean.class);
        beanMap.put("SesionBean", SesionBean.class);
        beanMap.put("ContenidoUrlBean", ContenidoUrlBean.class);
        beanMap.put("ListaPublicidadResponseBean", ListaPublicidadResponseBean.class);
        beanMap.put("CatalogoBean", CatalogoBean.class);
        beanMap.put("MensajeBean", MensajeBean.class);
    }

    public AbstractBean obtenerBean(String beanName) throws InstantiationException, IllegalAccessException {
        // Obtiene la clase correspondiente al nombre del bean
        Class<? extends AbstractBean> beanClass = beanMap.get(beanName);
        if (beanClass != null) {
            // Crea una instancia del objeto correspondiente utilizando reflexión
            return beanClass.newInstance();
        } else {
            // Manejo de error si el nombre del bean no está en el mapa
            throw new IllegalArgumentException("Bean no encontrado: " + beanName);
        }
    }

    public Class<? extends AbstractBean> obtenerClase(String nombreBean) {
        return beanMap.get(nombreBean);
    }

    public AbstractBean obtenerBeanDesdeJson(String beanName, String json, Gson gson) throws InstantiationException, IllegalAccessException {
        Class<? extends AbstractBean> beanClass = beanMap.get(beanName);
        if (beanClass != null) {
            // Crea una instancia del objeto correspondiente utilizando reflexión
            return gson.fromJson(json, beanClass);
        } else {
            // Manejo de error si el nombre del bean no está en el mapa
            throw new IllegalArgumentException("Bean no encontrado: " + beanName);
        }
    }
}
