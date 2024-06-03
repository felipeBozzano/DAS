package ar.edu.ubp.das.streamingstudio.utils;

import jakarta.jws.WebParam;

import java.sql.*;
import java.util.HashMap;
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
            stmt.setString("token_de_sesion", token_de_sesion);
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
        Connection conn;
        Class.forName(driver_sql);
        conn = DriverManager.getConnection(sql_conection_string, sql_user, sql_pass);
        conn.setAutoCommit(true);

        CallableStatement stmt;
        ResultSet rs_partner;

        stmt = conn.prepareCall("{CALL dbo.Obtener_ID_de_Partner(?)}");
        stmt.setString("token", token_de_partner);
        rs_partner = stmt.executeQuery();
        int id_partner = 0;
        while (rs_partner.next()) {
            id_partner = rs_partner.getInt("id_partner");
        }

        ResultSet rs_usuario;
        stmt = conn.prepareCall("{CALL dbo.Obtener_Cliente(?)}");
        stmt.setString("token", token_de_usuario);
        rs_usuario = stmt.executeQuery();
        int id_cliente = 0;
        while (rs_usuario.next()) {
            id_cliente = rs_usuario.getInt("id_cliente");
        }

        Map<String, Integer> respuesta = new HashMap<>();
        respuesta.put("id_partner", id_partner);
        respuesta.put("id_cliente", id_cliente);
        return respuesta;
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

        stmt = conn.prepareCall("{CALL dbo.Crear_Sesion(?)}");
        stmt.setInt("id_cliente", id_cliente);
        stmt.setInt("id_partner", id_partner);
        stmt.setString("sesion", sesion);
        stmt.executeUpdate();
    }

}
