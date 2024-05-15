package ar.edu.ubp.das.streamingstudio.sstudio.controllers.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.*;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.FederacionesPendientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    HomeRepository homeRepository;

    @Autowired
    CatalogoRepository catalogoRepository;

    @Autowired
    EstadisticasPublicistaRepository estadisticasRepository;

    @Autowired
    EstadisticasPlataformaRepository estadisticasPlataformasRepository;

    @GetMapping("/home")
    public ResponseEntity<Map<String, Map<?, List<?>>>> mostrarHome(@RequestParam("id_cliente") int id_cliente){
        return new ResponseEntity<>(homeRepository.getHome(id_cliente), HttpStatus.OK);
    }

    @GetMapping("/catalogo")
    public ResponseEntity<String> obtenerCatalogo() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ResponseEntity<>(catalogoRepository.actualizarCatalogo(), HttpStatus.OK);
    }

    @GetMapping("/estadisticasPublicistas")
    public ResponseEntity<String> getEstadisticasPublicistas(){
        return new ResponseEntity<>(estadisticasRepository.reportesPublicistas(), HttpStatus.OK);
    }

    @GetMapping("/estadisticasPlataformas")
    public ResponseEntity<String> getEstadisticasPlataformas(){
        return new ResponseEntity<>(estadisticasPlataformasRepository.reportesPlataformas(), HttpStatus.OK);
    }
}
