package ar.edu.ubp.das.streamingstudio.beans;

import java.util.List;
import java.util.Objects;

public class ContenidoBean {
    private String id_contenido;
    private String titulo;
    private String descripcion;
    private String url_imagen;
    private String clasificacion;
    private boolean reciente;
    private boolean destacado;
    private boolean valido;

    private List<DirectorBean> directores;

    private List<ActorBean> actores;

    public List<DirectorBean> getDirectores() {
        return directores;
    }

    public void setDirectores(List<DirectorBean> directores) {
        this.directores = directores;
    }

    public List<ActorBean> getActores() {
        return actores;
    }

    public void setActores(List<ActorBean> actores) {
        this.actores = actores;
    }

    public ContenidoBean(){}

    public ContenidoBean(String id_contenido, String titulo, String descripcion, String url_imagen, String clasificacion, boolean reciente, boolean destacado, boolean valido, List<DirectorBean> directores, List<ActorBean> actores) {
        this.id_contenido = id_contenido;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_imagen = url_imagen;
        this.clasificacion = clasificacion;
        this.reciente = reciente;
        this.destacado = destacado;
        this.valido = valido;
        this.directores = directores;
        this.actores = actores;
    }

    public String getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(String id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
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

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @Override
    public String toString() {
        return """
                {
                    \t\tid_contenido": "%s",
                    \t\ttitulo": "%s",
                    \t\tdescripcion": "%s",
                    \t\turl_imagen": "%s",
                    \t\tclasificacion": "%s",
                    \t\treciente": %s,
                    \t\tdestacado": %s,
                    \t\tvalido": %s,
                    \t\tdirectores": %s,
                    \t\tactores": %s
                """.formatted(id_contenido, titulo, descripcion, url_imagen, clasificacion, reciente, destacado, valido, listToJson(directores), listToJson(actores));
    }

    public String listToJson(List<?> lista){
        if (lista == null || lista.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < lista.size(); i++) {
            json.append(lista.get(i).toString());
            if (i < lista.size() - 1) {
                json.append("\t\t\t\t,\n");
            }
        }
        json.append("\t\t\t]");
        return json.toString();
    }
}


