package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Publicista {
    private int id_publicista;
    private String nombre_de_fantasia;
    private String razon_social;
    private String email;
    private String contrasena;
    private String token_de_servicio;
    private String url_api;

    public Publicista(int id_publicista, String nombre_de_fantasia, String razon_social, String email, String contrasena, String token_de_servicio, String url_api) {
        this.id_publicista = id_publicista;
        this.nombre_de_fantasia = nombre_de_fantasia;
        this.razon_social = razon_social;
        this.email = email;
        this.contrasena = contrasena;
        this.token_de_servicio = token_de_servicio;
        this.url_api = url_api;
    }

    public int getId_publicista() {
        return id_publicista;
    }

    public void setId_publicista(int id_publicista) {
        this.id_publicista = id_publicista;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
}