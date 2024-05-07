package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SoapConnector extends AbstractConnector{
    public SoapConnector(){
        beanFactory = new AbstractBeanFactory();
    }

    @Override
    public AbstractBean execute_post_request(String url, Map<String, String> body, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractBean bean = beanFactory.obtenerBean(return_bean);
        return bean;
    }
}