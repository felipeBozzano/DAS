package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

import java.util.Date;

public class Transaccion {

    private int id_plataforma;
    private int id_cliente;
    private Date fecha_alta;
    private String codigo_de_transaccion;
    private String url_login_registro_plataforma;
    private String url_redireccion_propia;
    private String token;
    private String tipo_usuario;
    private Date fecha_baja;
    private Boolean facturada;

    public Transaccion(int id_plataforma, int id_cliente, Date fecha_alta, String codigo_de_transaccion, String url_login_registro_plataforma, String url_redireccion_propia, String tipo_usuario, Date fecha_baja, Boolean facturada) {
        this.id_plataforma = id_plataforma;
        this.id_cliente = id_cliente;
        this.fecha_alta = fecha_alta;
        this.codigo_de_transaccion = codigo_de_transaccion;
        this.url_login_registro_plataforma = url_login_registro_plataforma;
        this.url_redireccion_propia = url_redireccion_propia;
        this.tipo_usuario = tipo_usuario;
        this.fecha_baja = fecha_baja;
        this.facturada = facturada;
    }

    public Transaccion(int id_plataforma, int id_cliente, Date fecha_alta, String codigo_de_transaccion, String url_login_registro_plataforma, String url_redireccion_propia, String token, String tipo_usuario, Date fecha_baja, Boolean facturada) {
        this.id_plataforma = id_plataforma;
        this.id_cliente = id_cliente;
        this.fecha_alta = fecha_alta;
        this.codigo_de_transaccion = codigo_de_transaccion;
        this.url_login_registro_plataforma = url_login_registro_plataforma;
        this.url_redireccion_propia = url_redireccion_propia;
        this.token = token;
        this.tipo_usuario = tipo_usuario;
        this.fecha_baja = fecha_baja;
        this.facturada = facturada;
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

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getCodigo_de_transaccion() {
        return codigo_de_transaccion;
    }

    public void setCodigo_de_transaccion(String codigo_de_transaccion) {
        this.codigo_de_transaccion = codigo_de_transaccion;
    }

    public String getUrl_login_registro_plataforma() {
        return url_login_registro_plataforma;
    }

    public void setUrl_login_registro_plataforma(String url_login_registro_plataforma) {
        this.url_login_registro_plataforma = url_login_registro_plataforma;
    }

    public String getUrl_redireccion_propia() {
        return url_redireccion_propia;
    }

    public void setUrl_redireccion_propia(String url_redireccion_propia) {
        this.url_redireccion_propia = url_redireccion_propia;
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

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public Boolean getFacturada() {
        return facturada;
    }

    public void setFacturada(Boolean facturada) {
        this.facturada = facturada;
    }
}
