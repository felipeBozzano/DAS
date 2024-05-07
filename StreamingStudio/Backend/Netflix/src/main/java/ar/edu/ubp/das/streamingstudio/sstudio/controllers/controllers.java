package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import ar.edu.ubp.das.streamingstudio.sstudio.models.AutorizacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {

    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping("/federar")
    public ResponseEntity<Map<String, String>> federarCliente(@RequestBody AutorizacionBean autorizacionBean) {
        Map<String, String> respuesta = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String url = "http://localhost:8081/netflix/login";
        respuesta.put("codigoTransaccion", uuidString);
        respuesta.put("url", url);
        clienteRepository.autorizarCliente(autorizacionBean.getId_cliente(), autorizacionBean.getToken());
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }

    @PostMapping("/login")
    public void login(@RequestBody Map<String,String> body) {
        String codigo_de_transaccion = body.get("codigo_de_transaccion");

        // HACE EL LOGIN Y DEVUELVE UN id_cliente

        // CREAR UN TOKEN UNICO E INSERTAR UNA FILA EN LA TABLA AUTORIZACIÓN CON EL ID_CLIENTE
        // Y EL CODIGO_DE_TRANSACCIÓN OBTENIDOS ANTERIORMENTE

        // EN BASE AL CODIGO DE TRANSACCION, BUSCAR EN LA TABLA TRANSACCION EL URL DE REDIRECCION
        // DE STREAMING STUDIO

        // REDIRIGIR AL URL DE REDIRECCION DE STREAMING STUDIO
    }

    @PostMapping("/usuario/{id_cliente}/obtener_token")
    public ResponseEntity<Map<String, String>> obtenerToken() {
        Map<String, String> respuesta = new HashMap<>();
        UUID token = UUID.randomUUID();
        String tokenString = token.toString();
        respuesta.put("token", tokenString);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
