package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

import java.util.Date;

public class PublicidadResponseBean extends AbstractBean{
    private int tipo_banner;
    private String url_de_imagen;
    private String url_de_publicidad;
    private int id_publicidad;
    private Date fecha_de_alta;
    private Date fecha_de_baja;

    public PublicidadResponseBean() {
    }

    public int getTipo_banner() {
        return tipo_banner;
    }

    public void setTipo_banner(int tipo_banner) {
        this.tipo_banner = tipo_banner;
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

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
    }

    public Date getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(Date fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public Date getFecha_de_baja() {
        return fecha_de_baja;
    }

    public void setFecha_de_baja(Date fecha_de_baja) {
        this.fecha_de_baja = fecha_de_baja;
    }
}
