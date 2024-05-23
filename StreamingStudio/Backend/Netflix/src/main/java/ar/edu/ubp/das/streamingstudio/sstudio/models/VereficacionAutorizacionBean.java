package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class VereficacionAutorizacionBean {
    private boolean verificado;
    private String token;

    public VereficacionAutorizacionBean(boolean verificado) {
        this.verificado = verificado;
        this.token = null;
    }

    public VereficacionAutorizacionBean(boolean verificado, String token) {
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
