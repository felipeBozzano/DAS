package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class ClicBean {

    private int id_clic;
    private int id_cliente;
    private int id_publicidad;
    private int id_plataforma;
    private int id_contenido;
    private Date fecha;

    public ClicBean(int id_clic, int id_cliente, int id_publicidad, Date fecha) {
        this.id_clic = id_clic;
        this.id_cliente = id_cliente;
        this.id_publicidad = id_publicidad;
        this.fecha = fecha;
    }

    public ClicBean(int id_clic, int id_cliente, int id_plataforma, int id_contenido, Date fecha) {
        this.id_clic = id_clic;
        this.id_cliente = id_cliente;
        this.id_plataforma = id_plataforma;
        this.id_contenido = id_contenido;
        this.fecha = fecha;
    }

    public int getId_clic() {
        return id_clic;
    }

    public void setId_clic(int id_clic) {
        this.id_clic = id_clic;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
