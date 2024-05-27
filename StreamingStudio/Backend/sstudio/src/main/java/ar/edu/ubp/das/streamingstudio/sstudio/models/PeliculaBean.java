package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class PeliculaBean {

    private String id_contenido;
    private String titulo;
    private String url_imagen;

    public PeliculaBean(String id_contenido, String titulo, String url_imagen) {
        this.id_contenido = id_contenido;
        this.titulo = titulo;
        this.url_imagen = url_imagen;
    }

    public PeliculaBean(){}


    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
