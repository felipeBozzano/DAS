package ar.edu.ubp.das.streamingstudio.sstudio.utils;

import java.util.HashMap;
import java.util.Map;

public class RespuestaFront {
    private Map<String, String> respuestaOK;
    private Map<String, String> respuestaError;

    public RespuestaFront(){
        respuestaOK = new HashMap<>();
        respuestaOK.put("mensaje", "1");

        respuestaError = new HashMap<>();
        respuestaError.put("mensaje", "Error");
    }

    public Map<String, String> getRespuestaOK() {
        return respuestaOK;
    }

    public Map<String, String> getRespuestaError() {
        return respuestaError;
    }

}
