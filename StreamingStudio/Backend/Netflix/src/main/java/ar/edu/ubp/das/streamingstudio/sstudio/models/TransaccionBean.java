package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class TransaccionBean {
    public TransaccionBean() {
    }

    private String token_de_servicio;
    private char tipo_de_transaccion;
    private String url_de_redireccion;

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

    public char getTipo_de_transaccion() {
        return tipo_de_transaccion;
    }

    public void setTipo_de_transaccion(char tipo_de_transaccion) {
        this.tipo_de_transaccion = tipo_de_transaccion;
    }
}
