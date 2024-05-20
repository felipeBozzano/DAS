package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class PublicidadFacturasBean {
    private int id_tipo_banner;
    private int id_publicidad;
    private int id_publicista;
    private String codigo_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;
    private Date fecha_inicio;
    private Date fecha_final;
    private int cantidad_de_dias;

    public PublicidadFacturasBean() {
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
        return fecha_inicio;
    }

    public void setFecha_de_alta(Date fecha_de_alta) {
        this.fecha_inicio = fecha_de_alta;
    }

    public Date getFecha_de_baja() {
        return fecha_final;
    }

    public void setFecha_de_baja(Date fecha_de_baja) {
        this.fecha_final = fecha_de_baja;
    }

    public int getId_tipo_banner() {
        return id_tipo_banner;
    }

    public void setId_tipo_banner(int id_tipo_banner) {
        this.id_tipo_banner = id_tipo_banner;
    }

    public int getCantidad_de_dias() {
        return cantidad_de_dias;
    }

    public void setCantidad_de_dias(int cantidad_de_dias) {
        this.cantidad_de_dias = cantidad_de_dias;
    }

    @Override
    public String toString() {
        return "id_publicidad: " + id_publicidad + "\n"
                + "id_publicista: " + id_publicista + "\n"
                + "id_tipo_banner" + id_tipo_banner + "\n"
                + "codigo_publicidad" + codigo_publicidad + "\n"
                + "url_de_imagen: " + url_de_imagen + "\n"
                + "url_de_publiocidad: " + url_de_publicidad + "\n"
                + "fecha_de_alta: " + fecha_inicio + "\n"
                + "fecha_de_baja: " + fecha_final + "\n"
                + "id_tipo_banner: " + id_tipo_banner + "\n";

    }
}
