package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {

    @GetMapping("/obtener_token")
    public String geToken() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        return uuidString;
    }


}
