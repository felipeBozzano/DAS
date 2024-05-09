package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class TransaccionBean {
    private String token_de_servicio;
    private String tipo_de_transaccion;
    private String url_de_redireccion;
    private int id_cliente;

    public TransaccionBean() {
    }

    public String getUrl_de_redireccion() {
        return url_de_redireccion;
    }

    public void setUrl_de_redireccion(String url_de_redireccion) {
        this.url_de_redireccion = url_de_redireccion;
    }

    public String getToken_de_servicio() {
        return token_de_servicio;
    }

    public void setToken_de_servicio(String token_de_servicio) {
        this.token_de_servicio = token_de_servicio;
    }

    public String getTipo_de_transaccion() {
        return tipo_de_transaccion;
    }

    public void setTipo_de_transaccion(String tipo_de_transaccion) {
        this.tipo_de_transaccion = tipo_de_transaccion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
}
