package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Date;

public class AutorizacionBean {
    public AutorizacionBean(){}

    private String token;
    private int id_cliente;
    private int codigo_de_transaccion;

    private Date fecha_de_alta;

    private Date fecha_de_baja;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getCodigo_de_transaccion() {
        return codigo_de_transaccion;
    }

    public void setCodigo_de_transaccion(int codigo_de_transaccion) {
        this.codigo_de_transaccion = codigo_de_transaccion;
    }

    public Date getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(Date fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public Date getFecha_de_baja() {
        return fecha_de_baja;
    }

    public void setFecha_de_baja(Date fecha_de_baja) {
        this.fecha_de_baja = fecha_de_baja;
    }
}
