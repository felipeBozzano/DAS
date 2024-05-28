package ar.edu.ubp.das.streamingstudio.publicistasbicicletas.beans;

public class PublicidadBean {
    private String id_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;
    private String fecha_de_alta;
    private String fecha_de_baja;
    private String tipo_banner;

    public PublicidadBean() {
    }

    public String getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(String id_publicidad) {
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

    public String getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(String fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public String getFecha_de_baja() {
        return fecha_de_baja;
    }

    public void setFecha_de_baja(String fecha_de_baja) {
        this.fecha_de_baja = fecha_de_baja;
    }

    public String getTipo_banner() {
        return tipo_banner;
    }

    public void setTipo_banner(String tipo_banner) {
        this.tipo_banner = tipo_banner;
    }
}
