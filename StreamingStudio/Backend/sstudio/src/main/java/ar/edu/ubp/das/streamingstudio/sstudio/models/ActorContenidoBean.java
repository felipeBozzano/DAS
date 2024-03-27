package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class ActorContenidoBean {

    private int id_contenido;
    private int id_actor;

    public ActorContenidoBean(int id_contenido, int id_actor) {
        this.id_contenido = id_contenido;
        this.id_actor = id_actor;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }
}
