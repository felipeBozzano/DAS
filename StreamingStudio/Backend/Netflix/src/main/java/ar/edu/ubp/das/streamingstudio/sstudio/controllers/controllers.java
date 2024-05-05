package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {

    @PostMapping("/obtener_token")
    public ResponseEntity<Map<String, String>> geToken() {
        Map<String, String> respuesta = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String url = "http://localhost:8081/netflix/login";
        respuesta.put("codigoTransaccion", uuidString);
        respuesta.put("url", url);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
