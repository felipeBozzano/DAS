package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import java.util.Date;

public class PublicidadResponseBean {
    private String url_de_imagen;
    private String url_de_publicidad;
    private String codigo_publicidad;

    public PublicidadResponseBean() {
    }

    public String getUrl_de_imagen() {
        return url_de_imagen;
    }

    public void setUrl_de_imagen(String url_de_imagen) {
        this.url_de_imagen = url_de_imagen;
    }

    public String getUrl_de_publicidad() {
        return url_de_publicidad;
    }

    public void setUrl_de_publicidad(String url_de_publicidad) {
        this.url_de_publicidad = url_de_publicidad;
    }

    public String getCodigo_publicidad() {
        return codigo_publicidad;
    }

    public void setCodigo_publicidad(String codigo_publicidad) {
        this.codigo_publicidad = codigo_publicidad;
    }
}
