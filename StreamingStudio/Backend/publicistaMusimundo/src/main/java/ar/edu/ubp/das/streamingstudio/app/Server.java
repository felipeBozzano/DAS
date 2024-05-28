package ar.edu.ubp.das.streamingstudio.app;

import ar.edu.ubp.das.streamingstudio.platforms.PublicistaMusimundoWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        PublicistaMusimundoWS implementor = new PublicistaMusimundoWS();
        String address = "http://localhost:8086/publicistaMusimundo";
        Endpoint.publish(address, implementor);
    }
}
