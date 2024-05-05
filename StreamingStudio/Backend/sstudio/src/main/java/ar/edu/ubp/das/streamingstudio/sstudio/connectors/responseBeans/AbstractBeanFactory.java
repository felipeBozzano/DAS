package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;
import java.util.HashMap;
import java.util.Map;

public class AbstractBeanFactory {

    private Map<String, Class<? extends AbstractBean>> beanMap;

    public AbstractBeanFactory() {
        beanMap = new HashMap<>();
        // Asocia el nombre de la clase con el tipo de objeto correspondiente
        beanMap.put("ComenzarFederacionBean", ComenzarFederacionBean.class);
        // Agrega más asociaciones según sea necesario para tus otros beans
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

    public Class<? extends AbstractBean> obtenerClase(String nombreBean){
        return  beanMap.get(nombreBean);
    }

//    public abstract BaseBean execute_post_request(String url, Map<String,String> body, String header, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
