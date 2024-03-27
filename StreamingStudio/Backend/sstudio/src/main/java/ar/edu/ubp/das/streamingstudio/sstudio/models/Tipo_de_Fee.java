package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class Tipo_de_Fee {
    private int id_tipo_de_fee;
    private String tipo_de_fee;
    private String descripcion;

    public int getId_tipo_de_fee() {
        return id_tipo_de_fee;
    }

    public Tipo_de_Fee(int id_tipo_de_fee, String tipo_de_fee, String descripcion) {
        this.id_tipo_de_fee = id_tipo_de_fee;
        this.tipo_de_fee = tipo_de_fee;
        this.descripcion = descripcion;
    }

    public void setId__tipo_de_fee(int id__tipo_de_fee) {
        this.id_tipo_de_fee = id__tipo_de_fee;
    }

    public String getTipo_de_fee() {
        return tipo_de_fee;
    }

    public void setTipo_de_fee(String tipo_de_fee) {
        this.tipo_de_fee = tipo_de_fee;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo_de_Fee(int id__tipo_de_fee) {
        this.id_tipo_de_fee = id__tipo_de_fee;
    }

    public Tipo_de_Fee(){

    }
}
