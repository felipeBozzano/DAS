package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Fee;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.Enviar_facturas_repository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.Federar_cliente_repository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.User_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers_francisco {

    @Autowired
    User_repository user_repository;

    @Autowired
    Federar_cliente_repository federar_cliente_repository;

    @Autowired
    Enviar_facturas_repository enviar_facturas_repository;


    @PostMapping(
            path="/create_user",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(user_repository.createUser(cliente), HttpStatus.CREATED);
    }

    @GetMapping("/obtener_usuario")
    public ResponseEntity<List<ClienteUsuarioBean>> getUser(@RequestParam("email") String email) {
        return new ResponseEntity<>(user_repository.getUser(email), HttpStatus.OK);
    }

     /* Federacion usaurio*/

    @PostMapping(
            path="/federar_cliente",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Integer> federarClientePlataforma(@RequestBody FederacionBean federacion) {
        return new ResponseEntity<>(federar_cliente_repository.federarClientePlataforma(federacion.getId_plataforma(), federacion.getId_cliente()), HttpStatus.OK);
    }

    /* Facturacion */

    @GetMapping("/datos_publiciadad")
    public ResponseEntity<List<PublicidadBean>> getPublicadades() {
        return new ResponseEntity<>(federar_cliente_repository.buscarDatoPublicidades(), HttpStatus.OK);
    }

    @GetMapping("/costo_banner")
    public ResponseEntity<Double> getCostoBanner(@RequestParam("id_banner") int id_banner) {
        return new ResponseEntity<>(federar_cliente_repository.obtenerCostoDeBanner(id_banner), HttpStatus.OK);
    }

    @GetMapping("/enviar_facturas_publicistas")
    public ResponseEntity<String> enviar_facturacion_publicistas() {
        return new ResponseEntity<String>(enviar_facturas_repository.enviarFacturasPublicistas(), HttpStatus.OK);
    }

    @GetMapping("/enviar_facturas_plataformas")
    public ResponseEntity<String> enviar_facturacion_plataformas() {
        return new ResponseEntity<String>(enviar_facturas_repository.enviarFacturasPlataformas(), HttpStatus.OK);
    }

    @GetMapping("/enviar_facturas_plataforma")
    public ResponseEntity<List<FederacionBean>> facturacion_plataforma() {
        return new ResponseEntity<>(enviar_facturas_repository.buscarDatosFederaciones(), HttpStatus.OK);
    }

    @GetMapping("/obtener_fees_plataforma")
    public ResponseEntity<List<Fee>> obtener_fees_plataforma(@RequestParam("id_plataforma") int id_plataforma) {
        return new ResponseEntity<>(enviar_facturas_repository.obtenerFeesPlataforma(id_plataforma), HttpStatus.OK);
    }


}
