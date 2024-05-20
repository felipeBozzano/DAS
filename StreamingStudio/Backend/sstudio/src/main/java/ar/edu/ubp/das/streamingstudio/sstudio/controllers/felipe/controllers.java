package ar.edu.ubp.das.streamingstudio.sstudio.controllers.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoFiltroBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    ReproducirContenidoRepository reproducirContenidoRepository;

    @GetMapping("/home")
    public ResponseEntity<Map<String, Map<String, List<ContenidoHomeBean>>>> mostrarHome(@RequestParam("id_cliente") int id_cliente) {
        return new ResponseEntity<>(homeRepository.getHome(id_cliente), HttpStatus.OK);
    }

    /* ----------------------------------------------------------------------------------------------------- */
    /* -------------------------------------- Reproducir contenido ------------------------------------------*/
    /* ----------------------------------------------------------------------------------------------------- */

    @PostMapping(
            path = "/informacion_contenido/{id_contenido}/plataforma/{id_plataforma}"
    )
    public ResponseEntity<Map<String, String>> buscarContenidoPorFiltros(@PathVariable("id_contenido") String id_contenido, @PathVariable("id_plataforma") int id_plataforma, @RequestParam("id_cliente") int id_cliente) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ResponseEntity<>(reproducirContenidoRepository.obtener_url_de_contenido(id_contenido, id_plataforma, id_cliente), HttpStatus.OK);
    }

}
