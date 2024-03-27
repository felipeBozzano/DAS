package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class TipoUsuarioBean {

    private int id_tipo_usuario;
    private String descripcion;

    public TipoUsuarioBean(int id_tipo_usuario, String descripcion) {
        this.id_tipo_usuario = id_tipo_usuario;
        this.descripcion = descripcion;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
