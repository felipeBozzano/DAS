package ar.edu.ubp.das.streamingstudio.beans;
public class ActorBean {
    private int id_actor;
    private String nombre;
    private String apellido;

    public ActorBean(){}

    public ActorBean(int id_actor, String nombre, String apellido) {
        this.id_actor = id_actor;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_director(int id_actor) {
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

    @Override
    public String toString() {
        return """
                \t\t\t\t{
                \t\t\t\t\t"id_actor": "%s",
                \t\t\t\t\t"apellido": "%s",
                \t\t\t\t\t"nombre": "%s"
                \t\t\t\t},
                """.formatted(id_actor, apellido, nombre);
    }
}

