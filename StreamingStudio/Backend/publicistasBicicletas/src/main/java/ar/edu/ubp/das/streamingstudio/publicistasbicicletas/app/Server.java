package ar.edu.ubp.das.streamingstudio.publicistasbicicletas.app;

import ar.edu.ubp.das.streamingstudio.publicistasbicicletas.platforms.PublicidadBicicletasWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        PublicidadBicicletasWS implementor = new PublicidadBicicletasWS();
        String address = "http://localhost:8083/publicistaBicicletas";
        Endpoint.publish(address, implementor);
    }
}
