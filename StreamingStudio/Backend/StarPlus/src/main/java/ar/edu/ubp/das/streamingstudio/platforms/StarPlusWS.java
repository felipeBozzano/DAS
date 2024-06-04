package ar.edu.ubp.das.streamingstudio.platforms;

import ar.edu.ubp.das.streamingstudio.beans.*;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import ar.edu.ubp.das.streamingstudio.utils.*;

@WebService
@XmlSeeAlso(CatalogoBean.class)
public class StarPlusWS {
    private Utils utils;
    private String driver_sql;
    private String sql_conection_string;
    private String sql_user;
    private String sql_pass;
    private final String partner_no_verificado = """
                    {
                    \t"codigoRespuesta": "-1",
                    \t"mensajeRespuesta": "Partner no verificado"
                    }
                    """;

    public StarPlusWS() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            driver_sql = properties.getProperty("driver_sql");
            sql_conection_string = properties.getProperty("sql_conection_string");
            sql_user = properties.getProperty("sql_user");
            sql_pass = properties.getProperty("sql_pass");
            utils = new Utils();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @WebMethod()
    @WebResult(name = "catalogo")
    public String catalogo(@WebParam(name = "token_de_partner") String token_de_partner) throws ClassNotFoundException,
            SQLException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            CallableStatement stmt;
            ResultSet rs;
            stmt = conn.prepareCall("{CALL dbo.Obtener_Contenido_Actual}");
            rs = stmt.executeQuery();
            List<ContenidoBean> listaContenido = new LinkedList<>();

            while (rs.next()) {
                ContenidoBean contenido = new ContenidoBean();
                String id_contenido = rs.getString("id_contenido");

                List<DirectorBean> listaDirectores = utils.obtenerDirectores(id_contenido, driver_sql, sql_conection_string, sql_user, sql_pass);
                List<ActorBean> listaActores = utils.obtenerActores(id_contenido, driver_sql, sql_conection_string, sql_user, sql_pass);
                List<GeneroBean> listaGeneros = utils.obtenerGeneros(id_contenido, driver_sql, sql_conection_string, sql_user, sql_pass);

                contenido.setId_contenido(rs.getString("id_contenido"));
                contenido.setTitulo(rs.getString("titulo"));
                contenido.setDescripcion(rs.getString("descripcion"));
                contenido.setUrl_imagen(rs.getString("url_imagen"));
                contenido.setClasificacion(rs.getString("clasificacion"));
                contenido.setReciente(rs.getBoolean("reciente"));
                contenido.setDestacado(rs.getBoolean("destacado"));
                contenido.setValido(rs.getBoolean("valido"));
                contenido.setActores(listaActores);
                contenido.setDirectores(listaDirectores);
                contenido.setGeneros(listaGeneros);

                listaContenido.add(contenido);
            }

