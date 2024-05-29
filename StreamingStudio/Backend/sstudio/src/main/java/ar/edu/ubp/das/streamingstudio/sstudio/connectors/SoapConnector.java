package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.PublicidadResponseBean;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SoapConnector extends AbstractConnector {

    public AbstractBean execute_post_request(String url, Map<String, String> body, String return_bean){
        AbstractBean bean = new AbstractBean() {};
        return bean;
    }

    public SoapConnector(){
        beanFactory = new AbstractBeanFactory();
    }
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();


    public PublicidadResponseBean obtenerPublicidades(String url, Map<String, String> body, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractBean bean = beanFactory.obtenerBean(return_bean);
        String message = """
                   <ws:url xmlns:ws=url + >
                   <return_bean>%s</return_bean>
                   </ws:obtener_publicidades>""".formatted(body);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = (Document) docBuilder.newDocument();
            sourceDocument = (Document) docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        Source source = new DOMSource((Node) sourceDocument);
        Result result = new DOMResult((Node) resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(url, source, result);
        PublicidadResponseBean responseBean = new PublicidadResponseBean();
        responseBean.setUrl_de_imagen(((org.w3c.dom.Document) resultDocument).getElementsByTagName("url_imagen").item(0).getFirstChild().getNodeValue());
        //responseBean.setFecha_de_alta(((org.w3c.dom.Document) resultDocument).getElementsByTagName("fecha_alta").item(0).getFirstChild().getNodeValue());
        //responseBean.setFecha_de_baja(((org.w3c.dom.Document) resultDocument).getElementsByTagName("fecha_baja").item(0).getFirstChild().getNodeValue());
        responseBean.setUrl_de_publicidad(((org.w3c.dom.Document) resultDocument).getElementsByTagName("url_de_ublicidad").item(0).getFirstChild().getNodeValue());

        return responseBean;
    }

}
