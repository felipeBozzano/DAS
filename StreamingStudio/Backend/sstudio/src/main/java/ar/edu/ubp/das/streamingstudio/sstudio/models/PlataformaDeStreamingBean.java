package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.Objects;

public class PlataformaDeStreamingBean {
    private int id_plataforma;
    private String url_imagen;
    private String url_api;

    public PlataformaDeStreamingBean() {
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlataformaDeStreamingBean that = (PlataformaDeStreamingBean) o;
        return id_plataforma == that.id_plataforma &&
                Objects.equals(url_imagen, that.url_imagen) &&
                Objects.equals(url_api, that.url_api);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_plataforma, url_imagen, url_api);
    }
}
