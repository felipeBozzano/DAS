package ar.edu.ubp.das.streamingstudio.beans;

import java.util.List;

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
    private List<GeneroBean> generos;

    public ContenidoBean() {
    }

    public ContenidoBean(String id_contenido, String titulo, String descripcion, String url_imagen, String clasificacion, boolean reciente, boolean destacado, boolean valido, List<DirectorBean> directores, List<ActorBean> actores, List<GeneroBean> generos) {
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
        this.generos = generos;
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

    public List<GeneroBean> getGeneros() {
        return generos;
    }

    public void setGeneros(List<GeneroBean> generos) {
        this.generos = generos;
    }

    @Override
    public String toString() {
        return """
                {
                    \t\t"id_contenido": "%s",
                    \t\t"titulo": "%s",
                    \t\t"descripcion": "%s",
                    \t\t"url_imagen": "%s",
                    \t\t"clasificacion": "%s",
                    \t\t"reciente": %s,
                    \t\t"destacado": %s,
                    \t\t"valido": %s,
                    \t\t"directores": %s,
                    \t\t"actores": %s,
                    \t\t"generos": %s
                """.formatted(id_contenido, titulo, descripcion, url_imagen, clasificacion, reciente, destacado,
                valido, listToJson(directores), listToJson(actores), listToJson(generos));
    }

    public String listToJson(List<?> lista) {
        if (lista == null || lista.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < lista.size(); i++) {
            json.append(lista.get(i).toString());
        }
        json.deleteCharAt(json.length() - 2);
        json.append("\t\t\t]");
        return json.toString();
    }
}


