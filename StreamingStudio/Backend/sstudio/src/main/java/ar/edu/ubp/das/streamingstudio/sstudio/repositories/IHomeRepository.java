package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;

import java.util.List;
import java.util.Map;

public interface IHomeRepository {
    public Map<String, Map<String, List<ContenidoHomeBean>>> getHome(int id_cliente);
    public List<?> getMasVisto(int id_cliente);
    public List<?> getReciente(int id_cliente);
    public List<?> getDestacado(int id_cliente);
    public Map<String, List<ContenidoHomeBean>> agruparContenido(List<ContenidoHomeBean> listaContenidos);
}
