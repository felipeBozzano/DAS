package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.AutorizacionRepository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.ClienteRepository;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AutorizacionRepository autorizacionRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @PostMapping("/obtener_codigo_de_transaccion")
    public ResponseEntity<VerificacionTransaccionBean> crearTransaccion(@RequestBody TransaccionBean transaccionBean) {
        // Verificar que el partner esté registrado
        if (!partnerRepository.verificarTokenDePartner(transaccionBean.getToken_de_servicio())) {
            VerificacionTransaccionBean respuesta = new VerificacionTransaccionBean(false);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(autorizacionRepository.crearTransaccion(transaccionBean.getTipo_de_transaccion()),HttpStatus.OK);
    }

    @GetMapping("/verificar_autorizacion")
    public ResponseEntity<AutorizacionBean> verificarAutorizacion(@RequestParam("codigo_de_transaccion") String codigoTransaccion) {
        return new ResponseEntity<>(autorizacionRepository.verificarAutorizacion(codigoTransaccion), HttpStatus.OK);
    }

    @GetMapping("/crear_autorizacion")
    public ResponseEntity<AutorizacionBean> login(@RequestBody Map<String, String> body) {
        int id_cliente = Integer.parseInt(body.get("id_cliente"));
        String codigoTransaccion = body.get("codigo_transaccion");
        autorizacionRepository.crearAutorizacion(id_cliente, codigoTransaccion);
        String url_de_redireccion = autorizacionRepository.obtenerUrlDeRedireccion(codigoTransaccion);

        AutorizacionBean autorizacion = new AutorizacionBean(codigoTransaccion, id_cliente, url_de_redireccion);

        // REDIRIGIR AL URL DE REDIRECCION DE STREAMING STUDIO
        // La información que devuelve este endpoint es para redirigir a streaming studio
        // redirect(url_de_redireccion).body(id_cliente_plataforma, codigo_de_transaccion)

        return new ResponseEntity<>(autorizacion, HttpStatus.OK);
    }

    @PostMapping(
            path="/create_user",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(clienteRepository.createUser(cliente), HttpStatus.CREATED);
    }

    @PostMapping("/usuario/{id_cliente}/obtener_token")
    public ResponseEntity<VereficacionAutorizacionBean> obtenerToken(@PathVariable("id_cliente") Integer id_cliente,
                                                            @RequestBody AutorizacionBean autorizacionBean) {
        if (!partnerRepository.verificarTokenDePartner(autorizacionBean.getToken_de_servicio())) {
            VereficacionAutorizacionBean respuesta = new VereficacionAutorizacionBean(false);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        String token = autorizacionRepository.obtenerToken(id_cliente, autorizacionBean.getCodigo_de_transaccion());
        VereficacionAutorizacionBean respuesta = new VereficacionAutorizacionBean(true, token);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
