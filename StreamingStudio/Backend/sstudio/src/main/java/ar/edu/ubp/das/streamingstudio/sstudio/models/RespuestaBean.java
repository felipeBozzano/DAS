package ar.edu.ubp.das.streamingstudio.sstudio.models;

public class RespuestaBean {
    private Codigo status;
    private String mensaje;
    private String body;

    public RespuestaBean (){}

    public RespuestaBean (Codigo cod, String msj, String body){
        this.status = cod;
        this.mensaje = msj;
        this.body = body;
    }

    public Codigo getStatus() {
        return status;
    }
    public void setStatus(Codigo status) {
        this.status = status;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}
