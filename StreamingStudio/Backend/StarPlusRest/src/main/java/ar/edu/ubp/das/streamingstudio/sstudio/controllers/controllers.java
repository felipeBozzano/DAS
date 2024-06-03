package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.*;
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
        path = "/start_plus")

public class controllers {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AutorizacionRepository autorizacionRepository;

    @PostMapping(
            path = "/login_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> loginUsuario(@RequestBody ClienteUsuarioBean cliente) {
        int user = clienteRepository.verificarUsuario(cliente.getEmail(), cliente.getcontrasena());
        Map<String, String> respuesta = new HashMap<>();
        if (user == 1) {
            Map<String, Integer> info_usuario = clienteRepository.informacion_usuario(cliente.getEmail(), cliente.getcontrasena());
            respuesta.put("id_cliente", String.valueOf(info_usuario.get("id_cliente")));
            respuesta.put("nombre", String.valueOf(info_usuario.get("nombre")));
            respuesta.put("apellido", String.valueOf(info_usuario.get("apellido")));
            respuesta.put("email", String.valueOf(info_usuario.get("email")));
            respuesta.put("mensaje", "Usuario existente");
        } else {
            respuesta.put("mensaje", "Usuario no existente");
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(
            path = "/create_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(clienteRepository.createUser(cliente), HttpStatus.CREATED);
    }

    @PostMapping(path = "/crear_autorizacion", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AutorizacionBean> crear_autorizacion(@RequestBody AutorizacionBean autorizacion) {
        autorizacionRepository.crearAutorizacion(autorizacion.getId_cliente(), autorizacion.getCodigo_de_transaccion());
        String url_de_redireccion = autorizacionRepository.obtenerUrlDeRedireccion(autorizacion.getCodigo_de_transaccion());
        AutorizacionBean nueva_autorizacion = new AutorizacionBean(autorizacion.getCodigo_de_transaccion(), autorizacion.getId_cliente(), url_de_redireccion);
        return new ResponseEntity<>(nueva_autorizacion, HttpStatus.OK);
    }


}
