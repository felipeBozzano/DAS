package ar.edu.ubp.das.streamingstudio.sstudio.connectors.responseBeans;

public class FederacionDesvinculada extends AbstractBean {
    public String mensaje;
    public String codigo;

    public FederacionDesvinculada(){}

    public FederacionDesvinculada(String mensaje, String codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
