package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class ContenidoUrlBean extends AbstractBean {
    private String id_contenido;
    private String url;

    public ContenidoUrlBean() {
    }

    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
