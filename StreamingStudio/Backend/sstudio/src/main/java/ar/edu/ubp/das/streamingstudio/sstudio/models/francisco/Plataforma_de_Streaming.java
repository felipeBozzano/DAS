package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Plataforma_de_Streaming {
    private int id_plataforma;
    private String nombre_de_fantasaia;
    private String razon_social;
    private String url_imagen;
    private String token_de_servicio;
    private String url_api;
    private boolean valido;

    public Plataforma_de_Streaming(int id_plataforma, String nombre_de_fantasaia, String razon_social, String url_imagen, String token_de_servicio, String url_api, boolean valido) {
        this.id_plataforma = id_plataforma;
        this.nombre_de_fantasaia = nombre_de_fantasaia;
        this.razon_social = razon_social;
        this.url_imagen = url_imagen;
        this.token_de_servicio = token_de_servicio;
        this.url_api = url_api;
        this.valido = valido;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public String getNombre_de_fantasaia() {
        return nombre_de_fantasaia;
    }

    public void setNombre_de_fantasaia(String nombre_de_fantasaia) {
        this.nombre_de_fantasaia = nombre_de_fantasaia;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getToken_de_servicio() {
        return token_de_servicio;
    }

    public void setToken_de_servicio(String token_de_servicio) {
        this.token_de_servicio = token_de_servicio;
    }

    public String getUrl_api() {
        return url_api;
    }

    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }
}
