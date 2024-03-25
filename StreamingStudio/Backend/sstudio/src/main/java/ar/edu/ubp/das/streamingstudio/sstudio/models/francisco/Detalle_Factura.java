package ar.edu.ubp.das.streamingstudio.sstudio.models.francisco;

public class Detalle_Factura {
    private int id_factura;
    private int id_detalle;
    private float precio_unitario;
    private int cantidad;
    private float subtotal;
    private String descripcion;

    public Detalle_Factura(int id_factura, int id_detalle, float precio_unitario, int cantidad, float subtotal, String descripcion) {
        this.id_factura = id_factura;
        this.id_detalle = id_detalle;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.descripcion = descripcion;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
