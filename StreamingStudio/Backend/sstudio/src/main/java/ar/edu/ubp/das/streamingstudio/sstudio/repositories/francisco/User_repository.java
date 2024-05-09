package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Repository
public class User_repository {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("usuario", cliente.getUsuario())
                .addValue("contrasena", cliente.getcontrasena())
                .addValue("email", cliente.getEmail())
                .addValue("nombre", cliente.getNombre())
                .addValue("apellido", cliente.getApellido())
                .addValue("valido", true);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ClienteUsuarioBean>)out.get("Crear_Usuario");
    }

    @Transactional
    public int verificarUsuario(String usuario, String contrasena) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("contrasena", contrasena);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Login_Usuario")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer resultado = resulset.getFirst().get("ExisteUsuario");
        return resultado;
    }

    @Transactional
    public Integer verificarUsuarioConRetardo(String usuario, String contrasena) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        // Simulación de una operación asíncrona que tarda 2 segundos en completarse
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                int resultado = verificarUsuario(usuario, contrasena);
                future.complete(resultado);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        // Espera hasta que el futuro se complete y obtén el resultado
        try {
            return future.get(); // Obtiene el resultado del CompletableFuture
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error al obtener el resultado del CompletableFuture", e);
        }
    }

    public CompletableFuture<Integer> verificarUsuarioConRetardoo(String usuario, String contrasena) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

        // Crear un ScheduledExecutorService
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Programar la ejecución de la tarea después de 2 segundos
        executorService.schedule(() -> {
            try {
                int resultado = verificarUsuario(usuario, contrasena); // Obtener el resultado de la verificación
                future.complete(resultado); // Completar el futuro con el resultado
            } catch (Exception e) {
                future.completeExceptionally(e); // Completar el futuro con una excepción si ocurre un error
            }
        }, 2, TimeUnit.SECONDS); // Retraso de 2 segundos

        // Detener el executorService después de completar la tarea
        executorService.shutdown();

        return future; // Devolver el futuro
    }


    @Transactional
    public List<ClienteUsuarioBean> getUser(String email) {
        return jdbcTpl.query("SELECT * FROM dbo.Cliente_Usuario WHERE email = ?",new Object[]{email}, BeanPropertyRowMapper.newInstance(ClienteUsuarioBean.class)
        );
    }

    public List<PlataformaDeStreamingBean> obtenerFederaciones(int id_cliente) {
        return List.of();
    }
}
