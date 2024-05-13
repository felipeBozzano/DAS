package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class ContenidoBean {

    private String id_contenido;
    private int id_plataforma;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private int clasificacion;
    private Boolean mas_visto;
    private boolean reciente;
    private boolean destacado;
    private boolean valido;

    public ContenidoBean(String id_contenido, int id_plataforma, String titulo, String descripcion, String url_imagen, int clasificacion,
                         boolean destacado, boolean reciente, boolean valido) {
        this.id_contenido = id_contenido;
        this.id_plataforma = id_plataforma;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_imagen = url_imagen;
        this.clasificacion = clasificacion;
        this.mas_visto = false;
        this.destacado = destacado;
        this.reciente = reciente;
        this.valido = valido;
    }

    public ContenidoBean() {
    }

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

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public boolean isReciente() {
        return reciente;
    }

    public void setReciente(boolean reciente) {
        this.reciente = reciente;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}
