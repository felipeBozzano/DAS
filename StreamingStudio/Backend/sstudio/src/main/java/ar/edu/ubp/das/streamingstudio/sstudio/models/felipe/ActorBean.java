package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class ActorBean {

    private int id_actor;
    private String nombre;
    private String apellido;

    public ActorBean(int id_actor, String nombre, String apellido) {
        this.id_actor = id_actor;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
