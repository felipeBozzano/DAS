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
    public String catalogo(@WebParam(name = "token_de_partner") String token_de_partner) throws ClassNotFoundException, SQLException {

        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        System.out.println("Token de partner: " + token_de_partner);
        CatalogoBean catalogo = new CatalogoBean();

        CallableStatement stmt;
        ResultSet rs_catalogo;
        stmt = conn.prepareCall("{CALL dbo.Obtener_Contenido_Actual}");
        rs_catalogo = stmt.executeQuery();
        List<ContenidoBean> listaContenido = new LinkedList<>();
        while (rs_catalogo.next()) {
            ContenidoBean contenido = new ContenidoBean();
            System.out.println(rs_catalogo.getString("id_contenido"));

            // DIRECTORES
            CallableStatement stmt_directores;
            ResultSet rs_directores;
            stmt_directores = conn.prepareCall("{CALL dbo.Obtener_Directores(?)}");
            stmt_directores.setString("id_contenido", rs_catalogo.getString("id_contenido"));
            rs_directores = stmt_directores.executeQuery();
            List<DirectorBean> listaDirectores = new LinkedList<>();
            while (rs_directores.next()) {
                DirectorBean director = new DirectorBean();
                director.setApellido(rs_directores.getString("apellido"));
                director.setNombre(rs_directores.getString("nombre"));
                director.setId_director(rs_directores.getInt("id_director"));
                listaDirectores.add(director);
            }
            rs_directores.close();
            stmt_directores.close();

            // ACTORES
            CallableStatement stmt_actores;
            ResultSet rs_actores;
            stmt_actores = conn.prepareCall("{CALL dbo.Obtener_Actores(?)}");
            stmt_actores.setString("id_contenido", rs_catalogo.getString("id_contenido"));
            rs_actores = stmt_actores.executeQuery();
            List<ActorBean> listaActores = new LinkedList<>();
            while (rs_actores.next()) {
                ActorBean actor = new ActorBean();
                actor.setApellido(rs_actores.getString("apellido"));
                actor.setNombre(rs_actores.getString("nombre"));
                actor.setId_director(rs_actores.getInt("id_actor"));
                listaActores.add(actor);
            }
            rs_actores.close();
            stmt_actores.close();

            // GENEROS
            CallableStatement stmt_generos;
            ResultSet rs_generos;
            stmt_generos = conn.prepareCall("{CALL dbo.Obtener_Generos(?)}");
            stmt_generos.setString("id_contenido", rs_catalogo.getString("id_contenido"));
            rs_generos = stmt_generos.executeQuery();
            List<GeneroBean> listaGeneros = new LinkedList<>();
            while (rs_generos.next()) {
                GeneroBean genero = new GeneroBean();
                genero.setDescripcion(rs_generos.getString("descripcion"));
                genero.setId_genero(rs_generos.getInt("id_genero"));
                listaGeneros.add(genero);
            }
            rs_generos.close();
            stmt_generos.close();

            // CONTENIDO
            contenido.setId_contenido(rs_catalogo.getString("id_contenido"));
            contenido.setTitulo(rs_catalogo.getString("titulo"));
            contenido.setDescripcion(rs_catalogo.getString("descripcion"));
            contenido.setUrl_imagen(rs_catalogo.getString("url_imagen"));
            contenido.setClasificacion(rs_catalogo.getString("clasificacion"));
            contenido.setReciente(rs_catalogo.getBoolean("reciente"));
            contenido.setDestacado(rs_catalogo.getBoolean("destacado"));
            contenido.setValido(rs_catalogo.getBoolean("valido"));
            contenido.setActores(listaActores);
            contenido.setDirectores(listaDirectores);
            contenido.setGeneros(listaGeneros);

            listaContenido.add(contenido);
            catalogo = new CatalogoBean(listaContenido);
            System.out.println(catalogo);
        }
        return catalogo.toString();
    }

    @WebMethod()
    @WebResult(name = "obtenerEstadisticas")
    public String obtenerEstadisticas(@WebParam(name = "token_de_partner") String token_de_partner,
                                      @WebParam(name = "total") String total,
                                      @WebParam(name = "fecha") String fecha,
                                      @WebParam(name = "descripcion") String descripcion) throws SQLException, ClassNotFoundException {

        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
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
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            try {
                stmt = conn.prepareCall("{CALL dbo.Registrar_Reporte(?, ?, ?)}");
                stmt.setFloat("total", Float.parseFloat(total));
                stmt.setDate("fecha", fecha_formateada);
                stmt.setString("descripcion", descripcion);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
    @WebResult(name = "login")
    public String login(@WebParam(name = "email") String email, @WebParam(name = "contrasena") String contrasena) throws
            ClassNotFoundException, SQLException {

        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        CallableStatement stmt;
        ResultSet rs_login;
        System.out.println(email);
        System.out.println(contrasena);
        stmt = conn.prepareCall("{CALL dbo.Login_Usuario(?,?)}");
        stmt.setString("email", email);
        stmt.setString("contrasena", contrasena);
        rs_login = stmt.executeQuery();
        System.out.println(rs_login);
        UsuarioBean usuario = new UsuarioBean();
        int existe = 0;
        while (rs_login.next()) {
            existe = rs_login.getInt("ExisteUsuario");
        }
        CallableStatement stmt_user;
        ResultSet rs_user;
        if (existe == 1) {
            stmt_user = conn.prepareCall("{CALL dbo.Informacion_Usuario(?,?)}");
            stmt_user.setString("email", email);
            stmt_user.setString("contrasena", contrasena);
            rs_user = stmt_user.executeQuery();
            while (rs_user.next()) {
                usuario.setEmail(rs_user.getString("email"));
                usuario.setId_cliente(rs_user.getInt("id_cliente"));
                usuario.setNombre(rs_user.getString("nombre"));
                usuario.setApellido(rs_user.getString("apellido"));
                usuario.setMensaje("Usuario existente");
            }
        }

        System.out.println(usuario.toString());
        return usuario.toString();
    }

    @WebMethod()
    @WebResult(name = "crearTransaccion")
    public String crearTransaccion(@WebParam(name = "tipo_de_transaccion") String
                                           tipo_de_transaccion, @WebParam(name = "url_redireccion_ss") String
                                           url_redireccion_ss, @WebParam(name = "token_de_partner") String token_de_partner) throws
            ClassNotFoundException, SQLException {
        if (utils.verificarTokenDePartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass)) {
            Connection conn;
            ResultSet rs;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);

            UUID codigo_de_transaccion = UUID.randomUUID();
            String codigo_de_transaccion_string = codigo_de_transaccion.toString();

            String url_de_redireccion;
            if (tipo_de_transaccion.equals("L"))
                url_de_redireccion = "http://localhost:4203/login";
            else
                url_de_redireccion = "http://localhost:4203/register";

            CallableStatement stmt;

            // Crear transacción
            stmt = conn.prepareCall("{CALL dbo.Crear_Transaccion(?,?,?)}");
            stmt.setString("codigo_de_transaccion", codigo_de_transaccion_string);
            stmt.setString("url_de_redireccion", url_redireccion_ss);
            stmt.setString("tipo_de_transaccion", tipo_de_transaccion);
            stmt.executeUpdate();

            // Crear y devolver respuesta
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
        ResultSet rs_verificar_autorizacion;

        stmt = conn.prepareCall("{CALL dbo.Verificar_Autorizacion(?)}");
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        rs_verificar_autorizacion = stmt.executeQuery();

        TransaccionBean transaccionBean = new TransaccionBean();
        int existe = 0;
        while (rs_verificar_autorizacion.next()) {
            transaccionBean.setToken_de_servicio(rs_verificar_autorizacion.getString("token_de_servicio"));
            transaccionBean.setId_cliente(rs_verificar_autorizacion.getInt("id_cliente"));
            transaccionBean.setTipo_de_transaccion(rs_verificar_autorizacion.getString("codigo_de_transaccion"));
            transaccionBean.setUrl_de_redireccion(rs_verificar_autorizacion.getString("url_de_redireccion"));

        }

        return transaccionBean.toString();
    }

    @WebMethod()
    @WebResult(name = "crearAutorizacion")
    public void crearAutorizacion(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion,
                                  @WebParam(name = "id_cliente") int id_cliente) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        UUID token = UUID.randomUUID();
        String token_string = token.toString();

        CallableStatement stmt;
        ResultSet rs_crear_autorizacion;

        stmt = conn.prepareCall("{CALL dbo.Crear_Autorizacion(?,?)}");
        stmt.setInt("id_cliente", id_cliente);
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        stmt.setString("token", token_string);
        rs_crear_autorizacion = stmt.executeQuery();
        AutorizacionBean autorizacion = new AutorizacionBean();
        while (rs_crear_autorizacion.next()) {
            autorizacion.setId_cliente(rs_crear_autorizacion.getInt("id_cliente"));
            autorizacion.setToken_de_servicio(rs_crear_autorizacion.getString("token_de_servicio"));
            autorizacion.setCodigo_de_transaccion(rs_crear_autorizacion.getString("codigo_de_transaccion"));
            autorizacion.setUrl_de_redireccion(rs_crear_autorizacion.getString("url_de_redireccion"));
        }
        System.out.println(autorizacion);
    }

    @WebMethod()
    @WebResult(name = "obtenerUrlDeRedireccion")
    public String obtenerUrlDeRedireccion(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion) throws
            ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        UUID token = UUID.randomUUID();
        String token_string = token.toString();

        CallableStatement stmt;
        ResultSet rs_codio_transaccion;

        stmt = conn.prepareCall("{CALL dbo.Obtener_codigo_de_redireccion(?)}");
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        rs_codio_transaccion = stmt.executeQuery();
        String url_de_redireccion = "";
        while (rs_codio_transaccion.next()) {
            url_de_redireccion = rs_codio_transaccion.getString("url_de_redireccion");
        }
        System.out.println(url_de_redireccion);
        return url_de_redireccion;
    }

    @WebMethod()
    @WebResult(name = "obtenerToken")
    public String obtenerToken(@WebParam(name = "codigo_de_transaccion") String codigo_de_transaccion) throws
            ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        UUID token = UUID.randomUUID();
        String token_string = token.toString();

        CallableStatement stmt;
        ResultSet rs_token;

        stmt = conn.prepareCall("{CALL dbo.Obtener_codigo_de_redireccion(?)}");
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        rs_token = stmt.executeQuery();
        String token_plataforma = "";
        while (rs_token.next()) {
            token_plataforma = rs_token.getString("url_de_redireccion");
        }
        System.out.println(token_plataforma);
        return token_plataforma;
    }

    @WebMethod()
    @WebResult(name = "obtenerSesion")
    public String obtenerSesion(@WebParam (name = "token_de_servicio") String token_de_servicio,
                                @WebParam (name = "token_de_usuario") String token_de_usuario) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        Map<String, String> respuesta = new HashMap<>();

        try {
            Map<String, Integer> ids = utils.obtenerIds(token_de_servicio, token_de_usuario, driver_sql, sql_conection_string, sql_user, sql_pass);
            int id_partner = ids.get("id_partner");
            int id_cliente = ids.get("id_cliente");

            UUID sesion = UUID.randomUUID();
            String sesion_string = sesion.toString();
            utils.crearSesion(id_partner, id_cliente, sesion_string, driver_sql, sql_conection_string, sql_user, sql_pass);

            return """
                    {
                    \tsesion: %s,
                    \tcodigoRespuesta: %s,
                    \tmensajeRespuesta: %s
                    }
                    """.formatted(sesion_string, "200", "La sesion fue creada");

        } catch (Exception e) {return """
                    {
                    \tsesion: %s,
                    \tcodigoRespuesta: %s,
                    \tmensajeRespuesta: %s
                    }
                    """.formatted(null, "1", "La sesion no pudo ser creada");
        }
    }

    @WebMethod()
    @WebResult(name = "obtenerUrlDeContenido")
    public String obtenerUrlDeContenido(@WebParam(name = "token_de_partner") String token_de_partner,
                                        @WebParam(name = "token_de_sesion") String token_de_sesion,
                                        @WebParam(name = "id_contenido") String id_contenido) throws SQLException, ClassNotFoundException {
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
                        "url_de_contenido": "%s"
                    }""".formatted(url_de_contenido);

        } else {
            return partner_no_verificado;
        }
    }
}
