package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class RestConnector extends AbstractConnector {

    public RestConnector(){
        beanFactory = new AbstractBeanFactory();
    }

    @Override
    public AbstractBean execute_post_request(String url, Map<String, String> body, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        ObjectMapper objectMapper = new ObjectMapper();
        AbstractBean bean;
        try {
            String bodyString = objectMapper.writeValueAsString(body);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            bean = objectMapper.readValue((String) response.body(), beanFactory.obtenerClase(return_bean));
            return bean;
        } catch (Exception e) {
            bean = beanFactory.obtenerBean(return_bean);
            bean.setStatus(-1);
            bean.setMessage("Unexpected Error");
            return bean;
        }
    }
}
