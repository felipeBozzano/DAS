package ar.edu.ubp.das.streamingstudio.beans;

public class UsuarioBean {
    private int id_cliente;
    private String usuario;
    private String contrasena;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean valido;

    private String mensaje;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    UsuarioBean(int id_cliente, String usuario, String contrasena, String email, String nombre, String apellido, Boolean valido, String mensaje) {
        this.id_cliente = id_cliente;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.valido = valido;
        this.mensaje = mensaje;
    }

    UsuarioBean(String usuario, String contrasena, String email, String nombre, String apellido, Boolean valido) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.valido = valido;
    }

    public UsuarioBean(){

    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getcontrasena() {
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

    public void setcontrasena(String contrasena) {
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

    @Override
    public String toString(){
        return """
                {
                \t"id_cliente": "%s",
                \t"apellido": "%s",
                \t"nombre": "%s"
                \t"email": "%s"
                \t"mensaje": "%s"
                }
                """.formatted(id_cliente, apellido, nombre, email, mensaje);
    }
}
