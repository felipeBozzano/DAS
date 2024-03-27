package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ClasificacionBean {

    private int id_clasificacion;
    private String descripcion;

    public ClasificacionBean(int id_clasificacion, String descripcion) {
        this.id_clasificacion = id_clasificacion;
        this.descripcion = descripcion;
    }

    public int getId_clasificacion() {
        return id_clasificacion;
    }

    public void setId_clasificacion(int id_clasificacion) {
        this.id_clasificacion = id_clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
