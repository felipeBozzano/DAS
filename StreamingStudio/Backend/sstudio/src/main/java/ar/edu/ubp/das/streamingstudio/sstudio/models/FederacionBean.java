package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class FederacionBean {

    private int id_plataforma;
    private int id_cliente;
    private String token;
    private String tipo_usuario;
    private Boolean facturada;
    private int cantidad_de_federaciones;

    private double monto_federacion;

    public double getMonto_federacion() {
        return monto_federacion;
    }

    public void setMonto_federacion(double monto_federacion) {
        this.monto_federacion = monto_federacion;
    }

    public int getCantidad_de_federaciones() {
        return cantidad_de_federaciones;
    }

    public void setCantidad_de_federaciones(int cantidad_de_federaciones) {
        this.cantidad_de_federaciones = cantidad_de_federaciones;
    }

    public FederacionBean(int id_plataforma, int id_cliente, String token, String tipo_usuario, Boolean facturada) {
        this.id_plataforma = id_plataforma;
        this.id_cliente = id_cliente;
        this.token = token;
        this.tipo_usuario = tipo_usuario;
        this.facturada = facturada;
    }

    public FederacionBean(){}

    public FederacionBean(int id_plataforma, int id_cliente){
        this.id_plataforma = id_plataforma;
        this.id_cliente = id_cliente;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public Boolean getFacturada() {
        return facturada;
    }

    public void setFacturada(Boolean facturada) {
        this.facturada = facturada;
    }
}
