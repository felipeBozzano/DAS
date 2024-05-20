package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class PublicidadBean {
    private int id_publicista;
    private int id_publicidad;
    private String codigo_publicidad;
    private String url_de_imagen;
    private String url_de_publicidad;

    public PublicidadBean(String codigo_publicidad, String url_de_imagen, String url_de_publicidad) {
        this.codigo_publicidad = codigo_publicidad;
        this.url_de_imagen = url_de_imagen;
        this.url_de_publicidad = url_de_publicidad;
        this.id_publicista = 0;
        this.id_publicidad = 0;
    }

    public PublicidadBean() {
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
    }

    public int getId_publicidad() {
        return id_publicidad;
    }

    public void setId_publicidad(int id_publicidad) {
        this.id_publicidad = id_publicidad;
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

    @Override
    public String toString() {
        return "PublicidadBean{" +
                "id_publicista=" + id_publicista +
                ", id_publicidad=" + id_publicidad +
                ", codigo_publicidad='" + codigo_publicidad + '\'' +
                ", url_de_imagen='" + url_de_imagen + '\'' +
                ", url_de_publicidad='" + url_de_publicidad + '\'' +
                '}';
    }
}
