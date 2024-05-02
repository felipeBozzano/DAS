package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.BaseBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class AbstractConnector {
    public abstract BaseBean execute_post_request(String url, Map<String,String> body, String header, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
