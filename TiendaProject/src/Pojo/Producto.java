package Pojo;

import java.sql.Struct;

public class Producto {

    private String descripcion;
    private String Id;
    private String stock;
    private String fotoUrl;
    private float precio;
    private String seccion;
    private String idProveedor;
    private Proveedor proveedor;

    public Producto() {
    }

    public Producto(String descripcion, String id, String stock, String fotoUrl, float precio, String seccion, String idProveedor, Proveedor proveedor) {
        this.descripcion = descripcion;
        Id = id;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
}
