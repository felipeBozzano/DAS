package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.connectors.AbstractConnectorFactory;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContenidoRepository {
    static JdbcTemplate jdbcTpl;
    static Map<String,String> respuesta;
    private static final AbstractConnectorFactory conectorFactory = new AbstractConnectorFactory();

    public static String actualizarContenidoMasVisto() {
        List<ContenidoBean> contenidoMasVistoActual = obtenerContenidoMasVistoActual();
        List<ContenidoBean> contenidoMasVistoMesDelAnterior = obtenerContenidoMasVistoDelMesAnterior();

        Set<ContenidoBean> setContenidoMasVistoActual = new HashSet<>(contenidoMasVistoActual);
        Set<ContenidoBean> setContenidoMasVistoMesDelAnterior = new HashSet<>(contenidoMasVistoMesDelAnterior);

        Set<ContenidoBean> setContenidoAQuitar = setContenidoMasVistoActual;
        setContenidoAQuitar.removeAll(contenidoMasVistoMesDelAnterior);

        Set<ContenidoBean> setContenidoAAgregar = setContenidoMasVistoMesDelAnterior;
        setContenidoAAgregar.removeAll(setContenidoMasVistoActual);

        return "OK";
    }

    
    public static List<ContenidoBean> obtenerContenidoMasVistoActual() {
        return null;
    }

    
    public static List<ContenidoBean> obtenerContenidoMasVistoDelMesAnterior() {
        return null;
    }

    
    public static void agregarAMasVisto(Set<ContenidoBean> contenidoAAgregar) {

    }
    
    public static void quitarDeMasVisto(Set<ContenidoBean> contenidoAQuitar) {

    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=StreamingStudio;encrypt=false;trustServerCertificate=false");
        dataSource.setUsername("SA");
        dataSource.setPassword("Gaboty30.!");
        jdbcTpl = new JdbcTemplate(dataSource);
        actualizarContenidoMasVisto();
    }
}
