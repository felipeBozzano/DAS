package ar.edu.ubp.das.streamingstudio.sstudio.models.felipe;

public class PreferenciaBean {

    private int id_genero;
    private int id_cliente;

    public PreferenciaBean(int id_genero, int id_cliente) {
        this.id_genero = id_genero;
        this.id_cliente = id_cliente;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
}
