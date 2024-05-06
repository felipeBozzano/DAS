package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnector;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans.ComenzarFederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Federar_cliente_repository {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;

    @Transactional
    public Map<String, String> federarClientePlataforma(int id_plataforma, int id_cliente, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int federacion = buscarFederacion(id_plataforma, id_cliente);

        Map respuesta = new HashMap();
        if(federacion == 1){
            respuesta.put("mensaje", "El cliente ya esta federado");
        } else {
            federacion = VerificarFederacionCurso(id_plataforma, id_cliente);
            if(federacion == 1) {
                finalizarFederacion(id_plataforma, id_cliente);
            }else{
                String urlRedireccionPropia = "";
                respuesta = comenzarFederacion(id_plataforma, id_cliente, urlRedireccionPropia, tipo_transaccion);
            }
        }
        return respuesta;
    }

    @Transactional
    public int buscarFederacion(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer federacion = resulset.get(0).get("federacion");
        return federacion;

    }

    @Transactional
    public int VerificarFederacionCurso(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Federacion_en_Curso")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer federacion = resulset.get(0).get("existe_federacion");
        return federacion;
    }

    @Transactional
    public List<PublicidadBean> buscarDatoPublicidades() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicidades")
                .withSchemaName("dbo")
                .returningResultSet("publicidad", BeanPropertyRowMapper.newInstance(PublicidadBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PublicidadBean>)out.get("publicidad");
    }

    @Transactional
    public double obtenerCostoDeBanner(int id_banner) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_banner", id_banner);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Costo_de_Banner")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Double >> resulset = (List<Map<String, Double>>) out.get("#result-set-1");
        double cotsto_banner = resulset.get(0).get("costo");
        return cotsto_banner;
    }

    @Transactional
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Token_de_Servicio_de_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> mapa_token = (List<Map<String,String>>) out.get("#result-set-1");
        String token = mapa_token.get(0).get("token_de_servicio");
        return token;
    }

    @Transactional
    public void finalizarFederacion(int id_plataforma, int id_cliente) {
        String token = "";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("token", token);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Transactional
    public Map<String, String> comenzarFederacion(int id_plataforma, int id_cliente, String url_redireccion_propia, String tipo_transaccion) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<String, String> respuesta = new HashMap();
        AbstractConnector conector;
        AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();
        conector = conectorFactory.crearConector("REST");
        Map<String, String> body = new HashMap<>();
        body.put("url", "https://localhost:8080/ss/");
        ComenzarFederacionBean bean = (ComenzarFederacionBean) conector.execute_post_request("http://localhost:8081/netflix/obtener_token", body, "ComenzarFederacionBean");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente)
                .addValue("codigo_de_transaccion", bean.getCodigoTransaccion())
                .addValue("url_login_registro_plataforma", bean.getUrl())
                .addValue("url_redireccion_propia", url_redireccion_propia)
                .addValue("tipo_transaccion", tipo_transaccion);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Comenzar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        respuesta.put("mensaje", "Federacion comenzada");
        respuesta.put("url_redireccion", bean.getUrl());
        respuesta.put("codigo_transaccion", bean.getCodigoTransaccion());
        return respuesta;
    }
}
