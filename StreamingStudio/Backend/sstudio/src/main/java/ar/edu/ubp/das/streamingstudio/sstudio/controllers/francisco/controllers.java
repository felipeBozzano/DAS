package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
import ar.edu.ubp.das.streamingstudio.sstudio.service.fran.TipoFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    TipoFeeService tipo_fee_service;
    @GetMapping("/obtener_tipo_fee")
    public  ResponseEntity<List<Tipo_de_Fee>> lista(){
        List<Tipo_de_Fee> lista = tipo_fee_service.lista();
        return new ResponseEntity(lista, HttpStatus.OK);
    }
}
