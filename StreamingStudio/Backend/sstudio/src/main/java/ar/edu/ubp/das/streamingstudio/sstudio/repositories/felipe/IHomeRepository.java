package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;

import java.util.List;
import java.util.Map;

public interface IHomeRepository {
    public Map<String, Map<Integer, List<?>>> getHome(int id_cliente);
    public List<?> getPublicidadesActivas();
    public List<?> getMasVisto(int id_cliente);
    public List<?> getReciente(int id_cliente);
    public List<?> getDestacado(int id_cliente);
    public Map<Integer, List<?>> agruparContenido(List<ContenidoBean> listaContenidos);
    public Map<Integer, List<?>> agruparPublicidad(List<PublicidadBean> listaPublicidad);
}
