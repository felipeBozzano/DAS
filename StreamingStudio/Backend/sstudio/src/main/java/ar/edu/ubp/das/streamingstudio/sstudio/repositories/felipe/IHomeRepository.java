package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;

import java.util.List;
import java.util.Map;

public interface IHomeRepository {
    public Map<String, Map<?, List<?>>> getHome(int id_cliente);
    public List<?> getPublicidadesActivas();
    public List<?> getMasVisto(int id_cliente);
    public List<?> getReciente(int id_cliente);
    public List<?> getDestacado(int id_cliente);
    public Map<String, List<?>> agruparContenido(List<ContenidoHomeBean> listaContenidos);
    public Map<Integer, List<?>> agruparPublicidad(List<PublicidadHomeBean> listaPublicidad);
}
