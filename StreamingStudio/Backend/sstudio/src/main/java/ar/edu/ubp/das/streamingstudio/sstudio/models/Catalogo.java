package ar.edu.ubp.das.streamingstudio.sstudio.models;
import java.util.Date;

public class Catalogo {
    private int id_contenido;
    private int id_plataforma;
    private boolean reciente;
    private boolean destacado;
    private String id_en_plataforma;
    private Date fecha_de_alta;
    private Date fecha_de_baja;

    public Catalogo(int id_contenido, int id_plataforma, boolean reciente, boolean destacado, String id_en_plataforma, Date fecha_de_alta, Date fecha_de_baja) {
        this.id_contenido = id_contenido;
        this.id_plataforma = id_plataforma;
        this.reciente = reciente;
        this.destacado = destacado;
        this.id_en_plataforma = id_en_plataforma;
        this.fecha_de_alta = fecha_de_alta;
        this.fecha_de_baja = fecha_de_baja;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getId_plataforma() {
        return id_plataforma;
    }

    public void setId_plataforma(int id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    public boolean isReciente() {
        return reciente;
    }

    public void setReciente(boolean reciente) {
        this.reciente = reciente;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public String getId_en_plataforma() {
        return id_en_plataforma;
    }

    public void setId_en_plataforma(String id_en_plataforma) {
        this.id_en_plataforma = id_en_plataforma;
    }

    public Date getFecha_de_alta() {
        return fecha_de_alta;
    }

    public void setFecha_de_alta(Date fecha_de_alta) {
        this.fecha_de_alta = fecha_de_alta;
    }

    public Date getFecha_de_baja() {
        return fecha_de_baja;
    }

    public void setFecha_de_baja(Date fecha_de_baja) {
        this.fecha_de_baja = fecha_de_baja;
    }
}