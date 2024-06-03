package ar.edu.ubp.das.streamingstudio.utils;

import ar.edu.ubp.das.streamingstudio.beans.ActorBean;
import ar.edu.ubp.das.streamingstudio.beans.DirectorBean;
import ar.edu.ubp.das.streamingstudio.beans.GeneroBean;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {
    public Boolean verificarTokenDePartner(String token_de_partner,
                                           String driver_sql,
                                           String sql_conection_string,
                                           String sql_user,
                                           String sql_pass) throws ClassNotFoundException, SQLException {
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

    public Boolean usarSesion(String token_de_sesion,
                              String driver_sql,
                              String sql_conection_string,
                              String sql_user,
                              String sql_pass) {
        try {
            Connection conn;
            Class.forName(driver_sql);
            conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
            conn.setAutoCommit(true);
            CallableStatement stmt;

            stmt = conn.prepareCall("{CALL dbo.Usar_Sesion(?)}");
            stmt.setString("sesion", token_de_sesion);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Integer> obtenerIds(String token_de_partner,
                                           String token_de_usuario,
                                           String driver_sql,
                                           String sql_conection_string,
                                           String sql_user,
                                           String sql_pass) throws ClassNotFoundException, SQLException {
        int id_partner = obtenerIdPartner(token_de_partner, driver_sql, sql_conection_string, sql_user, sql_pass);
        int id_cliente = obtenerIdCliente(token_de_usuario, driver_sql, sql_conection_string, sql_user, sql_pass);
        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("id_partner", id_partner);
        respuesta.put("id_cliente", id_cliente);
        return respuesta;
    }

    private int obtenerIdPartner(String token_de_partner,
                                 String driver_sql,
                                 String sql_conection_string,
                                 String sql_user,
                                 String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;
        ResultSet rs;

        stmt = conn.prepareCall("{CALL dbo.Obtener_ID_de_Partner(?)}");
        stmt.setString("token", token_de_partner);
        rs = stmt.executeQuery();
        int id_partner = 0;
        while (rs.next()) {
            id_partner = rs.getInt("id_partner");
        }
        return id_partner;
    }

    private int obtenerIdCliente(String token_de_usuario,
                                 String driver_sql,
                                 String sql_conection_string,
                                 String sql_user,
                                 String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;
        ResultSet rs;

        stmt = conn.prepareCall("{CALL dbo.Obtener_Cliente(?)}");
        stmt.setString("token", token_de_usuario);
        rs = stmt.executeQuery();
        int id_cliente = 0;
        while (rs.next()) {
            id_cliente = rs.getInt("id_cliente");
        }
        return id_cliente;
    }

    public void crearSesion(int id_partner,
                            int id_cliente,
                            String sesion,
                            String driver_sql,
                            String sql_conection_string,
                            String sql_user,
                            String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Crear_Sesion(?, ?, ?)}");
        stmt.setInt("id_cliente", id_cliente);
        stmt.setInt("id_partner", id_partner);
        stmt.setString("sesion", sesion);
        stmt.executeUpdate();
    }

    public String obtenerUrlDeRedireccion(String codigo_de_transaccion,
                                          String driver_sql,
                                          String sql_conection_string,
                                          String sql_user,
                                          String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Obtener_codigo_de_redireccion(?)}");
        stmt.setString("codigo_de_transaccion", codigo_de_transaccion);
        rs = stmt.executeQuery();
        String url_de_redireccion = "";
        while (rs.next()) {
            url_de_redireccion = rs.getString("url_de_redireccion");
        }
        return url_de_redireccion;
    }

    public List<DirectorBean> obtenerDirectores(String id_contenido,
                                                String driver_sql,
                                                String sql_conection_string,
                                                String sql_user,
                                                String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Obtener_Directores(?)}");
        stmt.setString("id_contenido", id_contenido);
        rs = stmt.executeQuery();
        List<DirectorBean> listaDirectores = new LinkedList<>();

        while (rs.next()) {
            DirectorBean director = new DirectorBean();
            director.setApellido(rs.getString("apellido"));
            director.setNombre(rs.getString("nombre"));
            director.setId_director(rs.getInt("id_director"));
            listaDirectores.add(director);
        }

        return listaDirectores;
    }

    public List<ActorBean> obtenerActores(String id_contenido,
                                          String driver_sql,
                                          String sql_conection_string,
                                          String sql_user,
                                          String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Obtener_Actores(?)}");
        stmt.setString("id_contenido", id_contenido);
        rs = stmt.executeQuery();
        List<ActorBean> listaActores = new LinkedList<>();

        while (rs.next()) {
            ActorBean actor = new ActorBean();
            actor.setApellido(rs.getString("apellido"));
            actor.setNombre(rs.getString("nombre"));
            actor.setId_director(rs.getInt("id_actor"));
            listaActores.add(actor);
        }

        return listaActores;
    }

    public List<GeneroBean> obtenerGeneros(String id_contenido,
                                           String driver_sql,
                                           String sql_conection_string,
                                           String sql_user,
                                           String sql_pass) throws ClassNotFoundException, SQLException {
        Connection conn;
        ResultSet rs;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);
        CallableStatement stmt;

        stmt = conn.prepareCall("{CALL dbo.Obtener_Generos(?)}");
        stmt.setString("id_contenido", id_contenido);
        rs = stmt.executeQuery();
        List<GeneroBean> listaGenero = new LinkedList<>();

        while (rs.next()) {
            GeneroBean genero = new GeneroBean();
            genero.setDescripcion(rs.getString("descripcion"));
            genero.setId_genero(rs.getInt("id_genero"));
            listaGenero.add(genero);
        }

        return listaGenero;
    }

}
