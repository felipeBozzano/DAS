package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import ar.edu.ubp.das.streamingstudio.sstudio.models.ContenidoCatalogoBean;

import java.util.List;

public class CatalogoBean extends AbstractBean {
    private List<ContenidoCatalogoBean> listaContenido;

    public CatalogoBean(){}

    public CatalogoBean(List<ContenidoCatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public List<ContenidoCatalogoBean> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<ContenidoCatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }
}
