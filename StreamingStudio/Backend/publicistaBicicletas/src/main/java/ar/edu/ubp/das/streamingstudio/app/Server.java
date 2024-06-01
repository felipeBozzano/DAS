package ar.edu.ubp.das.streamingstudio.app;

import ar.edu.ubp.das.streamingstudio.platforms.PublicistaBicicletasWS;
import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        PublicistaBicicletasWS implementor = new PublicistaBicicletasWS();
        String address = "http://localhost:8087/publicistaBicicletas";
        Endpoint.publish(address, implementor);
    }
}
