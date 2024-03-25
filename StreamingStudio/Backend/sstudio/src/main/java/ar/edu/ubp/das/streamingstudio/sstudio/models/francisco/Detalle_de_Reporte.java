package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Detalle_de_Reporte {
    private int id_reporte;
    private int id_detalle;
    private String descripcion;
    private int cantidad_de_clics;

    public Detalle_de_Reporte(int id_reporte, int id_detalle, String descripcion, int cantidad_de_clics) {
        this.id_reporte = id_reporte;
        this.id_detalle = id_detalle;
        this.descripcion = descripcion;
        this.cantidad_de_clics = cantidad_de_clics;
    }

    public int getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(int id_reporte) {
        this.id_reporte = id_reporte;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad_de_clics() {
        return cantidad_de_clics;
    }

    public void setCantidad_de_clics(int cantidad_de_clics) {
        this.cantidad_de_clics = cantidad_de_clics;
    }
}
