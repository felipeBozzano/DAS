package ar.edu.ubp.das.streamingstudio.beans;

import java.util.List;

public class CatalogoBean {

    private List<ContenidoBean> listaContenido;

    public CatalogoBean(){}

    public CatalogoBean(List<ContenidoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    public List<ContenidoBean> getListaContenido() {
        return listaContenido;
    }

    public void setListaContenido(List<ContenidoBean> listaContenido) {
        this.listaContenido = listaContenido;
    }

    @Override
    public String toString() {
        String temp = """
                {
                    "codigoRespuesta": "200",
                    "mensajeRespuesta": "Catalogo enviado",
                    "listaContenido": [\n%s
                """.formatted(ObjectToJson(listaContenido));
        return temp;
    }

    public String ObjectToJson(List<ContenidoBean> contenido) {

        StringBuilder json = new StringBuilder();
        for (int i = 0; i < contenido.size(); i++) {
            json.append("\t\t").append(contenido.get(i).toString());
            if (i < contenido.size() - 1) {
                json.append("\t\t},\n");
            }else{
                json.append("\t\t}\n");
            }
        }
        json.append("\t]\n");
        json.append("}");
        return json.toString();
    }
}

