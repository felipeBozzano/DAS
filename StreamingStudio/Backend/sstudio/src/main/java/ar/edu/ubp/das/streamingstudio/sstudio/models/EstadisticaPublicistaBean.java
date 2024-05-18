package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class EstadisticaPublicistaBean {
    private int id_publicista;
    private int id_publicidad;
    private String codigo_publicidad;
    private int cantidad_de_clics;

    public EstadisticaPublicistaBean() {
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
    }

    public String getCodigo_publicidad() {
        return codigo_publicidad;
    }

    public void setCodigo_publicidad(String codigo_publicidad) {
        this.codigo_publicidad = codigo_publicidad;
    }

    public int getCantidad_de_clics() {
        return cantidad_de_clics;
    }

    public void setCantidad_de_clics(int cantidad_de_clics) {
        this.cantidad_de_clics = cantidad_de_clics;
    }
}
