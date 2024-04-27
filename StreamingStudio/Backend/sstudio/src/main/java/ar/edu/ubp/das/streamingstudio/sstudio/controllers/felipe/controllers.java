package ar.edu.ubp.das.streamingstudio.sstudio.controllers.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.CatalogoRepository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.EstadisticasRepository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.HomeRepository;
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

    @Autowired
    CatalogoRepository catalogoRepository;

    @Autowired
    EstadisticasRepository estadisticasRepository;

    @GetMapping("/home")
    public ResponseEntity<Map<String, Map<Integer, List<?>>>> getTipoFee(@RequestParam("id_cliente") int id_cliente){
        return new ResponseEntity<>(homeRepository.getHome(id_cliente), HttpStatus.OK);
    }

    @GetMapping("/catalogo")
    public ResponseEntity<String> getCatalogo(){
        return new ResponseEntity<>(catalogoRepository.actualizarCatalogo(), HttpStatus.OK);
    }

    @GetMapping("/estadisticasPublicistas")
    public ResponseEntity<String> getEstadisticas(){
        return new ResponseEntity<String>(estadisticasRepository.reportesPublicistas(), HttpStatus.OK);
    }
}
