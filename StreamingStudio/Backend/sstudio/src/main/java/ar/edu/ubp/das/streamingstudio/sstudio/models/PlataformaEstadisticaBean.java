package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class PlataformaEstadisticaBean {
    private String nombre_de_fantasia;
    private String razon_social;
    private String token_de_servicio;
    private String url_api;
    private String protocolo_api;

    public PlataformaEstadisticaBean() {
    }

    public String getNombre_de_fantasia() {
        return nombre_de_fantasia;
    }

    public void setNombre_de_fantasia(String nombre_de_fantasia) {
        this.nombre_de_fantasia = nombre_de_fantasia;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getToken_de_servicio() {
        return token_de_servicio;
    }

    public void setToken_de_servicio(String token_de_servicio) {
        this.token_de_servicio = token_de_servicio;
    }

    public String getProtocolo_api() {
        return protocolo_api;
    }

    public void setProtocolo_api(String protocolo_api) {
        this.protocolo_api = protocolo_api;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

}
