package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoFiltroBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ar.edu.ubp.das.streamingstudio.sstudio.utils.batch.BatchUtils.crearJdbcTemplate;

public class ContenidoMasVisto {
    static JdbcTemplate jdbcTpl;

    public static void actualizarContenidoMasVisto() {
        Set<ContenidoFiltroBean> contenidoMasVistoActual = obtenerContenidoMasVistoActual();
        Set<ContenidoFiltroBean> contenidoMasVistoMesDelAnterior = obtenerContenidoMasVistoDelMesAnterior();

        // Obtengo los contenidos a quitar la bandera "Mas Visto"
        contenidoMasVistoActual.removeAll(contenidoMasVistoMesDelAnterior);
        quitarDeMasVisto(contenidoMasVistoActual);

        // Actualizo los contenidos mas vistos
        agregarAMasVisto(contenidoMasVistoMesDelAnterior);

        System.out.println("Contenido quitado de Mas Visto");
        System.out.println(contenidoMasVistoActual);
        System.out.println("------------------------------");
        System.out.println("Contenido agregado a Mas Visto");
        System.out.println(contenidoMasVistoMesDelAnterior);
    }

    public static Set<ContenidoFiltroBean> obtenerContenidoMasVistoActual() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_mas_Visto_Actual")
                .withSchemaName("dbo")
                .returningResultSet("contenido", BeanPropertyRowMapper.newInstance(ContenidoFiltroBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoFiltroBean> lista_contenido = (List<ContenidoFiltroBean>) out.get("contenido");
        return new HashSet<>(lista_contenido);
    }

    public static Set<ContenidoFiltroBean> obtenerContenidoMasVistoDelMesAnterior() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Contenido_mas_Visto_del_Mes_Anterior")
                .withSchemaName("dbo")
                .returningResultSet("contenido", BeanPropertyRowMapper.newInstance(ContenidoFiltroBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<ContenidoFiltroBean> lista_contenido = (List<ContenidoFiltroBean>) out.get("contenido");
        return new HashSet<>(lista_contenido);
    }

    public static void quitarDeMasVisto(Set<ContenidoFiltroBean> contenidoAQuitar) {
        for (ContenidoFiltroBean contenido : contenidoAQuitar) {
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", contenido.getId_contenido());
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Quitar_de_Contenido_mas_Visto")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    public static void agregarAMasVisto(Set<ContenidoFiltroBean> contenidoAAgregar) {
        for (ContenidoFiltroBean contenido : contenidoAAgregar) {
            SqlParameterSource in = new MapSqlParameterSource()
                    .addValue("id_contenido", contenido.getId_contenido());
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                    .withProcedureName("Actualizar_a_Contenido_mas_Visto")
                    .withSchemaName("dbo");
            jdbcCall.execute(in);
        }
    }

    public static void main(String[] args) {
        jdbcTpl = crearJdbcTemplate();
        actualizarContenidoMasVisto();
    }
}
