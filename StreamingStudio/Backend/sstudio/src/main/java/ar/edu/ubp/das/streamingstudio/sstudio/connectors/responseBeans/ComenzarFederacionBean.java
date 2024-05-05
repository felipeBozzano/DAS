package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class ComenzarFederacionBean extends AbstractBean {
    private String codigoTransaccion;
    private String url;

    public ComenzarFederacionBean() {
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
