package ar.edu.ubp.das.streamingstudio.publicistasbicicletas.platforms;

import ar.edu.ubp.das.streamingstudio.publicistasbicicletas.beans.PublicidadBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@WebService
public class PublicidadBicicletasWS {
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;
    private Gson gson;

    public PublicidadBicicletasWS() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");
            ;
            sql_conection_string = properties.getProperty("sql_conection_string");
            ;
            sql_user = properties.getProperty("sql_user");
            ;
            sql_pass = properties.getProperty("sql_pass");
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gson = gsonBuilder.create();
    }

    @WebMethod
    @WebResult(name = "obtener_publicidades")
    public List<PublicidadBean> obtenerPublicidades(String token_de_partner) throws ClassNotFoundException, SQLException {
        List<PublicidadBean> publicidades = new LinkedList<>();

        Connection conn;
        CallableStatement stmt;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        stmt = conn.prepareCall("{CALL dbo.Verificar_Token_de_Partner(?)}");
        stmt.setString("token", token_de_partner);

        String temp = "";
        rs = stmt.executeQuery();
        while (rs.next()) {
             temp = rs.getString("ExistePartner");
        }

        if (temp.equals("true")) {
            stmt = conn.prepareCall("{CALL dbo.Obtener_Datos_de_Publicidades}");

            rs = stmt.executeQuery();
            while (rs.next()) {
                PublicidadBean pub = new PublicidadBean();
                pub.setTipo_banner(rs.getString("banner_code"));
                pub.setUrl_de_imagen(rs.getString("url_imagen"));
                pub.setUrl_de_publicidad(rs.getString("url_contenido"));
                pub.setId_publicidad(rs.getString("codigo_unico_id"));
                pub.setFecha_de_alta(rs.getString("fecha_de_alta"));
                pub.setFecha_de_baja(rs.getString("fecha_de_baja"));
                publicidades.add(pub);
            }
        }
        else {
            publicidades.add(new PublicidadBean());
        }
        return publicidades;
    }
}