            CatalogoBean catalogo = new CatalogoBean(listaContenido);
            System.out.println(catalogo);
            return catalogo.toString();
        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "obtenerEstadisticas")
    public String obtenerEstadisticas(@WebParam(name = "token_de_partner") String token_de_partner,
                                      @WebParam(name = "total") String total,
                                      @WebParam(name = "fecha") String fecha,
                                      @WebParam(name = "descripcion") String descripcion) throws SQLException,
            ClassNotFoundException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            CallableStatement stmt;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            Date fecha_formateada;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(fecha, formatter);
            fecha_formateada = Date.valueOf(localDate);

            stmt = conn.prepareCall("{CALL dbo.Registrar_Reporte(?, ?, ?)}");
            stmt.setFloat("total", Float.parseFloat(total));
            stmt.setDate("fecha", fecha_formateada);
            stmt.setString("descripcion", descripcion);
            stmt.executeUpdate();

            return """
                    {
                        "codigoRespuesta": "200",
                        "mensajeRespuesta": "Reporte recibido"
                    }
                    """;
        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "crearTransaccion")
    public String crearTransaccion(@WebParam(name = "tipo_de_transaccion") String tipo_de_transaccion,
                                   @WebParam(name = "url_redireccion_ss") String url_redireccion_ss,
                                   @WebParam(name = "token_de_partner") String token_de_partner) throws
            ClassNotFoundException, SQLException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            CallableStatement stmt;

            UUID codigo_de_transaccion = UUID.randomUUID();
            String codigo_de_transaccion_string = codigo_de_transaccion.toString();

            String url_de_redireccion;
            if (tipo_de_transaccion.equals("L"))
                url_de_redireccion = "http://localhost:4204/login";
            else
                url_de_redireccion = "http://localhost:4204/register";

            stmt = conn.prepareCall("{CALL dbo.Crear_Transaccion(?,?,?)}");
            stmt.setString("codigo_de_transaccion", codigo_de_transaccion_string);
            stmt.setString("url_de_redireccion", url_redireccion_ss);
            stmt.setString("tipo_de_transaccion", tipo_de_transaccion);
            stmt.executeUpdate();

            VerificacionTransaccionBean respuesta = new VerificacionTransaccionBean(true, codigo_de_transaccion_string, url_de_redireccion);

            return respuesta.toString();
        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "verificarAutorizacion")
    public String verificarAutorizacion(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion) throws
            ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Verificar_Autorizacion(?)}");
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        rs = stmt.executeQuery();

        TransaccionBean transaccionBean = new TransaccionBean();
        while (rs.next()) {
            transaccionBean.setToken_de_servicio(rs.getString("token_de_servicio"));
            transaccionBean.setId_cliente(rs.getInt("id_cliente"));
            transaccionBean.setTipo_de_transaccion(rs.getString("codigo_de_transaccion"));
            transaccionBean.setUrl_de_redireccion(rs.getString("url_de_redireccion"));

        }

        return transaccionBean.toString();
    }

    @WebMethod()
    @WebResult(name = "crearAutorizacion")
    public String crearAutorizacion(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion,
                                    @WebParam(name = "id_cliente") String id_cliente) throws ClassNotFoundException,
            SQLException {
        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        UUID token = UUID.randomUUID();
        String token_string = token.toString();

        stmt = conn.prepareCall("{CALL dbo.Crear_Autorizacion(?,?)}");
        stmt.setInt("id_cliente", Integer.parseInt(id_cliente));
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        stmt.setString("token", token_string);
        stmt.executeUpdate();

        String url_de_redireccion = utils.obtenerUrlDeRedireccion(codigo_de_transaccion, driver_sql, sql_conection_string, sql_user, sql_pass);
        return """
                {
                    "codigo_de_transaccion": "%s",
                    "id_cliente": "%s",
                    "url_de_redireccion": "%s"
                }
                """.formatted(codigo_de_transaccion, id_cliente, url_de_redireccion);
    }

    @WebMethod()
    @WebResult(name = "obtenerToken")
    public String obtenerToken(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion,
                               @WebParam(name = "token_de_partner") String token_de_partner) throws
            ClassNotFoundException, SQLException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            CallableStatement stmt;

            FederacionBean federacionBean = new FederacionBean();

            stmt = conn.prepareCall("{CALL dbo.Obtener_Token(?)}");
            stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
            rs = stmt.executeQuery();

            while (rs.next()) {
                federacionBean.setToken(rs.getString("token"));
                federacionBean.setCodigo_de_transaccion(codigo_de_transaccion);
            }

            return federacionBean.toString();
        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "obtenerSesion")
    public String obtenerSesion(@WebParam (name = "token_de_partner") String token_de_partner,
                                @WebParam (name = "token_de_usuario") String token_de_usuario) throws
            ClassNotFoundException, SQLException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            try {
                Map<String, Integer> ids = utils.obtenerIds(token_de_partner, token_de_usuario, driver_sql, sql_conection_string, sql_user, sql_pass);
                int id_partner = ids.get("id_partner");
                int id_cliente = ids.get("id_cliente");

                UUID sesion = UUID.randomUUID();
                String sesion_string = sesion.toString();
                utils.crearSesion(id_partner, id_cliente, sesion_string, driver_sql, sql_conection_string, sql_user, sql_pass);

                return """
                        {
                        \t"sesion": "%s",
                        \t"codigoRespuesta": "200",
                        \t"mensajeRespuesta": "La sesion fue creada"
                        }""".formatted(sesion_string);

            } catch (Exception e) {
                return """
                        {
                        \tsesion: %s,
                        \tcodigoRespuesta: %s,
                        \tmensajeRespuesta: %s
                        }
                        """.formatted(null, "1", "La sesion no pudo ser creada");
            }
        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "obtenerUrlDeContenido")
    public String obtenerUrlDeContenido(@WebParam(name = "token_de_partner") String token_de_partner,
                                        @WebParam(name = "token_de_sesion") String token_de_sesion,
                                        @WebParam(name = "id_contenido") String id_contenido) throws SQLException,
            ClassNotFoundException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            if (!utils.usarSesion(token_de_sesion, driver_sql, sql_conection_string, sql_user, sql_pass)) {
                return """
                        {
                            "codigoRespuesta": "1",
                            "mensajeRespuesta": "Sesion erronea o ya utilizada"
                        }
                        """;
            }

            Connection conn;
            CallableStatement stmt;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            String url_de_contenido = null;

            try {
                stmt = conn.prepareCall("{CALL dbo.Obtener_Url_de_Contenido(?)}");
                stmt.setString("id_contenido", id_contenido);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    url_de_contenido = rs.getString("url_reproduccion");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return """
                    {
                        "codigoRespuesta": "200",
                        "mensajeRespuesta": "Sesion correcta",
                        "url": "%s"
                    }""".formatted(url_de_contenido);

        } else {
            return partner_no_verificado;
        }
    }

    @WebMethod()
    @WebResult(name = "desvincular")
    public String desvincular(@WebParam (name = "token_de_servicio") String token_de_servicio) throws ClassNotFoundException, SQLException {

        Connection conn;
        CallableStatement stmt;
        int rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        Map<String, String> respuesta = new HashMap<>();

            stmt = conn.prepareCall("{CALL dbo.desvincular(?)}");
            stmt.setString("token", token_de_servicio);
            rs = stmt.executeUpdate();

            if(rs == 1) {
                return """
                        {
                        \t"mensaje": "%s",
                        \t"codigo": "%s"
                        }
                        """.formatted("Federacion desvinculada con exito", "200");
            }else{
                return """
                        {
                        \t"mensaje": "%s",
                        \t"codigo": "%s"
                        }
                        """.formatted("Fallo la desvinculacion", "500");
        }
    }
}
