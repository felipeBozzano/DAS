package ar.edu.ubp.das.streamingstudio.sstudio.controllers.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    HomeRepository homeRepository;

    @GetMapping("/home")
    public ResponseEntity<Map<String, Map<?, List<?>>>> mostrarHome(@RequestParam("id_cliente") int id_cliente){
        return new ResponseEntity<>(homeRepository.getHome(id_cliente), HttpStatus.OK);
    }

}
