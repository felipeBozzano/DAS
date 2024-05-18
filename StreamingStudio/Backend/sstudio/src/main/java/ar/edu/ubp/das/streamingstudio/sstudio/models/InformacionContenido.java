package ar.edu.ubp.das.streamingstudio.sstudio.models;

import java.util.List;
import java.util.Map;

public class InformacionContenido {
    private Map<String, String> infoContenido;
    private Map<String, String> genero;
    private List<Map<String, String>> directores;
    private List<Map<String, String>> actores;
    private List<Map<String, String>> plataformas;

    // Getters y Setters

    public Map<String, String> getInfoContenido() {
        return infoContenido;
    }

    public void setInfoContenido(Map<String, String> infoContenido) {
        this.infoContenido = infoContenido;
    }

    public Map<String, String> getGenero() {
        return genero;
    }

    public void setGenero(Map<String, String> genero) {
        this.genero = genero;
    }

    public List<Map<String, String>> getDirectores() {
        return directores;
    }

    public void setDirectores(List<Map<String, String>> directores) {
        this.directores = directores;
    }

    public List<Map<String, String>> getActores() {
        return actores;
    }

    public void setActores(List<Map<String, String>> actores) {
        this.actores = actores;
    }

    public List<Map<String, String>> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Map<String, String>> plataformas) {
        this.plataformas = plataformas;
    }
}
