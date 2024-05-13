package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
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
import java.util.concurrent.*;

@Repository
public class User_repository {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;

    private Map<String, String> respuesta;

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
    public List<ClienteUsuarioBean> getUser(String email) {
        return jdbcTpl.query("SELECT * FROM dbo.Cliente_Usuario WHERE email = ?",new Object[]{email}, BeanPropertyRowMapper.newInstance(ClienteUsuarioBean.class));
    }

    public Map<String, List<PlataformaDeStreamingBean>> obtenerFederaciones(int id_cliente) {
        Map<String, List<PlataformaDeStreamingBean>> plataformas = new HashMap<>();
        Set<PlataformaDeStreamingBean> conjuntoPlataformasActivas = obtenerPlataformasActivas();
        Set<PlataformaDeStreamingBean> conjuntoPlataformasFederadas = obtenerPlataformasFederadas(id_cliente);

        conjuntoPlataformasActivas.removeAll(conjuntoPlataformasFederadas);

        List<PlataformaDeStreamingBean> plataformasAFederar = new ArrayList<>(conjuntoPlataformasActivas);
        List<PlataformaDeStreamingBean> plataformasFederadas = new ArrayList<>(conjuntoPlataformasFederadas);

        plataformas.put("Plataformas A Federar", plataformasAFederar);
        plataformas.put("Plataformas Federadas", plataformasFederadas);

        return plataformas;
    }

    public Set<PlataformaDeStreamingBean> obtenerPlataformasActivas() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Plataformas_de_Streaming_Activas")
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
