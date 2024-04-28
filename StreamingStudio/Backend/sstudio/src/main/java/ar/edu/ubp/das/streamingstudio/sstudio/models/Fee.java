package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class Fee {
    private int id_fee;
    private float monto;
    private Date fecha_alta;
    private Date fecha_baja;
    private int tipo_de_fee;

    public Fee(int id_fee, float monto, Date fecha_alta, Date fecha_baja, int tipo_de_fee) {
        this.id_fee = id_fee;
        this.monto = monto;
        this.fecha_alta = fecha_alta;
        this.fecha_baja = fecha_baja;
        this.tipo_de_fee = tipo_de_fee;
    }

    public Fee(){}

    public int getId_fee() {
        return id_fee;
    }

    public void setId_fee(int id_fee) {
        this.id_fee = id_fee;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
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

    public int getTipo_de_fee() {
        return tipo_de_fee;
    }

    public void setTipo_de_fee(int tipo_de_fee) {
        this.tipo_de_fee = tipo_de_fee;
    }
}
