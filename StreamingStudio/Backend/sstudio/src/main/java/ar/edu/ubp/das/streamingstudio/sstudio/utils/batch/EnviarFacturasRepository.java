

package ar.edu.ubp.das.streamingstudio.sstudio.utils.batch;

import ar.edu.ubp.das.streamingstudio.sstudio.models.FederacionBean;
import ar.edu.ubp.das.streamingstudio.sstudio.models.Fee;
import ar.edu.ubp.das.streamingstudio.sstudio.models.PublicidadFacturasBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.*;

@Repository
public class EnviarFacturasRepository {

    @Autowired
    private JdbcTemplate jdbcTpl;

    /* FACTURACION */

    @Transactional
    public String enviarFacturasPublicistas() {
        List<PublicidadFacturasBean> datosPublicidades = buscarDatoPublicidades();

        // ** FACTURAS PUBLICISTAS ** //
        // agrupar publicidades por publicista
        Map<Integer, List<PublicidadFacturasBean>> publicidades_agrupadas = new HashMap<>();
        for (PublicidadFacturasBean publicidad : datosPublicidades) {
            if (!publicidades_agrupadas.containsKey(publicidad.getId_publicista())) {
                publicidades_agrupadas.put(publicidad.getId_publicista(), new ArrayList<>());
            }
            publicidades_agrupadas.get(publicidad.getId_publicista()).add(publicidad);
        }

        for (Integer id_publicista : publicidades_agrupadas.keySet()) {
            int id_factura = crearFacturaPublicista(id_publicista);
            List<PublicidadFacturasBean> listaPublicadades = publicidades_agrupadas.get(id_publicista);
            double total = 0;
            for(PublicidadFacturasBean publicidad : listaPublicadades){
                double costoBanner = obtenerCostoDeBanner(publicidad.getId_tipo_banner());
                total += costoBanner * publicidad.getCantidad_de_dias();
                crearDetalleFacturaPublicista(id_factura, costoBanner, publicidad.getCantidad_de_dias(), costoBanner * publicidad.getCantidad_de_dias(), "Publicidad " + id_factura);
            }
            finalizarFactura(id_factura, total);
            enviarFactura(id_factura);
        }

        //System.out.println(publicidades_agrupadas.toString());
        return "ok";
    }

