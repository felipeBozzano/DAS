package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class DirectorContenidoBean {
    private int id_contenido;
    private int id_director;

    public DirectorContenidoBean(int id_contenido, int id_director) {
        this.id_contenido = id_contenido;
        this.id_director = id_director;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getId_director() {
        return id_director;
    }

    public void setId_director(int id_director) {
        this.id_director = id_director;
    }
}
