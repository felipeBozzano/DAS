package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Estado_Factura {

    private int id_estado;
    private String descripcion;

    public Estado_Factura(int id_estado, String descripcion) {
        this.id_estado = id_estado;
        this.descripcion = descripcion;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