    @Transactional
    public double obtenerCostoDeBanner(int id_banner) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_banner", id_banner);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Costo_de_Banner")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
        List<Map<String, Double >> resulset = (List<Map<String, Double>>) out.get("#result-set-1");
        double costo_banner = resulset.getFirst().get("costo");
        return costo_banner;
    }

    @Transactional
    public List<PublicidadFacturasBean> buscarDatoPublicidades() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Publicidades")
                .withSchemaName("dbo")
                .returningResultSet("publicidad", BeanPropertyRowMapper.newInstance(PublicidadFacturasBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        return (List<PublicidadFacturasBean>)out.get("publicidad");
    }

    // FACTURAS PUBLICISTAS //

    @Transactional
    public int crearFacturaPublicista(int id_publicista) {
    SqlParameterSource in = new MapSqlParameterSource()
            .addValue("id_publicista", id_publicista);
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
            .withProcedureName("Crear_Factura_Publicista")
            .withSchemaName("dbo")
            .declareParameters(new SqlOutParameter("id_factura", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        int id_factura = (int) out.get("id_factura");
        return id_factura;
    }

    @Transactional
    public void crearDetalleFacturaPublicista(int id_factura, double precio_unitario, int cantidad, double subtotal, String description) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura)
                .addValue("precio_unitario", precio_unitario)
                .addValue("cantidad", cantidad)
                .addValue("subtotal", subtotal)
                .addValue("descripcion", description);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Detalle_Factura")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Transactional
    public void finalizarFactura(int id_factura, double total) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura)
                .addValue("total", total);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Finalizar_Factura")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Transactional
    public void enviarFactura(int id_factura) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Enviar_Factura")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    // FACTURAS PLATAFORMA STREAMING //

    @Transactional
    public String enviarFacturasPlataformas() {
        List<FederacionBean> federacionesPorPlataforma = buscarDatosFederaciones();

        // ** FACTURAS PLATAFORMA STREAMING ** //
        // agrupar publicidades por publicista
        Map<Integer, List<FederacionBean>> federaciones_agrupadas = new HashMap<>();
        for (FederacionBean federacionesPlatatorma : federacionesPorPlataforma) {
            if (!federaciones_agrupadas.containsKey(federacionesPlatatorma.getId_plataforma())) {
                federaciones_agrupadas.put(federacionesPlatatorma.getId_plataforma(), new ArrayList<>());
            }
            federaciones_agrupadas.get(federacionesPlatatorma.getId_plataforma()).add(federacionesPlatatorma);
        }

        for (Integer id_plataforma : federaciones_agrupadas.keySet()) {
            int id_factura = crearFacturaPlataforma(id_plataforma);
            List<FederacionBean> listaFederaciones = federaciones_agrupadas.get(id_plataforma);
            double total = 0;
            double feeLogin = 0;
            double feeRegistro = 0;
            List<Fee> feesPlataforma = obtenerFeesPlataforma(id_plataforma);
            for(Fee fee : feesPlataforma){
                if(fee.getTipo_de_fee() == 1){
                    feeLogin = - fee.getMonto();
                } else if (fee.getTipo_de_fee() == 2) {
                    feeRegistro = fee.getMonto();
                }
            }
            for(FederacionBean  federacion : listaFederaciones){
                double preco_unitario = 0;
                if(Objects.equals(federacion.getTipo_transaccion(), "1")){
                    total += feeLogin;
                    preco_unitario = feeLogin;
                }else{
                    total += feeRegistro;
                    preco_unitario = feeRegistro;
                }

                crearDetalleFacturaPlataforma(id_factura, preco_unitario, 1, preco_unitario * 1, "Factura Plataforma " + id_factura);
            }
            facturarFederacion(id_plataforma);
            finalizarFactura(id_factura, total);
            enviarFactura(id_factura);
        }

        //System.out.println(publicidades_agrupadas.toString());
        return "ok";
    }

    @Transactional
    public List<FederacionBean> buscarDatosFederaciones() {
        SqlParameterSource in = new MapSqlParameterSource();
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Datos_de_Federaciones")
                .withSchemaName("dbo")
                .returningResultSet("federaciones", BeanPropertyRowMapper.newInstance(FederacionBean.class));
        Map<String, Object> out = jdbcCall.execute(in);
        return (List<FederacionBean>)out.get("federaciones");
    }

    @Transactional
    public List<Fee> obtenerFeesPlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Obtener_Fees_de_Plataforma")
                .withSchemaName("dbo")
                .returningResultSet("fees_plataforma", BeanPropertyRowMapper.newInstance(Fee.class));
        Map<String, Object> out = jdbcCall.execute(in);
        return (List<Fee>)out.get("fees_plataforma");
    }

    @Transactional
    public int crearFacturaPlataforma(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Factura_Plataforma")
                .withSchemaName("dbo")
                .declareParameters(new SqlOutParameter("id_factura", Types.INTEGER));
        Map<String, Object> out = jdbcCall.execute(in);
        int id_factura = (int) out.get("id_factura");
        return id_factura;
    }

    @Transactional
    public void facturarFederacion(int id_plataforma) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_plataforma", id_plataforma);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Facturar_Federacion")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

    @Transactional
    public void crearDetalleFacturaPlataforma(int id_factura, double precio_unitario, int cantidad, double subtotal, String description) {
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("id_factura", id_factura)
                .addValue("precio_unitario", precio_unitario)
                .addValue("cantidad", cantidad)
                .addValue("subtotal", subtotal)
                .addValue("descripcion", description);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
                .withProcedureName("Crear_Detalle_Factura")
                .withSchemaName("dbo");
        Map<String, Object> out = jdbcCall.execute(in);
    }

//    @Transactional
//    public List<PublicidadBean> buscarDatoPublicidades() {
//        SqlParameterSource in = new MapSqlParameterSource();
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Obtener_Datos_de_Publicidades")
//                .withSchemaName("dbo")
//                .returningResultSet("publicidad", BeanPropertyRowMapper.newInstance(PublicidadBean.class));
//        Map<String, Object> out = jdbcCall.execute(in);
//        return (List<PublicidadBean>) out.get("publicidad");
//    }
//
//    @Transactional
//    public double obtenerCostoDeBanner(int id_banner) {
//        SqlParameterSource in = new MapSqlParameterSource()
//                .addValue("id_banner", id_banner);
//        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTpl)
//                .withProcedureName("Obtener_Costo_de_Banner")
//                .withSchemaName("dbo");
//        Map<String, Object> out = jdbcCall.execute(in);
//        List<Map<String, Double >> resulset = (List<Map<String, Double>>) out.get("#result-set-1");
//        double cotsto_banner = resulset.getFirst().get("costo");
//        return cotsto_banner;
//    }
}
