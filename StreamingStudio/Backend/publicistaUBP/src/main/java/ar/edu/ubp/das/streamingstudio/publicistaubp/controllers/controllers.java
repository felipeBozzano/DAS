package ar.edu.ubp.das.streamingstudio.publicistaubp.controllers;

import ar.edu.ubp.das.streamingstudio.publicistaubp.models.EstadisticasBean;
import ar.edu.ubp.das.streamingstudio.publicistaubp.models.PublicidadBean;
import ar.edu.ubp.das.streamingstudio.publicistaubp.repositories.EstadisticasRepository;
import ar.edu.ubp.das.streamingstudio.publicistaubp.repositories.PublicidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(
        path = "/ubp")

public class controllers {
    @Autowired
    PublicidadesRepository publicidadesRepository;

    @Autowired
    EstadisticasRepository estadisticasRepository;

    @PostMapping("/obtener_publicidades")
    public ResponseEntity<List<PublicidadBean>> obtenerPublicidades() {
        return new ResponseEntity<>(publicidadesRepository.exponerPublicidades(), HttpStatus.OK);
    }

    @PostMapping(path = "/registrar_estadisticas",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> registrarReporteEstadisticas(EstadisticasBean estadisticas) {
        return new ResponseEntity<>(estadisticasRepository.registrarReporteEstadisticas(estadisticas), HttpStatus.OK);
    }
}

//jaxb-api