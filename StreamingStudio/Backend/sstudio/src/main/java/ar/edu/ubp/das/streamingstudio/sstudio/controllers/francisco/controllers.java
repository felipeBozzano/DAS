package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.TipoFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    TipoFeeRepository repository;

    @GetMapping("/obtener_tipo_fee")
    public ResponseEntity<List<Tipo_de_Fee>> getTipoFee(@RequestParam("id_tipo_de_fee") int id_tipo_fee){
        return new ResponseEntity<>(repository.getTipoFee(id_tipo_fee), HttpStatus.OK);
    }
}
