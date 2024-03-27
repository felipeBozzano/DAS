package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ContenidoBean {

    private int id_contenido;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private int clasificacion;
    private Boolean mas_visto;

    public ContenidoBean(int id_contenido, String titulo, String descripcion, String url_imagen, int clasificacion, Boolean mas_visto) {
        this.id_contenido = id_contenido;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_imagen = url_imagen;
        this.clasificacion = clasificacion;
        this.mas_visto = mas_visto;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Boolean getMas_visto() {
        return mas_visto;
    }

    public void setMas_visto(Boolean mas_visto) {
        this.mas_visto = mas_visto;
    }
}
