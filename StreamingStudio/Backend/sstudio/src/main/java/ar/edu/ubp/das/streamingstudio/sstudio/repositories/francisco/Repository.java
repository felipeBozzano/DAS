

package ar.edu.ubp.das.streamingstudio.sstudio.repositories.francisco;

import ar.edu.ubp.das.streamingstudio.sstudio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@org.springframework.stereotype.Repository
public class Repository {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTpl;


    @Transactional
    public List<Tipo_de_Fee> getTipoFee(int tipo_de_fee) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_tipo_de_fee", tipo_de_fee);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Tipo_Fee")
                .withSchemaName("dbo")
                .returningResultSet("tipos_de_fee", BeanPropertyRowMapper.newInstance(Tipo_de_Fee.class));

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<Tipo_de_Fee>)out.get("tipos_de_fee");
    }

    @Transactional
    public List<ClienteUsuarioBean> createUser(ClienteUsuarioBean cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("usuario", cliente.getUsuario())
                .addValue("contrasena", cliente.getContrasena())
                .addValue("email", cliente.getEmail())
                .addValue("nombre", cliente.getNombre())
                .addValue("apellido", cliente.getApellido())
                .addValue("valido", true);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Usuario")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        return (List<ClienteUsuarioBean>)out.get("Crear_Usuario");
    }

    @Transactional
    public List<ClienteUsuarioBean> getUser(String email) {
        return jdbcTpl.query("SELECT * FROM dbo.Cliente_Usuario WHERE email = ?",new Object[]{email}, BeanPropertyRowMapper.newInstance(ClienteUsuarioBean.class)
        );
    }

    @Transactional
    public int deleteUser(String email) {
        return jdbcTpl.update("DELETE FROM dbo.Cliente_Usuario WHERE email = ?", email);
    }

    /* FEDERACION */

    @Transactional
    public int federarClientePlataforma(int id_plataforma, int id_cliente) {
        int federacion = buscarFederacion(id_plataforma, id_cliente);
        if(federacion == 1){
            return 1;
        } else {
            federacion = VerificarFederacionCurso(id_plataforma, id_cliente);
            if(federacion == 1) {
                return 1;
            }
            try {
                // Specify the URL to send the GET request to
                URL url = new URL("http://localhost:8081/netflix/obtener_token");

                // Open a connection to the specified URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                // Read the response from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response
                System.out.println("Response:");
                System.out.println(response.toString());
                connection.disconnect();
                return responseCode;
                // Close the connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return federacion;
    }

    @Transactional
    public int buscarFederacion(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Buscar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer federacion = resulset.get(0).get("federacion");
        return federacion;

    }

    @Transactional
    public int VerificarFederacionCurso(int id_plataforma, int id_cliente) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma)
                .addValue("id_cliente", id_cliente);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Verificar_Federacion_en_Curso")
                .withSchemaName("dbo");

        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Integer>> resulset = (List<Map<String, Integer>>) out.get("#result-set-1");
        Integer federacion = resulset.get(0).get("existe_federacion");
        return federacion;
    }

    /* FACTURACION */

    @Transactional
    public List<PublicidadBean> buscarDatoPublicidades() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicidades")
                .withSchemaName("dbo")
                .returningResultSet("publicidad", BeanPropertyRowMapper.newInstance(PublicidadBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PublicidadBean>)out.get("publicidad");
    }

    @Transactional
    public float obtenerCostoDeBanner(int id_banner) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_banner", id_banner);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Costo_de_Banner")
                .withSchemaName("dbo")
                .returningResultSet("costo_banner", BeanPropertyRowMapper.newInstance(BannerBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        List<HashMap<String, Integer>> resulset = (List<HashMap<String, Integer>>) out.get("costo_banner");
        if (resulset != null && !resulset.isEmpty()) {
            HashMap<String, Integer> firstRow = resulset.get(0); // Get the first HashMap in the list
            Object value = firstRow.get("your_key"); // Replace "your_key" with the actual key
            if (value != null && value instanceof Integer) {
                return (Integer) value; // Assuming the value is an integer
            }
        }
        return -1;
    }

    public void crearFacturaPublicista(int id_publicista) {
    SqlParameterSource in = new MapSqlParameterSource()
            .addValue("id_publicista", id_publicista);
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
            .withProcedureName("Crear_Factura_Publicista")
            .withSchemaName("dbo");
    }

    public void crearDetalleFacturaPublicista(int id_factura, int id_detalle, float precio_unitario, int cantidad, float subtotal, String description) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura)
                .addValue("id_detalle", id_detalle)
                .addValue("precio_unitario", precio_unitario)
                .addValue("cantidad", cantidad)
                .addValue("subtotal", subtotal)
                .addValue("description", description);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Detalle_Factura")
                .withSchemaName("dbo");
    }

    public void finalizarFactura(int id_factura, int total) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura)
                .addValue("total", total);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Factura")
                .withSchemaName("dbo");
    }

    public void enviarFactura(int id_factura) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Enviar_Factura")
                .withSchemaName("dbo");
    }



}