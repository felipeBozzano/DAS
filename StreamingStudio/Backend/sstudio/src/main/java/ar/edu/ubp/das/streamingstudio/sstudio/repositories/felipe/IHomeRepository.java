package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;

import java.util.List;
import java.util.Map;

public interface IHomeRepository {
    public Map<String, List<?>> getHome(int id_cliente);
    public List<PublicidadBean> getPublicidadesActivas();
    public List<ContenidoBean> getMasVisto(int id_cliente);
    public List<ContenidoBean> getReciente(int id_cliente);
    public List<ContenidoBean> getDestacado(int id_cliente);
}
