package ar.edu.ubp.das.streamingstudio.beans;

public class VerificacionTransaccionBean {
    private boolean verificado;
    private String codigo_de_transaccion;
    private String url;

    public VerificacionTransaccionBean(boolean verificado, String codigo_de_transaccion, String url) {
        this.verificado = verificado;
        this.codigo_de_transaccion = codigo_de_transaccion;
        this.url = url;
    }

    public VerificacionTransaccionBean(boolean verificado) {
        this.verificado = verificado;
        this.codigo_de_transaccion = null;
        this.url = null;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
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

    @Override
    public String toString() {
        return """
                {
                \t"verificado": "%s",
                \t"codigo_de_transaccion": "%s",
                \t"url": "%s"
                }
                """.formatted(verificado, codigo_de_transaccion, url);
    }

}
