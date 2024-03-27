package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.TipoFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    TipoFeeRepository repository;

    @GetMapping("/obtener_tipo_fee")
    public ResponseEntity<Map<String,Object>> lista(){
        return new ResponseEntity<>(repository.getTipoFee(1), HttpStatus.OK);
    }
}
