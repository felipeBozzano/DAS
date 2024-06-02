package ar.edu.ubp.das.streamingstudio.sstudio.controllers;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.ContenidoUrlBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.*;
import ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.EnviarFacturasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(
        path = "/ss")

public class controllers {

    @Autowired
    HomeRepository home_repository;

    @Autowired
    ReproducirContenidoRepository reproducir_contenido_repository;

    @Autowired
    UsuarioClienteRepository user_repository;

    @Autowired
    FederarClienteRepository federar_clienteRepository;

    @Autowired
    EnviarFacturasRepository enviar_facturasRepository;

    @Autowired
    FiltrarContenidoRepository buscar_contenido_repository;

    @Autowired
    PublicidadesRepository publicidades_repository;


    /* ----------------------------------------------------------------------------------------------------- */
    /* ----------------------------------------------  Usuario  -------------------------------------------- */
    /* ----------------------------------------------------------------------------------------------------- */

    @PostMapping(
            path = "/create_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(user_repository.createUser(cliente), HttpStatus.CREATED);
    }

    @PostMapping(
            path = "/login_user",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> loginUsuario(@RequestBody ClienteUsuarioBean cliente) {
        int user = user_repository.verificarUsuario(cliente.getEmail(), cliente.getcontrasena());
        Map<String, String> respuesta = new HashMap<>();
        if (user == 1) {
            Map<String, Integer> info_usuario = user_repository.informacion_usuario(cliente.getEmail(), cliente.getcontrasena());
            respuesta.put("id_cliente", String.valueOf(info_usuario.get("id_cliente")));
            respuesta.put("nombre", String.valueOf(info_usuario.get("nombre")));
            respuesta.put("apellido", String.valueOf(info_usuario.get("apellido")));
            respuesta.put("email", String.valueOf(info_usuario.get("email")));
            respuesta.put("mensaje", "Usuario existente");
        }else {
            respuesta.put("mensaje", "Usuario no existente");
        }
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(
            path = "/usuario/{id_cliente}/mi_perfil"
    )
    public ResponseEntity<ClienteUsuarioBean> verPerfilUsuario(@PathVariable("id_cliente") Integer id_cliente) {
        return new ResponseEntity<>(user_repository.obtenerInformacionUsuario(id_cliente), HttpStatus.OK);
    }

    /* ----------------------------------------------------------------------------------------------------- */
    /* ----------------------------------------  Federacion_Usuario  --------------------------------------- */
    /* ----------------------------------------------------------------------------------------------------- */

    @PostMapping(
            path = "/usuario/federaciones",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, List<PlataformaDeStreamingBean>>> verFederaciones(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>(user_repository.obtenerFederaciones(Integer.parseInt(body.get("id_cliente"))), HttpStatus.OK);
    }

    @PostMapping(
            path = "/usuario/comenzar_federacion",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> federarClientePlataforma(@RequestBody Map<String, String> body) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int id_plataforma = Integer.parseInt(body.get("id_plataforma"));
        int id_cliente = Integer.parseInt(body.get("id_cliente"));
        String tipo_de_transaccion = body.get("tipo_de_transaccion");

        return new ResponseEntity<>(federar_clienteRepository.federarClientePlataforma(id_plataforma, id_cliente, tipo_de_transaccion), HttpStatus.OK);
    }

    @PostMapping(
            value = "/usuario/{id_cliente}/finalizar_federacion/{id_plataforma}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Map<String, String>> finalizarFederacion(@RequestBody Map<String, String> body) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String codigo_de_transaccion = body.get("codigo_de_transaccion");
        int id_cliente = Integer.parseInt(body.get("id_cliente"));
        int id_plataforma = Integer.parseInt(body.get("id_plataforma"));
        Map<String, String> respuesta = federar_clienteRepository.finalizarFederacion(id_plataforma, id_cliente, codigo_de_transaccion, true);

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

//    /* ----------------------------------------------------------------------------------------------------- */
//    /* --------------------------------------------  Facturacion  ------------------------------------------ */
//    /* ----------------------------------------------------------------------------------------------------- */
//
//    @GetMapping("/datos_publiciadad")
//    public ResponseEntity<List<PublicidadFacturasBean>> getPublicadades() {
//        return new ResponseEntity<>(enviar_facturasRepository.buscarDatoPublicidades(), HttpStatus.OK);
//    }
//
//    @GetMapping("/costo_banner")
//    public ResponseEntity<Double> getCostoBanner(@RequestParam("id_banner") int id_banner) {
//        return new ResponseEntity<>(enviar_facturasRepository.obtenerCostoDeBanner(id_banner), HttpStatus.OK);
//    }
//
//    @GetMapping("/enviar_facturas_publicistas")
//    public ResponseEntity<String> enviar_facturacion_publicistas() {
//        return new ResponseEntity<String>(enviar_facturasRepository.enviarFacturasPublicistas(), HttpStatus.OK);
//    }
//
//    @GetMapping("/enviar_facturas_plataformas")
//    public ResponseEntity<String> enviar_facturacion_plataformas() {
//        return new ResponseEntity<String>(enviar_facturasRepository.enviarFacturasPlataformas(), HttpStatus.OK);
//    }
//
//    @GetMapping("/enviar_facturas_plataforma")
//    public ResponseEntity<List<FederacionBean>> facturacion_plataforma() {
//        return new ResponseEntity<>(enviar_facturasRepository.buscarDatosFederaciones(), HttpStatus.OK);
//    }

    /* ----------------------------------------------------------------------------------------------------- */
    /* ---------------------------------- Buscar contenido por filtros --------------------------------------*/
    /* ----------------------------------------------------------------------------------------------------- */
    @PostMapping(
            path = "/contenido_por_filtros"
    )
    public ResponseEntity<List<ContenidoHomeBean>> buscarContenidoPorFiltros(@RequestBody ContenidoFiltroBean body) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(body);
        List<ContenidoHomeBean> contenido = buscar_contenido_repository.buscarContenidoPorFiltros(body.getId_cliente(), body.getTitulo(), body.isReciente(), body.isDestacado(), body.getClasificacion(), body.getMas_visto(), body.getGenero());
        return new ResponseEntity<>(contenido, HttpStatus.OK);
    }

    @GetMapping(
            path = "/informacion_contenido/{id_contenido}/{id_cliente}"
    )
    public ResponseEntity<InformacionContenidoBean> obtenerInformacionContenido(@PathVariable("id_contenido") String id_contenido, @PathVariable("id_cliente") int id_cliente) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        InformacionContenidoBean infoContenido = buscar_contenido_repository.informacionContenido(id_contenido, id_cliente);
        return new ResponseEntity<>(infoContenido, HttpStatus.OK);
    }

    @PostMapping(
            path = "/series"
    )
    public ResponseEntity<List<SerieBean>> obtenerSeries() {
        List<SerieBean> series = buscar_contenido_repository.obtenerSeries();
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

    @PostMapping(
            path = "/peliculas"
    )
    public ResponseEntity<List<PeliculaBean>> obtenerPeliculas() {
        List<PeliculaBean> peliculas = buscar_contenido_repository.obtenerPeliculas();
        return new ResponseEntity<>(peliculas, HttpStatus.OK);
    }


    /* ----------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------ Publicidades --------------------------------------------- */
    /* ----------------------------------------------------------------------------------------------------- */
    @GetMapping(
            path = "/publicidades_activas"
    )
    public ResponseEntity<Map<String, Map<Integer, List<PublicidadHomeBean>>>> obtenerPublicidadesActivas() {
        return new ResponseEntity<>(publicidades_repository.obtenerPublicidadesAgrupadas(), HttpStatus.OK);
    }

    @GetMapping(
            path = "/publicidades_activas1"
    )
    public ResponseEntity<List<PublicidadHomeBean>> obtenerPublicidadesActivas1() {
        return new ResponseEntity<>(publicidades_repository.obtenerPublicidades(), HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<Map<String, Map<String, ContenidoResponseHomeBean>>> mostrarHome(@RequestParam("id_cliente") int id_cliente) {
        return new ResponseEntity<>(home_repository.getHome(id_cliente), HttpStatus.OK);
    }

    /* ----------------------------------------------------------------------------------------------------- */
    /* -------------------------------------- Reproducir contenido ------------------------------------------*/
    /* ----------------------------------------------------------------------------------------------------- */

    @PostMapping(
            path = "/informacion_contenido/mostrar_video",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<ContenidoUrlBean> buscarContenidoPorFiltros(@RequestBody Map<String, String> body) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        String id_contenido = body.get("id_contenido");
        int id_plataforma = Integer.parseInt(body.get("id_plataforma"));
        int id_cliente = Integer.parseInt(body.get("id_cliente"));
        return new ResponseEntity<>(reproducir_contenido_repository.obtener_url_de_contenido(id_contenido, id_plataforma, id_cliente), HttpStatus.OK);
//        contenido.put("id_contenido", "P-1");
//        contenido.put("url", "<iframe width=\"1519\" height=\"569\" src=\"https://www.youtube.com/embed/YbrZc8YnagQ\" title=\"Toy Story Toons: Fiesta Saurus Rex\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>");
//        return new ResponseEntity<>(reproducir_contenido_repository.obtener_url_de_contenido(id_contenido, id_plataforma, id_cliente), HttpStatus.OK);
    }
}
