package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class ContenidoFiltroResponseBean extends AbstractBean {
    private int id_cliente;
    private String titulo;
    private String clasificacion;
    private String mas_visto;
    private String reciente;
    private String destacado;
    private GeneroResponseBean generos;

    public ContenidoFiltroResponseBean() {
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMas_visto() {
        return mas_visto;
    }

    public void setMas_visto(String mas_visto) {
        this.mas_visto = mas_visto;
    }

    public String getReciente() {
        return reciente;
    }

    public void setReciente(String reciente) {
        this.reciente = reciente;
    }

    public String getDestacado() {
        return destacado;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
    }

    public GeneroResponseBean getGeneros() {
        return generos;
    }

    public void setGeneros(GeneroResponseBean generos) {
        this.generos = generos;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
