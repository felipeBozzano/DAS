package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class FederacionBean extends AbstractBean {
    private String codigoTransaccion;
    private String url;
    private String token;

    public FederacionBean() {
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }
}
