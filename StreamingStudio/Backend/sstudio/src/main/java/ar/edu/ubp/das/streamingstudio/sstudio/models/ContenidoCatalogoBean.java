package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ContenidoCatalogoBean {
    private String id_contenido;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private String clasificacion;
    private boolean reciente;
    private boolean destacado;
    private boolean valido;
    private  ActorBean actores;

    private DirectorBean directores;

    public ActorBean getActores() {
        return actores;
    }

    public void setActores(ActorBean actores) {
        this.actores = actores;
    }

    public DirectorBean getDirectores() {
        return directores;
    }

    public void setDirectores(DirectorBean directores) {
        this.directores = directores;
    }

    public ContenidoCatalogoBean() {
    }

    public ContenidoCatalogoBean(String id_contenido, String titulo, String descripcion, String url_imagen, String clasificacion, boolean reciente, boolean destacado, boolean valido, ActorBean actores, DirectorBean directores) {
        this.id_contenido = id_contenido;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_imagen = url_imagen;
        this.clasificacion = clasificacion;
        this.reciente = reciente;
        this.destacado = destacado;
        this.valido = valido;
        this.actores = actores;
        this.directores = directores;
    }

    public ContenidoCatalogoBean(String id_contenido, String titulo, String descripcion, String url_imagen, String clasificacion, boolean reciente, boolean destacado, boolean valido) {
        this.id_contenido = id_contenido;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_imagen = url_imagen;
        this.clasificacion = clasificacion;
        this.reciente = reciente;
        this.destacado = destacado;
        this.valido = valido;
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

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
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
