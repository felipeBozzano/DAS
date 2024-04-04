
package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ClienteUsuarioBean {

    private int id_cliente;
    private String usuario;
    private String contrasena;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean valido;

    ClienteUsuarioBean(int id_cliente, String usuario, String contrasena, String email, String nombre, String apellido, Boolean valido) {
        this.id_cliente = id_cliente;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.valido = valido;
    }

    ClienteUsuarioBean(String usuario, String contrasena, String email, String nombre, String apellido, Boolean valido) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.valido = valido;
    }

    public ClienteUsuarioBean(){

    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }
}
