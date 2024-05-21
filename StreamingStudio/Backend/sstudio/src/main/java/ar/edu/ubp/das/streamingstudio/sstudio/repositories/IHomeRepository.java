package ar.edu.ubp.das.streamingstudio.sstudio.repositories;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoResponseHomeBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadHomeBean;

import java.util.List;
import java.util.Map;

public interface IHomeRepository {
    Map<String, Map<String, ContenidoResponseHomeBean>> getHome(int id_cliente);
    List<ContenidoHomeBean> getMasVisto(int id_cliente);
    List<ContenidoHomeBean> getReciente(int id_cliente);
    List<ContenidoHomeBean> getDestacado(int id_cliente);
    Map<String, ContenidoResponseHomeBean> agruparContenido(List<ContenidoHomeBean> listaContenidos);
}
