package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Fee_Plataforma {
    private int id_plataforma;
    private int id_fee;

    public Fee_Plataforma(int id_plataforma, int id_fee) {
        this.id_plataforma = id_plataforma;
        this.id_fee = id_fee;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public int getId_fee() {
        return id_fee;
    }

    public void setId_fee(int id_fee) {
        this.id_fee = id_fee;
    }
}
