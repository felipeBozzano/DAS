package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import ar.edu.ubp.das.streamingstudio.sstudio.models.AutorizacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.TransaccionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(
        path = "/netflix")

public class controllers {

    private Map<String, String> respuesta;

    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping("/federar")
    public ResponseEntity<Map<String, String>> federarCliente(@RequestBody TransaccionBean transaccionBean) {
        respuesta = new HashMap<>();
        if (!clienteRepository.verificarTokenDePartner(transaccionBean.getToken_de_servicio())) {
            respuesta.put("mensaje", "Partner no identificado");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        UUID codigo_de_transaccion = UUID.randomUUID();
        String codigo_de_transaccion_string = codigo_de_transaccion.toString();
        String url;
        if (transaccionBean.getTipo_de_transaccion().equals("L"))
            url = "http://localhost:8081/netflix/login";
        else
            url = "http://localhost:8081/netflix/register";
        clienteRepository.crearTransaccion(codigo_de_transaccion_string, transaccionBean.getUrl_de_redireccion(),
                transaccionBean.getTipo_de_transaccion());

        respuesta.put("codigoTransaccion", codigo_de_transaccion_string);
        respuesta.put("url", url);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("codigo_de_transaccion") String codigoTransaccion) {
        // VERIFICAR QUE NO HAYA UNA AUTORIZACION CREADA CON ESE CODIGO DE TRANSACCION
        AutorizacionBean autorizacion = clienteRepository.verificarAutorizacion(codigoTransaccion);
        String url_de_redireccion = autorizacion.getUrl_de_redireccion();
        int id_cliente_plataforma = autorizacion.getId_cliente();

        if (url_de_redireccion == null) {
            // HACE EL LOGIN Y DEVUELVE UN id_cliente
            id_cliente_plataforma = 1;

            // CREAR UN TOKEN UNICO E INSERTAR UNA FILA EN LA TABLA AUTORIZACIÓN CON EL ID_CLIENTE
            // Y EL CODIGO_DE_TRANSACCIÓN OBTENIDOS ANTERIORMENTE
            UUID token = UUID.randomUUID();
            String token_string = token.toString();
            clienteRepository.crearAutorizacion(id_cliente_plataforma, codigoTransaccion, token_string);

            // EN BASE AL CODIGO DE TRANSACCION, BUSCAR EN LA TABLA TRANSACCION EL URL DE REDIRECCION
            // DE STREAMING STUDIO
            url_de_redireccion = clienteRepository.obtenerUrlDeRedireccion(codigoTransaccion);
        }

        // REDIRIGIR AL URL DE REDIRECCION DE STREAMING STUDIO
        // redirect(url_de_redireccion).body(id_cliente_plataforma, codigo_de_transaccion)

        respuesta = new HashMap<>();
        respuesta.put("mensaje", "Autorizacion creada");
        respuesta.put("id_cliente_plataforma", String.valueOf(id_cliente_plataforma));
        respuesta.put("codigo_de_transaccion", codigoTransaccion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/usuario/{id_cliente}/obtener_token")
    public ResponseEntity<Map<String, String>> obtenerToken(@PathVariable("id_cliente") Integer id_cliente,
                                                            @RequestBody AutorizacionBean autorizacionBean) {
        respuesta = new HashMap<>();
        if (!clienteRepository.verificarTokenDePartner(autorizacionBean.getToken_de_servicio())) {
            respuesta.put("mensaje", "Partner no identificado");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_ACCEPTABLE);
        }

        String token = clienteRepository.obtenerToken(id_cliente, autorizacionBean.getCodigo_de_transaccion());
        respuesta.put("token", token);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
