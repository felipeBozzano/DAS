package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;

public class CatalogoBean {

    private List<ContenidoBean> listaContenido;

    public CatalogoBean(){}

    public CatalogoBean(List<ContenidoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public List<ContenidoBean> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<ContenidoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }
}
