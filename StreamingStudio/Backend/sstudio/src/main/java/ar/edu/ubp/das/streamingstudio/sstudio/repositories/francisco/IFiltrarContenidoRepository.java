package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.InformacionContenido;
import io.micrometer.common.lang.Nullable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IFiltrarContenidoRepository {
    List<ContenidoHomeBean> buscarContenidoPorFiltros(int id_cliente, String titulo, @Nullable boolean reciente, @Nullable boolean destacado, @Nullable String clasificacion, @Nullable boolean masVisto, @Nullable String genero);
    InformacionContenido informacionContenido(String id_contenido, int id_cliente);
    Map<String,String> obtenerInformacionContenido(String id_contenido);
    Map<String,String> obtenerGenero(String id_contenido);
    List<Map<String,String>> obtenerDirectores(String id_contenido);
    List<Map<String,String>> obtenerActores(String id_contenido);
    List<Map<String,String>> obtenerInformacionPlataformas(String id_contenido, int id_cliente);
}
