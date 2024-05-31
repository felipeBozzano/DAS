package ar.edu.ubp.das.streamingstudio.platforms;

import ar.edu.ubp.das.streamingstudio.beans.ActorBean;
import ar.edu.ubp.das.streamingstudio.beans.CatalogoBean;
import ar.edu.ubp.das.streamingstudio.beans.ContenidoBean;
import ar.edu.ubp.das.streamingstudio.beans.DirectorBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonToken;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@WebService
@XmlSeeAlso(CatalogoBean.class)
public class StarPlusWS {
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;
    private Gson gson;

    public StarPlusWS(){
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");;
            sql_conection_string = properties.getProperty("sql_conection_string");;
            sql_user = properties.getProperty("sql_user");;
            sql_pass = properties.getProperty("sql_pass");;
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gson = gsonBuilder.create();
    }

    @WebMethod()
    @WebResult(name = "catalogo")
    public List<ContenidoBean> obtenerCatalogo(@WebParam (name = "token_de_partner") String token_de_partner) throws ClassNotFoundException, SQLException {

        Connection conn;
        CallableStatement stmt;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        System.out.println("Token de partner: " + token_de_partner);

        stmt = conn.prepareCall("{CALL dbo.Verificar_Token_de_Partner(?)}");
        stmt.setString("token", token_de_partner);


        String temp = "";
        rs = stmt.executeQuery();


            CallableStatement stmt_catalogo;
            ResultSet rs_catalogo;
            stmt_catalogo = conn.prepareCall("{CALL dbo.Obtener_Contenido_Actual}");
            rs_catalogo = stmt_catalogo.executeQuery();
            List<ContenidoBean> listaContenido = new LinkedList<>();
            while (rs_catalogo.next()) {
                ContenidoBean contenido = new ContenidoBean();

                // DIRECTORES
                CallableStatement stmt_directores;
                ResultSet rs_directores;
                stmt_directores = conn.prepareCall("{CALL dbo.Obtener_Actores}");
                stmt.setString("id_contenido", rs_catalogo.getString("id_contenido"));
                rs_directores = stmt_directores.executeQuery();
                List<DirectorBean> listaDirectores = new LinkedList<>();
                while (rs_directores.next()) {
                    DirectorBean director = new DirectorBean();
                    director.setApellido(rs_directores.getString("apellido"));
                    director.setNombre(rs_directores.getString("nombre"));
                    director.setId_director(rs_directores.getInt("id_cator"));
                    listaDirectores.add(director);
                }

                // ACTORES
                CallableStatement stmt_actores;
                ResultSet rs_actores;
                stmt_actores = conn.prepareCall("{CALL dbo.Obtener_Actores}");
                stmt.setString("id_contenido", rs_catalogo.getString("id_contenido"));
                rs_actores = stmt_actores.executeQuery();
                List<ActorBean> listaActores = new LinkedList<>();
                while (rs_actores.next()) {
                    ActorBean actor = new ActorBean();
                    actor.setApellido(rs_actores.getString("apellido"));
                    actor.setNombre(rs_actores.getString("nombre"));
                    actor.setId_director(rs_actores.getInt("id_cator"));
                    listaActores.add(actor);
                }

                // CONTENIDO
                contenido.setTitulo(rs_catalogo.getString("titulo"));
                contenido.setDescripcion(rs_catalogo.getString("descripcion"));
                contenido.setActores(listaActores);
                contenido.setDirectores(listaDirectores);
                contenido.setDescripcion(rs_catalogo.getString("descripcion"));

                listaContenido.add(contenido);
            }

        return listaContenido;
    }
}
