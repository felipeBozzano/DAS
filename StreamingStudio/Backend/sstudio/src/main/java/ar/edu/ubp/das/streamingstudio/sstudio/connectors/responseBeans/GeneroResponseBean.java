package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class GeneroResponseBean {
    private Boolean accion;
    private Boolean comedia;
    private Boolean drama;

    public GeneroResponseBean() {
    }

    public Boolean getAccion() {
        return accion;
    }

    public void setAccion(Boolean accion) {
        this.accion = accion;
    }

    public Boolean getComedia() {
        return comedia;
    }

    public void setComedia(Boolean comedia) {
        this.comedia = comedia;
    }

    public Boolean getDrama() {
        return drama;
    }

    public void setDrama(Boolean drama) {
        this.drama = drama;
    }
}
