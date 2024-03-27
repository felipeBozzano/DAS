package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class CostoBannerBean {

    private int id_tipo_banner;
    private int id_banner;

    public CostoBannerBean(int id_tipo_banner, int id_banner) {
        this.id_tipo_banner = id_tipo_banner;
        this.id_banner = id_banner;
    }

    public int getId_tipo_banner() {
        return id_tipo_banner;
    }

    public void setId_tipo_banner(int id_tipo_banner) {
        this.id_tipo_banner = id_tipo_banner;
    }

    public int getId_banner() {
        return id_banner;
    }

    public void setId_banner(int id_banner) {
        this.id_banner = id_banner;
    }
}
