package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import java.util.List;

public class ListaPublicidadResponseBean extends AbstractBean{
    private List<PublicidadResponseBean> listaPublicidades;

    public ListaPublicidadResponseBean() {
    }

    public List<PublicidadResponseBean> getListaPublicidades() {
        return listaPublicidades;
    }

    public void setListaPublicidades(List<PublicidadResponseBean> listaPublicidades) {
        this.listaPublicidades = listaPublicidades;
    }
}
