package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContenidoRepository implements IContenidoRepository{
    @Override
    public String actualizarContenidoMasVisto() {
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

    @Override
    public List<ContenidoBean> obtenerContenidoMasVistoActual() {
        return null;
    }

    @Override
    public List<ContenidoBean> obtenerContenidoMasVistoDelMesAnterior() {
        return null;
    }

    @Override
    public void agregarAMasVisto(Set<ContenidoBean> contenidoAAgregar) {

    }

    @Override
    public void quitarDeMasVisto(Set<ContenidoBean> contenidoAQuitar) {

    }
}
