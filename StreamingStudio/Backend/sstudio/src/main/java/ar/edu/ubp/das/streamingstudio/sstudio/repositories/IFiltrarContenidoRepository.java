package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.InformacionContenidoBean;
import io.micrometer.common.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface IFiltrarContenidoRepository {
    List<ContenidoHomeBean> buscarContenidoPorFiltros(int id_cliente, @Nullable String titulo,
                                                      @Nullable String reciente, @Nullable String destacado,
                                                      @Nullable String clasificacion, @Nullable String masVisto,
                                                      @Nullable String genero);

    InformacionContenidoBean informacionContenido(String id_contenido, int id_cliente);

    Map<String, String> obtenerInformacionContenido(String id_contenido);

    Map<String, String> obtenerGenero(String id_contenido);

    List<Map<String, String>> obtenerDirectores(String id_contenido);

    List<Map<String, String>> obtenerActores(String id_contenido);

    List<Map<String, String>> obtenerInformacionPlataformas(String id_contenido, int id_cliente);
}
