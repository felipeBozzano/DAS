package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class EstadisticaPlataformaBean {
    private int id_plataforma;

    private int id_contenido;

    private String id_en_plataforma;

    private int cantidad_de_clics;

    public EstadisticaPlataformaBean() {
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getId_en_plataforma() {
        return id_en_plataforma;
    }

    public void setId_en_plataforma(String id_en_plataforma) {
        this.id_en_plataforma = id_en_plataforma;
    }

    public int getCantidad_de_clics() {
        return cantidad_de_clics;
    }

    public void setCantidad_de_clics(int cantidad_de_clics) {
        this.cantidad_de_clics = cantidad_de_clics;
    }
}
