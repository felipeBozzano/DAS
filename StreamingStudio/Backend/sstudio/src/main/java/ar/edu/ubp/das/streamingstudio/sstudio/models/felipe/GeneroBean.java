package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class GeneroBean {

    private int id_contenido;
    private int id_genero;

    public GeneroBean(int id_contenido, int id_genero) {
        this.id_contenido = id_contenido;
        this.id_genero = id_genero;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }
}
