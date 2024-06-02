package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;

public class CatalogoBean {

    private List<ContenidoBean> listaContenido;
    private String mensajeRespuesta;
    private String codigoRespuesta;

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

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
