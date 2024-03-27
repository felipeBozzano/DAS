package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class Administrador {
    private int id_administrador;
    private String usuario;
    private String contrasena;
    private String email;

    public Administrador(int id_administrador, String usuario, String contrasena, String email) {
        this.id_administrador = id_administrador;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
    }

    public int getId_administrador() {
        return id_administrador;
    }

    public void setId_administrador(int id_administrador) {
        this.id_administrador = id_administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
