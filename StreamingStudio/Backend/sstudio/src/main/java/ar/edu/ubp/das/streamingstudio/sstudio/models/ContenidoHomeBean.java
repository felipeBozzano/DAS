package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ContenidoHomeBean {
    private String id_contenido;
    private int id_plataforma;
    private String url_imagen;

    public ContenidoHomeBean() {
    }

    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
