package ar.edu.ubp.das.streamingstudio.publicistaubp.controllers;

import ar.edu.ubp.das.streamingstudio.publicistaubp.models.PublicidadResponseBean;
import ar.edu.ubp.das.streamingstudio.publicistaubp.repositories.EstadisticasRepository;
import ar.edu.ubp.das.streamingstudio.publicistaubp.repositories.PublicidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(
        path = "/publicistaUBP")

public class controllers {
    @Autowired
    PublicidadesRepository publicidadesRepository;

    @Autowired
    EstadisticasRepository estadisticasRepository;

    @PostMapping(path = "/obtenerPublicidades",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PublicidadResponseBean> obtenerPublicidades(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(publicidadesRepository.obtenerPublicidades(body), HttpStatus.OK);
    }

    @PostMapping(path = "/obtenerEstadisticas",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> registrarReporteEstadisticas(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(estadisticasRepository.registrarReporteEstadisticas(body), HttpStatus.OK);
    }
}
