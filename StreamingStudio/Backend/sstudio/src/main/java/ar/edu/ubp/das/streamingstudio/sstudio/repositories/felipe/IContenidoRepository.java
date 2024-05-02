package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;

import java.util.List;
import java.util.Set;

public interface IContenidoRepository {
    public String actualizarContenidoMasVisto();

    public List<ContenidoBean> obtenerContenidoMasVistoActual();

    public List<ContenidoBean> obtenerContenidoMasVistoDelMesAnterior();

    public void agregarAMasVisto(Set<ContenidoBean> contenidoAAgregar);

    public void quitarDeMasVisto(Set<ContenidoBean> contenidoAQuitar);
}
