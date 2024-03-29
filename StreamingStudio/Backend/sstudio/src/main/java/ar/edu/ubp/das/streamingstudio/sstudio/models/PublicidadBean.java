package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class PublicidadBean {
    private int id_publicidad;
    private int id_publicista;
    private int id_banner;
    private String codigo_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;
    private Date fecha_de_alta;
    private Date fecha_de_baja;

    public PublicidadBean(int id_publicidad, int id_publicista, int id_banner, String codigo_publicidad, String url_de_imagen, String url_de_publicidad, Date fecha_de_alta, Date fecha_de_baja) {
        this.id_publicidad = id_publicidad;
        this.id_publicista = id_publicista;
        this.id_banner = id_banner;
        this.codigo_publicidad = codigo_publicidad;
        this.url_de_imagen = url_de_imagen;
        this.url_de_publicidad = url_de_publicidad;
        this.fecha_de_alta = fecha_de_alta;
        this.fecha_de_baja = fecha_de_baja;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
    }

    public int getId_banner() {
        return id_banner;
    }

    public void setId_banner(int id_banner) {
        this.id_banner = id_banner;
    }

    public String getCodigo_publicidad() {
        return codigo_publicidad;
    }

    public void setCodigo_publicidad(String codigo_publicidad) {
        this.codigo_publicidad = codigo_publicidad;
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
