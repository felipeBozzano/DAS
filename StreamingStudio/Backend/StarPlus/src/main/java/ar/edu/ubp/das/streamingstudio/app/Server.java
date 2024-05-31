package ar.edu.ubp.das.streamingstudio.app;

import ar.edu.ubp.das.streamingstudio.platforms.StarPlusWS;
import jakarta.xml.ws.Endpoint;

import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        StarPlusWS implementor = new StarPlusWS();
        String address = "http://localhost:8084/star_plus";
        Endpoint.publish(address, implementor);
    }
}
