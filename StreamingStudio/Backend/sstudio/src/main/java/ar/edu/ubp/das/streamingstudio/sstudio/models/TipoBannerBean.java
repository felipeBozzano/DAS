package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class TipoBannerBean {

    private int id_tipo_banner;
    private float costo;
    private Boolean exclusividad;
    private Date fecha_alta;
    private Date fecha_baja;
    private String descripcion;

    public TipoBannerBean(int id_tipo_banner, float costo, Boolean exclusividad, Date fecha_alta, Date fecha_baja, String descripcion) {
        this.id_tipo_banner = id_tipo_banner;
        this.costo = costo;
        this.exclusividad = exclusividad;
        this.fecha_alta = fecha_alta;
        this.fecha_baja = fecha_baja;
        this.descripcion = descripcion;
    }

    public int getId_tipo_banner() {
        return id_tipo_banner;
    }

    public void setId_tipo_banner(int id_tipo_banner) {
        this.id_tipo_banner = id_tipo_banner;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Boolean getExclusividad() {
        return exclusividad;
    }

    public void setExclusividad(Boolean exclusividad) {
        this.exclusividad = exclusividad;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
