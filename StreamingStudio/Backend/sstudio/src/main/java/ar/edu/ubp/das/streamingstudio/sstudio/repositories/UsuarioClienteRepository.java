package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

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

import java.util.*;

@Repository
public class UsuarioClienteRepository implements IUsuarioClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("contrasena", cliente.getcontrasena())
                .addValue("email", cliente.getEmail())
                .addValue("nombre", cliente.getNombre())
                .addValue("apellido", cliente.getApellido())
                .addValue("valido", true);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);

        List<ClienteUsuarioBean> usuario = (List<ClienteUsuarioBean>)out.get("Crear_Usuario");
        return usuario;
    }

    public Map<String, Integer> informacion_usuario(String email, String contrasena) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("contrasena", contrasena);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Informacion_Usuario")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Map<String, Integer> resultado = resulset.get(0);
        return resultado;
    }

    public int verificarUsuario(String email, String contrasena) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("contrasena", contrasena);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Login_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);

        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        int resultado = resulset.getFirst().get("ExisteUsuario");
        return resultado;
    }

    public ClienteUsuarioBean obtenerInformacionUsuario(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Usuario")
                .withSchemaName("dbo")
                .returningResultSet("usuario", BeanPropertyRowMapper.newInstance(ClienteUsuarioBean.class));

        Map<String, Object> out = jdbcCall.execute(in);

        List<ClienteUsuarioBean> lista_usuario = (List<ClienteUsuarioBean>) out.get("usuario");
        return lista_usuario.getFirst();
    }

    public Map<String, List<PlataformaDeStreamingBean>> obtenerFederaciones(int id_cliente) {
        Map<String, List<PlataformaDeStreamingBean>> plataformas = new HashMap<>();
        Set<PlataformaDeStreamingBean> conjuntoPlataformasActivas = obtenerPlataformasActivas();
        Set<PlataformaDeStreamingBean> conjuntoPlataformasFederadas = obtenerPlataformasFederadas(id_cliente);

        conjuntoPlataformasActivas.removeAll(conjuntoPlataformasFederadas);

        List<PlataformaDeStreamingBean> plataformasAFederar = new ArrayList<>(conjuntoPlataformasActivas);
        List<PlataformaDeStreamingBean> plataformasFederadas = new ArrayList<>(conjuntoPlataformasFederadas);

        plataformas.put("Plataformas_a_federar", plataformasAFederar);
        plataformas.put("Plataformas_federadas", plataformasFederadas);

        return plataformas;
    }

    public Set<PlataformaDeStreamingBean> obtenerPlataformasActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Informacion_de_Plataformas_de_Streaming_Activas")
                .withSchemaName("dbo")
                .returningResultSet("plataformas", BeanPropertyRowMapper.newInstance(PlataformaDeStreamingBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<PlataformaDeStreamingBean> plataformas = (List<PlataformaDeStreamingBean>) out.get("plataformas");
        Set<PlataformaDeStreamingBean> conjuntoPlataformasActivas = new HashSet<>(plataformas);
        return conjuntoPlataformasActivas;
    }

    public Set<PlataformaDeStreamingBean> obtenerPlataformasFederadas(int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Plataformas_Federadas_de_Usuario")
                .withSchemaName("dbo")
                .returningResultSet("plataformasFederadas", BeanPropertyRowMapper.newInstance(PlataformaDeStreamingBean.class));;
        Map<String, Object> out = jdbcCall.execute(in);
        List<PlataformaDeStreamingBean> plataformasFederadas = (List<PlataformaDeStreamingBean>) out.get("plataformasFederadas");
        Set<PlataformaDeStreamingBean> conjuntoPlataformasFederadas = new HashSet<>(plataformasFederadas);
        return conjuntoPlataformasFederadas;
    }
}
