package ar.edu.ubp.das.streamingstudio.sstudio.repositories.felipe;

import ar.edu.ubp.das.streamingstudio.sstudio.models.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoCatalogoBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PlataformaDeStreamingBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadBean;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface ICatalogoRepository {
    public String actualizarCatalogo() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public List<CatalogoBean> obtenerCatalogo();
    public List<PlataformaDeStreamingBean> obtenerPlataformasActivas();
    public String obtenerTokenDeServicioDePlataforma(int id_plataforma);
    public List<ContenidoCatalogoBean> obtenerCatalogoDePlataforma(int id_plataforma, String tokenDeServicio) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public void darDeBajaContenido (List<CatalogoBean> catalogoActual, List<ContenidoCatalogoBean> catalogoNuevo);
    public void habilitarContenido (List<CatalogoBean> catalogoActual, List<ContenidoCatalogoBean> catalogoNuevo);
    public void agregarContenidoNuevo (int id_plataforma, List<CatalogoBean> catalogoActual, List<ContenidoCatalogoBean> catalogoNuevo);
    public void actualizarContenido (int id_plataforma, List<CatalogoBean> catalogoActual, List<ContenidoCatalogoBean> catalogoNuevo);
}
