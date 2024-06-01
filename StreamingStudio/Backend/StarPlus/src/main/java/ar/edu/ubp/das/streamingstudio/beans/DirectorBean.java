package ar.edu.ubp.das.streamingstudio.beans;

public class DirectorBean {

    private int id_director;
    private String nombre;
    private String apellido;

    public DirectorBean(){}

    public DirectorBean(int id_director, String nombre, String apellido) {
        this.id_director = id_director;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_director() {
        return id_director;
    }

    public void setId_director(int id_director) {
        this.id_director = id_director;
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
                \t\t\t\t\t"id_director": "%s",
                \t\t\t\t\t"apellido": "%s",
                \t\t\t\t\t"nombre": "%s"
                \t\t\t\t},
                """.formatted(id_director, apellido, nombre);
    }
}

