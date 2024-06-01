package ar.edu.ubp.das.streamingstudio.platforms;

import ar.edu.ubp.das.streamingstudio.beans.PublicidadResponseBean;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@WebService
@XmlSeeAlso(PublicidadResponseBean.class)
public class PublicistaBicicletasWS {
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;

    public PublicistaBicicletasWS() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");
            sql_conection_string = properties.getProperty("sql_conection_string");
            sql_user = properties.getProperty("sql_user");
            sql_pass = properties.getProperty("sql_pass");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean verificarTokenDePartner(String token_de_partner) throws ClassNotFoundException, SQLException {
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
        return temp.equals("true");
    }

    @WebMethod()
    @WebResult(name = "obtenerPublicidades")
    public String obtenerPublicidades(@WebParam(name = "token_de_partner") String token_de_partner) throws SQLException, ClassNotFoundException {

        if (verificarTokenDePartner(token_de_partner)) {
            Connection conn;
            CallableStatement stmt;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            stmt = conn.prepareCall("{CALL dbo.Obtener_Datos_de_Publicidades}");
            rs = stmt.executeQuery();
            List<PublicidadResponseBean> lista_de_publicidades = new LinkedList<>();
            while (rs.next()) {
                PublicidadResponseBean pub = new PublicidadResponseBean();
                pub.setUrl_de_imagen(rs.getString("url_de_imagen"));
                pub.setUrl_de_publicidad(rs.getString("url_de_publicidad"));
                pub.setCodigo_publicidad(rs.getString("codigo_publicidad"));
                lista_de_publicidades.add(pub);
            }

            if (lista_de_publicidades.isEmpty())
                return """
                        {
                        \t"codigoRespuesta": "1",
                        \t"mensajeRespuesta": "No hay publicidades que actualizar"
                        }
                        """;

            StringBuilder lista_de_publicidades_json = new StringBuilder("""
                    {
                    \t"codigoRespuesta": "200",
                    \t"mensajeRespuesta": "success",
                    \t"listaPublicidades": [
                    """);

            for (PublicidadResponseBean publicidad : lista_de_publicidades)
                lista_de_publicidades_json.append("""
                        %s""".formatted(publicidad.to_json()));

            String resultado = lista_de_publicidades_json.substring(0, lista_de_publicidades_json.length() - 2);
            resultado += """
                    \n\t]
                    }""";

            return resultado;
        } else {
            return """
                    {
                    \t"codigoRespuesta": "-1",
                    \t"mensajeRespuesta": "Partner no verificado"
                    }
                    """;
        }
    }

    @WebMethod()
    @WebResult(name = "obtenerEstadisticas")
    public String obtenerEstadisticas(@WebParam(name = "token_de_partner") String token_de_partner,
                                      @WebParam(name = "total") String total,
                                      @WebParam(name = "fecha") String fecha,
                                      @WebParam(name = "descripcion") String descripcion) throws SQLException, ClassNotFoundException {

        if (verificarTokenDePartner(token_de_partner)) {
            Date fecha_formateada = new Date(2020, 01, 01);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate localDate = LocalDate.parse(fecha, formatter);
                fecha_formateada = Date.valueOf(localDate);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }

            Connection conn;
            CallableStatement stmt;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            try {
                stmt = conn.prepareCall("{CALL dbo.Registrar_Reporte(?, ?, ?)}");
                stmt.setFloat("total", Float.parseFloat(total));
                stmt.setDate("fecha", fecha_formateada);
                stmt.setString("descripcion", descripcion);
                stmt.executeUpdate();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return """
                    {
                    "codigoRespuesta": "200",
                    "mensajeRespuesta": "Reporte recibido"
                    }
                    """;
        } else {
            return """
                    {
                    \t"codigoRespuesta": "-1",
                    \t"mensajeRespuesta": "Partner no verificado"
                    }
                    """;
        }
    }
}
