package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class AbstractConnectorFactory {
    public AbstractConnectorFactory() {
    
    }

    public AbstractConnector crearConector (String nombre) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Class<?> clase;
        if("SOAP".equals(nombre)) {
            clase = Class.forName("ar.edu.ubp.das.streamingstudio.sstudio.connectors.SoapConnector");
        } else if ("REST".equals(nombre)) {
            clase = Class.forName("ar.edu.ubp.das.streamingstudio.sstudio.connectors.RestConnector");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> constructor = clase.getDeclaredConstructor();
        AbstractConnector conector = (AbstractConnector) constructor.newInstance();
        return conector;
    }
}
