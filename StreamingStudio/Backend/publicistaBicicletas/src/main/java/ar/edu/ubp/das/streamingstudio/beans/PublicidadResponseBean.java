package ar.edu.ubp.das.streamingstudio.beans;

public class PublicidadResponseBean {
    private String codigo_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;

    public PublicidadResponseBean() {
    }

    public String getCodigo_publicidad() {
        return codigo_publicidad;
    }

    public void setCodigo_publicidad(String codigo_publicidad) {
        this.codigo_publicidad = codigo_publicidad;
    }

    public String getUrl_de_imagen() {
        return url_de_imagen;
    }

    public void setUrl_de_imagen(String url_de_imagen) {
        this.url_de_imagen = url_de_imagen;
    }

    public String getUrl_de_publicidad() {
        return url_de_publicidad;
    }

    public void setUrl_de_publicidad(String url_de_publicidad) {
        this.url_de_publicidad = url_de_publicidad;
    }

    public String to_json() {
        return """
                \t\t{
                \t\t\t"codigo_publicidad": "%s",
                \t\t\t"url_de_imagen": "%s",
                \t\t\t"url_de_publicidad": "%s"
                \t\t},
                """.formatted(codigo_publicidad, url_de_imagen, url_de_publicidad);
    }
}
