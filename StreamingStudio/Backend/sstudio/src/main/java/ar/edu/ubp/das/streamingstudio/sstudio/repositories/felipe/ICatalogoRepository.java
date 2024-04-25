package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface ICatalogoRepository {
    public String actualizarCatalogo();
    public List<CatalogoBean> obtenerCatalogo();
    public List<PlataformaDeStreamingBean> obtenerPlataformasActivas();
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma);
    public List<ContenidoBean> obtenerCatalogoDePlataforma(int id_plataforma, String tokenDeServicio);
    public void darDeBajaContenido (List<CatalogoBean> catalogoActual, List<ContenidoBean> catalogoNuevo);
    public void habilitarContenido (List<CatalogoBean> catalogoActual, List<ContenidoBean> catalogoNuevo);
    public void agregarContenidoNuevo (List<CatalogoBean> catalogoActual, List<ContenidoBean> catalogoNuevo);
    public void actualizarContenido (List<CatalogoBean> catalogoActual, List<ContenidoBean> catalogoNuevo);
}
