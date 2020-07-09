package Pojo;

public class FacturaTableModel {

    private String codigo;
    private int cantidad;
    private String descripcion;
    private float precioUni;
    private float precioTotal;

    public FacturaTableModel() {
    }

    public FacturaTableModel(String codigo, int cantidad, String descripcion, float precioUni, float precioTotal) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioUni = precioUni;
        this.precioTotal = precioTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioUni() {
        return precioUni;
    }

    public void setPrecioUni(float precioUni) {
        this.precioUni = precioUni;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "FacturaTableModel{" +
                "codigo='" + codigo + '\'' +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", precioUni=" + precioUni +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
