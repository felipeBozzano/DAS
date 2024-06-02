package ar.edu.ubp.das.streamingstudio.beans;

public class GeneroBean {
    private int id_genero;
    private String descripcion;

    public GeneroBean() {
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return """
                \t\t\t\t{
                \t\t\t\t\t"id_genero": "%s",
                \t\t\t\t\t"descripcion": "%s"
                \t\t\t\t},
                """.formatted(id_genero, descripcion);
    }
}
