package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class BannerBean {

    private int id_banner;
    private String tamano_de_banner;
    private String descripcion;

    public BannerBean(int id_banner, String tamano_de_banner, String descripcion) {
        this.id_banner = id_banner;
        this.tamano_de_banner = tamano_de_banner;
        this.descripcion = descripcion;
    }

    public int getId_banner() {
        return id_banner;
    }

    public void setId_banner(int id_banner) {
        this.id_banner = id_banner;
    }

    public String getTamano_de_banner() {
        return tamano_de_banner;
    }

    public void setTamano_de_banner(String tamano_de_banner) {
        this.tamano_de_banner = tamano_de_banner;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
