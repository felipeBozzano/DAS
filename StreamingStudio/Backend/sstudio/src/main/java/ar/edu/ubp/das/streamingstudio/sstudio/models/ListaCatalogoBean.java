package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;

public class ListaCatalogoBean {
    private List<ContenidoCatalogoBean> listaContenido;

    public ListaCatalogoBean(){}

    public ListaCatalogoBean(List<ContenidoCatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public List<ContenidoCatalogoBean> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<ContenidoCatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }
}
