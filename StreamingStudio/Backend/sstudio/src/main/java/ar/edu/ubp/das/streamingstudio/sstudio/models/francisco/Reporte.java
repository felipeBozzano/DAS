package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

import java.util.Date;

public class Reporte {
    private int id_reporte;
    private int total;
    private Date fecha;
    private int estado;
    private int id_publicista;
    private int id_plataforma;

    public Reporte(int id_reporte, int total, Date fecha, int estado, int id_publicista, int id_plataforma) {
        this.id_reporte = id_reporte;
        this.total = total;
        this.fecha = fecha;
        this.estado = estado;
        this.id_publicista = id_publicista;
        this.id_plataforma = id_plataforma;
    }

    public int getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(int id_reporte) {
        this.id_reporte = id_reporte;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }
}
