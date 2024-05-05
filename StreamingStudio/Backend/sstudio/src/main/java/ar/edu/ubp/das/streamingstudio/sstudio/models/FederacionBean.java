package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class FederacionBean {

    private int id_plataforma;
    private int id_cliente;
    private String token;
    private String tipo_transaccion;
    private Boolean facturada;
    private int cantidad_de_federaciones;
    private double monto_federacion;
    private String codigo_de_transaccion;

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
        this.tipo_transaccion = tipo_usuario;
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

    public String getTipo_transaccion() {
        return tipo_transaccion;
    }

    public void setTipo_transaccion(String tipo_transaccion) {
        this.tipo_transaccion = tipo_transaccion;
    }

    public Boolean getFacturada() {
        return facturada;
    }

    public void setFacturada(Boolean facturada) {
        this.facturada = facturada;
    }

    public String getCodigo_de_transaccion() {
        return codigo_de_transaccion;
    }

    public void setCodigo_de_transaccion(String codigo_de_transaccion) {
        this.codigo_de_transaccion = codigo_de_transaccion;
    }
}
