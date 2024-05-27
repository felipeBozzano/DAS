package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ContenidoFiltroBean {
    private String id_contenido;
    private Integer id_plataforma;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private String clasificacion;
    private Boolean mas_visto;
    private Boolean reciente;
    private Boolean destacado;
    private Boolean valido;
    private String genero;
    private int id_cliente;

    public ContenidoFiltroBean() {
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public Boolean getMas_visto() {
        return mas_visto;
    }

    public void setMas_visto(Boolean mas_visto) {
        this.mas_visto = mas_visto;
    }

    public Integer getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public Boolean isReciente() {
        return reciente;
    }

    public void setReciente(boolean reciente) {
        this.reciente = reciente;
    }

    public Boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public Boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @Override
    public String toString() {
        return "ContenidoFiltroBean{" +
                "id_contenido='" + id_contenido + '\'' +
                ", id_plataforma=" + id_plataforma +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", url_imagen='" + url_imagen + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                ", mas_visto=" + mas_visto +
                ", reciente=" + reciente +
                ", destacado=" + destacado +
                ", valido=" + valido +
                ", genero='" + genero + '\'' +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
