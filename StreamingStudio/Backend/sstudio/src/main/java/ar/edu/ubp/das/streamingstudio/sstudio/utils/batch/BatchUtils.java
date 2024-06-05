package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicistaBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Map;

public class BatchUtils {
    public static JdbcTemplate crearJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=StreamingStudio;encrypt=false;trustServerCertificate=false");
        dataSource.setUsername("SA");
//        dataSource.setPassword("Gaboty30.!");
        dataSource.setPassword("samano$uke_123");
        return new JdbcTemplate(dataSource);
    }

    public static Map<String, String> obtenerInformacionDeConexionAPlataforma(int id_plataforma, JdbcTemplate conectorBD) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(conectorBD)
                .withProcedureName("Obtener_Datos_de_Conexion_a_Plataforma")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String,String>> lista_mapa = (List<Map<String,String>>) out.get("#result-set-1");
        Map<String, String> info_plataforma = lista_mapa.getFirst();
        return info_plataforma;
    }

    public static PublicistaBean obtenerInformacionDeConexionAPublicista(int id_publicista, JdbcTemplate conectorBD) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_publicista", id_publicista);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(conectorBD)
                .withProcedureName("Obtener_Datos_de_Conexion_a_Publicista")
                .withSchemaName("dbo")
                .returningResultSet("publicista", BeanPropertyRowMapper.newInstance(PublicistaBean.class));

        Map<String, Object> out = jdbcCall.execute(in);
        List<PublicistaBean> lista_mapa = (List<PublicistaBean>) out.get("publicista");
        PublicistaBean info_publicista = lista_mapa.getFirst();
        return info_publicista;
    }

    public static Map<String,String> imprimirMapa(Map<String,String> mapa) {
        for (String key : mapa.keySet()) {
            System.out.println(key + ": " + mapa.get(key));
        }
        mapa.clear();
        return mapa;
    }
}
