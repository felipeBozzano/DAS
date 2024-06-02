package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class VerificacionAutorizacionBean {
    private boolean verificado;
    private String token;

    public VerificacionAutorizacionBean(boolean verificado) {
        this.verificado = verificado;
        this.token = null;
    }

    public VerificacionAutorizacionBean(boolean verificado, String token) {
        this.verificado = verificado;
        this.token = token;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
