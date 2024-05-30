package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;

public class ListaContenidoBean {

    private List<CatalogoBean> listaContenido;

    public ListaContenidoBean(){}

    public ListaContenidoBean(List<CatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public List<CatalogoBean> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<CatalogoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }
}
