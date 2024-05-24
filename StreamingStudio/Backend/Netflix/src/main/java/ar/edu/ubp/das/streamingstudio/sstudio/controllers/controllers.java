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
import org.springframework.web.servlet.view.RedirectView;

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
    public  ResponseEntity<AutorizacionBean> login(@RequestParam("id_cliente") int id_cliente, @RequestParam("codigo_transaccion") String codigo_transaccion) {
        autorizacionRepository.crearAutorizacion(id_cliente, codigo_transaccion);
        String url_de_redireccion = autorizacionRepository.obtenerUrlDeRedireccion(codigo_transaccion);
        AutorizacionBean autorizacion = new AutorizacionBean(codigo_transaccion, id_cliente, url_de_redireccion);
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
