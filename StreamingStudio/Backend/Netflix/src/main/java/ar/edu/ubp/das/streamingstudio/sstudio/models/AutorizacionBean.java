package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class AutorizacionBean {
    public AutorizacionBean() {
    }

    private String token_de_servicio;
    private String codigo_de_transaccion;

    public String getToken_de_servicio() {
        return token_de_servicio;
    }

    public void setToken_de_servicio(String token_de_servicio) {
        this.token_de_servicio = token_de_servicio;
    }

    public String getCodigo_de_transaccion() {
        return codigo_de_transaccion;
    }

    public void setCodigo_de_transaccion(String codigo_de_transaccion) {
        this.codigo_de_transaccion = codigo_de_transaccion;
    }
}
