package ar.edu.ubp.das.streamingstudio.sstudio.connectors;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBean;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.AbstractBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
public class SoapConnector extends AbstractConnector {
    public SoapConnector(){
        beanFactory = new AbstractBeanFactory();
    }
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    public AbstractBean execute_post_request(String url, Map<String, String> body, String return_bean) throws InstantiationException, IllegalAccessException {
        String message = body.get("message");

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

        String resultXml = convertDocumentToString((Document) ((DOMResult) result).getNode());
        return convertStringToBean(resultXml, return_bean);
    }

    private String convertDocumentToString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error converting Document to String", e);
        }
    }

    public AbstractBean convertStringToBean(String xml, String return_bean) {
        try {
            JAXBContext context = JAXBContext.newInstance(beanFactory.obtenerClase(return_bean));
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (AbstractBean) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new RuntimeException("Error converting String to Bean", e);
        }
    }

//    public PublicidadResponseBean obtenerPublicidades(String url, Map<String, String> body, String return_bean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        AbstractBean bean = beanFactory.obtenerBean(return_bean);
//        String message = """
//                   <ws:url xmlns:ws=url + >
//                   <return_bean>%s</return_bean>
//                   </ws:obtener_publicidades>""".formatted(body);
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        documentBuilderFactory.setNamespaceAware(true);
//        Document resultDocument;
//        Document sourceDocument;
//        try {
//            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
//            resultDocument = (Document) docBuilder.newDocument();
//            sourceDocument = (Document) docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
//        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (SAXException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        Source source = new DOMSource((Node) sourceDocument);
//        Result result = new DOMResult((Node) resultDocument);
//
//        webServiceTemplate.sendSourceAndReceiveToResult(url, source, result);
//        PublicidadResponseBean responseBean = new PublicidadResponseBean();
//        responseBean.setUrl_de_imagen(((org.w3c.dom.Document) resultDocument).getElementsByTagName("url_imagen").item(0).getFirstChild().getNodeValue());
//        //responseBean.setFecha_de_alta(((org.w3c.dom.Document) resultDocument).getElementsByTagName("fecha_alta").item(0).getFirstChild().getNodeValue());
//        //responseBean.setFecha_de_baja(((org.w3c.dom.Document) resultDocument).getElementsByTagName("fecha_baja").item(0).getFirstChild().getNodeValue());
//        responseBean.setUrl_de_publicidad(((org.w3c.dom.Document) resultDocument).getElementsByTagName("url_de_ublicidad").item(0).getFirstChild().getNodeValue());
//
//        return responseBean;
//    }

}
