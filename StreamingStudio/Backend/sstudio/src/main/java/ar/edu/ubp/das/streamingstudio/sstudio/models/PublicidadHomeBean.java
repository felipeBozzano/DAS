package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class PublicidadHomeBean {
    private int id_tipo_banner;
    private int id_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;

    public PublicidadHomeBean() {
    }

    public int getId_tipo_banner() {
        return id_tipo_banner;
    }

    public void setId_tipo_banner(int id_tipo_banner) {
        this.id_tipo_banner = id_tipo_banner;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
    }

    public String getUrl_de_imagen() {
        return url_de_imagen;
    }

    public void setUrl_de_imagen(String url_de_imagen) {
        this.url_de_imagen = url_de_imagen;
    }

    public String getUrl_de_publicidad() {
        return url_de_publicidad;
    }

    public void setUrl_de_publicidad(String url_de_publicidad) {
        this.url_de_publicidad = url_de_publicidad;
    }
}
