package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {

    @GetMapping("/obtener_token")
    public ResponseEntity<String> geToken() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        System.out.println(uuidString);
        return new ResponseEntity<String>(uuidString,HttpStatus.OK);
    }


}
