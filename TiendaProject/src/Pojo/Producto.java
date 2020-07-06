package Pojo;

import java.util.List;

public class Producto {

    private String descripcion;
    private String idProducto;
    private String stock;
    private String fotoUrl;
    private float precio;
    private String seccion;
    private String idProveedor;
    private Proveedor proveedor;

    public Producto() {
    }

    public Producto(String descripcion, String idProducto, String stock, String fotoUrl, float precio, String seccion, String idProveedor, Proveedor proveedor) {
        this.descripcion = descripcion;
        this.idProducto = idProducto;
        this.stock = stock;
        this.fotoUrl = fotoUrl;
        this.precio = precio;
        this.seccion = seccion;
        this.idProveedor = idProveedor;
        this.proveedor = proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdprducto() {
        return idProducto;
    }

    public void setIdprducto(String idprducto) {
        this.idProducto = idprducto;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "descripcion='" + descripcion + '\'' +
                ", idProducto='" + idProducto + '\'' +
                ", stock='" + stock + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", precio=" + precio +
                ", seccion='" + seccion + '\'' +
                ", idProveedor='" + idProveedor + '\'' +
                ", proveedor=" + proveedor +
                '}';
    }
}
