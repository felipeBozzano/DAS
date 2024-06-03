package ar.edu.ubp.das.streamingstudio.beans;

public class FederacionBean {

    private Boolean verificado;
    private String codigo_de_transaccion;
    private String url;
    private String token;

    public FederacionBean() {
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public String getCodigo_de_transaccion() {
        return codigo_de_transaccion;
    }

    public void setCodigo_de_transaccion(String codigo_de_transaccion) {
        this.codigo_de_transaccion = codigo_de_transaccion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return """
                {
                \tverificado: %s,
                \tcodigo_de_transaccion: %s,
                \turl: %s,
                \ttoken: %s
                }
                """.formatted(true, codigo_de_transaccion, url, token);
    }
}
