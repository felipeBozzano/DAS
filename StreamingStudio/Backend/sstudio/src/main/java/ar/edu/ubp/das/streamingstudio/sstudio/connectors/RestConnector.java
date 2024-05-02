package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.BaseBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.Bean1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RestConnector extends AbstractConnector{
    @Override
    public BaseBean execute_post_request(String url, Map<String, String> body, String header, String return_bean)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> pClass;
        pClass = Class.forName("ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans." + return_bean);

        if ("bean1".equals(return_bean)) {
            pClass = Class.forName("ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.Bean1");
        }
        else {
            throw new ClassNotFoundException();
        }

        Constructor<?> pConstructor = pClass.getDeclaredConstructor();
        BaseBean bean = (BaseBean) pConstructor.newInstance();
        return bean;
    }
}
