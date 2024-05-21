package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;

public class ContenidoResponseHomeBean {
    private String id_contenido;
    private List<Integer> id_plataforma;
    private String url_imagen;

    public ContenidoResponseHomeBean() {
    }

    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public List<Integer> getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(List<Integer> id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
