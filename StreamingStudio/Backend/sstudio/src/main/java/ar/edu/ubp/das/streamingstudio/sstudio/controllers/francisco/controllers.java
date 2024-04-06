package ar.edu.ubp.das.streamingstudio.sstudio.controllers.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
import ar.edu.ubp.das.streamingstudio.sstudio.repositories.TipoFeeRepository;
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

public class controllers {

    @Autowired
    TipoFeeRepository repository;

    @GetMapping("/obtener_tipo_fee")
    public ResponseEntity<List<Tipo_de_Fee>> getTipoFee(@RequestParam("id_tipo_de_fee") int id_tipo_fee){
        return new ResponseEntity<>(repository.getTipoFee(id_tipo_fee), HttpStatus.OK);
    }

    @PostMapping(
            path="/create_user",
            consumes={MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<ClienteUsuarioBean>> createUser(@RequestBody ClienteUsuarioBean cliente) {
        return new ResponseEntity<>(repository.createUser(cliente), HttpStatus.CREATED);
    }

    @GetMapping("/obtener_usuario")
    public ResponseEntity<List<ClienteUsuarioBean>> getUser(@RequestParam("email") String email) {
        return new ResponseEntity<>(repository.getUser(email), HttpStatus.OK);
    }

    @DeleteMapping("/borrar_usuario")
    public ResponseEntity<Integer> deleteUser(@RequestParam("email") String email) {
        return new ResponseEntity<>(repository.deleteUser(email), HttpStatus.OK);
    }
}
