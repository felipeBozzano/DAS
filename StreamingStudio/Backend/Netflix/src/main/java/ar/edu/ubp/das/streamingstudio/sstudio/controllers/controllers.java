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
        path = "/netflix")

public class controllers {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AutorizacionRepository autorizacionRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    CatalogoRepository catalogoRepository;

    @Autowired
    EstadisticasRepository estadisticasRepository;

    @Autowired
    SesionRepository sesionRepository;

    @Autowired
    ContenidoRepository contenidoRepository;

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
            respuesta.put("mensaje", "Usuario logueado");
        } else {
            respuesta.put("mensaje", "Usuario no existente");
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(
            path = "/create_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        Map<String, String> respuesta = new HashMap<>();
        if (clienteRepository.createUser(cliente)) {
            Map<String, Integer> info_usuario = clienteRepository.informacion_usuario(cliente.getEmail(), cliente.getcontrasena());
            respuesta.put("id_cliente", String.valueOf(info_usuario.get("id_cliente")));
            respuesta.put("nombre", String.valueOf(info_usuario.get("nombre")));
            respuesta.put("apellido", String.valueOf(info_usuario.get("apellido")));
            respuesta.put("email", String.valueOf(info_usuario.get("email")));
            respuesta.put("mensaje", "Usuario registrado");
        } else {
            respuesta.put("mensaje", "No se pudo registrar el cliente");
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/obtener_codigo_de_transaccion")
    public ResponseEntity<VerificacionTransaccionBean> crearTransaccion(@RequestBody TransaccionBean transaccionBean) {
        // Verificar que el partner esté registrado
        if (!partnerRepository.verificarTokenDePartner(transaccionBean.getToken_de_servicio())) {
            VerificacionTransaccionBean respuesta = new VerificacionTransaccionBean(false);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(autorizacionRepository.crearTransaccion(transaccionBean.getTipo_de_transaccion(), transaccionBean.getUrl_de_redireccion()), HttpStatus.OK);
    }

    @GetMapping("/verificar_autorizacion")
    public ResponseEntity<AutorizacionBean> verificarAutorizacion(@RequestParam("codigo_de_transaccion") String codigoTransaccion) {
        return new ResponseEntity<>(autorizacionRepository.verificarAutorizacion(codigoTransaccion), HttpStatus.OK);
    }

    @PostMapping(
            path = "/crear_autorizacion",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AutorizacionBean> crear_autorizacion(@RequestBody AutorizacionBean autorizacion) {
        autorizacionRepository.crearAutorizacion(autorizacion.getId_cliente(), autorizacion.getCodigo_de_transaccion());
        String url_de_redireccion = autorizacionRepository.obtenerUrlDeRedireccion(autorizacion.getCodigo_de_transaccion());
        AutorizacionBean nueva_autorizacion = new AutorizacionBean(autorizacion.getCodigo_de_transaccion(), autorizacion.getId_cliente(), url_de_redireccion);
        return new ResponseEntity<>(nueva_autorizacion, HttpStatus.OK);
    }

    @PostMapping("/obtener_token")
    public ResponseEntity<VerificacionAutorizacionBean> obtenerToken(@RequestBody AutorizacionBean autorizacionBean) {
        if (!partnerRepository.verificarTokenDePartner(autorizacionBean.getToken_de_servicio())) {
            VerificacionAutorizacionBean respuesta = new VerificacionAutorizacionBean(false);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        String token = autorizacionRepository.obtenerToken(autorizacionBean.getCodigo_de_transaccion());
        VerificacionAutorizacionBean respuesta = new VerificacionAutorizacionBean(true, token);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/catalogo")
    public ResponseEntity<CatalogoBean> catalogo() {
        CatalogoBean respuesta = catalogoRepository.obtenerCatalogo();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/contenido")
    public ResponseEntity<List<ContenidoBean>> contenido() {
        List<ContenidoBean> respuesta = catalogoRepository.obtenerContenido();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/directores")
    public ResponseEntity<List<DirectorBean>> directores(@RequestParam String id_contenido) {
        List<DirectorBean> respuesta = catalogoRepository.obtenerDirectores(id_contenido);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/actores")
    public ResponseEntity<List<ActorBean>> actores(@RequestParam String id_contenido) {
        List<ActorBean> respuesta = catalogoRepository.obtenerActores(id_contenido);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(path = "/obtenerEstadisticas",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> registrarReporteEstadisticas(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(estadisticasRepository.registrarReporteEstadisticas(body), HttpStatus.OK);
    }

    @PostMapping(path = "/crear_sesion",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> obtenerSesion(@RequestBody Map<String, String> body) {
        if (!partnerRepository.verificarTokenDePartner(body.get("token_de_partner")))
            return new ResponseEntity<>(partnerRepository.respuestaPartnerIncorrecto(), HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(sesionRepository.obtenerSesion(body), HttpStatus.OK);
    }

    @PostMapping(path = "/obtener_url_de_contenido",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> obtenerUrlDeContenido(@RequestBody Map<String, String> body) {
        if (!partnerRepository.verificarTokenDePartner(body.get("token_de_partner")))
            return new ResponseEntity<>(partnerRepository.respuestaPartnerIncorrecto(), HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(contenidoRepository.obtenerUrlDeContenido(body), HttpStatus.OK);
    }

    @PostMapping(path = "/desvincular",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> desvincular(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        return new ResponseEntity<>(autorizacionRepository.desvincular(token), HttpStatus.NOT_ACCEPTABLE);
    }
}

// COMENTARIO PARA FORZAR RECREACION DE PROYECTO
