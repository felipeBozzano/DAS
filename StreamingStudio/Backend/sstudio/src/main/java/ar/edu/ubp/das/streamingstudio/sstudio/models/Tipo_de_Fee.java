package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class Tipo_de_Fee {
    private int id__tipo_de_fee;
    private String tipo_de_fee;
    private String descripcion;

    public int getId__tipo_de_fee() {
        return id__tipo_de_fee;
    }

    public void setId__tipo_de_fee(int id__tipo_de_fee) {
        this.id__tipo_de_fee = id__tipo_de_fee;
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
        this.id__tipo_de_fee = id__tipo_de_fee;
    }

    public Tipo_de_Fee(){

    }
}
