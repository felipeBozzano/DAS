package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class AbstractConnector {
    protected AbstractBeanFactory beanFactory;
    public abstract AbstractBean execute_post_request(String url, Map<String,String> body, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;


}
