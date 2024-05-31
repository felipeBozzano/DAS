package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@Component
public class SoapConnector extends AbstractConnector {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
    private final Gson gson;

    public SoapConnector() {
        beanFactory = new AbstractBeanFactory();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gson = gsonBuilder.create();
    }

    public AbstractBean execute_post_request(String url, Map<String, String> body, String return_bean) throws InstantiationException, IllegalAccessException {
        String message = body.get("message");
        String web_service = body.get("web_service");
        AbstractBean bean;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            Document resultDocument;
            Document sourceDocument;

            try {
                DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
                resultDocument = docBuilder.newDocument();
                sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }

            Source source = new DOMSource(sourceDocument);
            Result result = new DOMResult(resultDocument);
            webServiceTemplate.sendSourceAndReceiveToResult(url, source, result);

            String json = resultDocument.getElementsByTagName(web_service).item(0).getFirstChild().getNodeValue();
            bean = beanFactory.obtenerBeanDesdeJson(return_bean, json, gson);
            return bean;
        }
        catch (Exception e) {
            bean = beanFactory.obtenerBean(return_bean);
            bean.setCodigoRespuesta(-1);
            bean.setMensajeRespuesta("Unexpected Error");
            return bean;
        }
    }
}
