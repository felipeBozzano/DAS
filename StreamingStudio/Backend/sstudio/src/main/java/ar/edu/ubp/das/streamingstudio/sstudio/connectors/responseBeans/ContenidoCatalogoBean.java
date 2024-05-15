package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class ContenidoCatalogoBean extends AbstractBean{
    private String id_contenido;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private int clasificacion;
    private boolean reciente;
    private boolean destacado;
    private boolean valido;

    private boolean id_cliente;

    public ContenidoCatalogoBean() {
    }

    public boolean isId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(boolean id_cliente) {
        this.id_cliente = id_cliente;
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

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public boolean isReciente() {
        return reciente;
    }

    public void setReciente(Boolean reciente) {
        this.reciente = reciente;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(Boolean destacado) {
        this.destacado = destacado;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }
}
