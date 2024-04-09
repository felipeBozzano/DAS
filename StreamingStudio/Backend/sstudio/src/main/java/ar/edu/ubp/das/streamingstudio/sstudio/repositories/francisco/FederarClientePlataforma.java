//package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;
//
//import ar.edu.ubp.das.streamingstudio.sstudio.models.FederacionBean;
//import ar.edu.ubp.das.streamingstudio.sstudio.models.Tipo_de_Fee;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.core.simple.SimpleJdbcCall;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//@Repository
//public class FederarClientePlataforma {
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private JdbcTemplate jdbcTpl;
//
//
//    @Transactional
//    public List<FederacionBean> federarClientePlataforma(int id_plataforma, int id_cliente) {
//        List<FederacionBean> federacion = buscarFederacion(id_plataforma, id_cliente);
//        if (federacion.size() == 0) {
//            return new ArrayList<>();
//        } else {
//            return federacion;
//        }
//    }
//
//    @Transactional
//    public List<FederacionBean> buscarFederacion(int id_plataforma, int id_cliente) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_plataforma", id_plataforma)
//                .addValue("id_cliente", id_cliente);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Buscar_Federacion")
//                .withSchemaName("dbo")
//                .returningResultSet("existe_federacion", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));
//
//        Map<String, Object> out = jdbcCall.execute(in);
//        return (List<FederacionBean>)out.get("existe_federacion");
//    }
//
//    @Transactional
//    public List<FederacionBean> VerificarFederacionCurso(int id_plataforma, int id_cliente) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_plataforma", id_plataforma)
//                .addValue("id_cliente", id_cliente);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Verificar_Federacion_en_Curso")
//                .withSchemaName("dbo")
//                .returningResultSet("federacion_en_curso", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));
//
//        Map<String, Object> out = jdbcCall.execute(in);
//        return (List<FederacionBean>)out.get("federacion_en_curso");
//    }
//
//    @Transactional
//    public String ObtenerTokenServicioPlataforma(int id_plataforma) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_plataforma", id_plataforma);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
//                .withSchemaName("dbo")
//                .returningResultSet("token", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));
//
//        Map<String, Object> out = jdbcCall.execute(in);
//        return (String)out.get("token");
//    }
//
//
//}
