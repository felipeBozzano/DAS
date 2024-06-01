package ar.edu.ubp.das.streamingstudio.publicistaubp.models;

import java.util.List;

public class PublicidadResponseBean {
    private Integer codigoRespuesta;
    private String mensajeRespuesta;
    private List<PublicidadBean> listaPublicidades;

    public PublicidadResponseBean() {
    }

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public List<PublicidadBean> getListaPublicidades() {
        return listaPublicidades;
    }

    public void setListaPublicidades(List<PublicidadBean> listaPublicidades) {
        this.listaPublicidades = listaPublicidades;
    }
}
