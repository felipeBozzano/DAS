

package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ClienteUsuarioBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Repository
public class ClienteUsuarioRepository {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;


    @Transactional
    public List<Tipo_de_Fee> getTipoFee(int tipo_de_fee) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_tipo_de_fee", tipo_de_fee);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Tipo_Fee")
                .withSchemaName("dbo")
                .returningResultSet("tipos_de_fee", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<Tipo_de_Fee>)out.get("tipos_de_fee");
    }

    @Transactional
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("usuario", cliente.getUsuario())
                .addValue("contrasena", cliente.getContrasena())
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
    public List<ClienteUsuarioBean> getUser(String email) {
        return jdbcTpl.query("SELECT * FROM dbo.Cliente_Usuario WHERE email = ?",new Object[]{email}, BeanPropertyRowMapper.newInstance(ClienteUsuarioBean.class)
        );
    }

    @Transactional
    public int deleteUser(String email) {
        return jdbcTpl.update("DELETE FROM dbo.Cliente_Usuario WHERE email = ?", email);
    }

    /* FEDERACION */

    @Transactional
    public List<FederacionBean> federarClientePlataforma(int id_plataforma, int id_cliente) {
        List<FederacionBean> federacion = buscarFederacion(id_plataforma, id_cliente);
        if (federacion.size() == 0) {
            return new ArrayList<>();
        } else {
            federacion = VerificarFederacionCurso(id_plataforma, id_cliente);
            return federacion;
        }
    }

    @Transactional
    public List<FederacionBean> buscarFederacion(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Federacion")
                .withSchemaName("dbo")
                .returningResultSet("existe_federacion", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<FederacionBean>)out.get("existe_federacion");
    }

    @Transactional
    public List<FederacionBean> VerificarFederacionCurso(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Federacion_en_Curso")
                .withSchemaName("dbo")
                .returningResultSet("federacion_en_curso", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<FederacionBean>)out.get("federacion_en_curso");
    }
}
