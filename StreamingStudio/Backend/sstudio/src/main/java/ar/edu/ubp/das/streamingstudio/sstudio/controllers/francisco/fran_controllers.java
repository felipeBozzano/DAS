package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.Enviar_facturas_repository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.FederarClienteRepository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco.User_repository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
@RequestMapping(
        path = "/ss")

public class fran_controllers {

    @Autowired
    User_repository user_repository;

    @Autowired
    FederarClienteRepository federar_clienteRepository;

    @Autowired
    Enviar_facturas_repository enviar_facturas_repository;


    @PostMapping(
            path="/create_user",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(user_repository.createUser(cliente), HttpStatus.CREATED);
    }

    @PostMapping(
            path="/login_user",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String,String>> loginUsuario(@RequestBody ClienteUsuarioBean cliente) {
        int user = user_repository.verificarUsuario(cliente.getUsuario(), cliente.getcontrasena());
        Map<String, String> respuesta = new HashMap<>();
        if(user == 1){
            respuesta.put("mensaje", "Usuario existente");
        }else {
            respuesta.put("mensaje", "Usuario no existente");
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(
            path="/usuario/{id_cliente}/mi_perfil"
    )
    public ResponseEntity<ClienteUsuarioBean> verPerfilUsuario(@PathVariable("id_cliente") Integer id_cliente) {
        return new ResponseEntity<>(user_repository.obtenerInformacionUsuario(id_cliente), HttpStatus.OK);
    }

    /* Federacion usaurio*/

    @GetMapping(
            path="/usuario/{id_cliente}/federaciones"
    )
    public ResponseEntity<Map<String, List<PlataformaDeStreamingBean>>> verFederaciones(@PathVariable("id_cliente") Integer id_cliente) {
        return new ResponseEntity<>(user_repository.obtenerFederaciones(id_cliente), HttpStatus.OK);
    }

    @PostMapping(
            path="/usuario/{id_cliente}/comenzar_federacion/{id_plataforma}/{tipo_de_transaccion}"
    )
    public ResponseEntity<Map<String, String>> federarClientePlataforma(@PathVariable("id_plataforma") Integer id_plataforma,
                                                                        @PathVariable("id_cliente") Integer id_cliente,
                                                                        @PathVariable("tipo_de_transaccion") String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return new ResponseEntity<>(federar_clienteRepository.federarClientePlataforma(id_plataforma, id_cliente, tipo_transaccion), HttpStatus.OK);
    }

    @PostMapping(
            value="/usuario/{id_cliente}/finalizar_federacion/{id_plataforma}",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> finalizarFederacion(@PathVariable("id_plataforma") Integer id_plataforma,
                                                                   @PathVariable("id_cliente") Integer id_cliente,
                                                                   @RequestBody Map<String,String> body,
                                                                   HttpServletResponse response) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String codigo_de_transaccion = body.get("codigo_de_transaccion");
        int id_cliente_plataforma = Integer.parseInt(body.get("id_cliente_plataforma"));
        Map<String, String> respuesta = federar_clienteRepository.finalizarFederacion(id_plataforma, id_cliente, codigo_de_transaccion, id_cliente_plataforma);

        // Construye la URL de redirección con id_cliente
        String urlRedireccion = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/usuario/" + id_cliente + "/federaciones")
                .buildAndExpand(id_cliente)
                .toUriString();

        // Redirige al cliente a la nueva URL
        response.setHeader("Location", urlRedireccion);
        response.setStatus(302);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /* Facturacion */

    @GetMapping("/datos_publiciadad")
    public ResponseEntity<List<PublicidadBean>> getPublicadades() {
        return new ResponseEntity<>(enviar_facturas_repository.buscarDatoPublicidades(), HttpStatus.OK);
    }

    @GetMapping("/costo_banner")
    public ResponseEntity<Double> getCostoBanner(@RequestParam("id_banner") int id_banner) {
        return new ResponseEntity<>(enviar_facturas_repository.obtenerCostoDeBanner(id_banner), HttpStatus.OK);
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
