package Pojo;

import java.util.ArrayList;
import java.util.List;

public class Factura {

    private String observaciones;
    private String codigoFactura;
    private String fechaDeFacturacion;
    private float cambio;
    private float total;
    private float neto;
    private float saldo;
    private String descuento;
    private String monedaCambio;
    private List<String> productosFactura;

    public Factura() {
        productosFactura = new ArrayList<>();
    }

    public Factura(String observaciones, String codigoFactura, String fechaDeFacturacion, float cambio, float total, float neto, float saldo, String descuento, String monedaCambio, List<String> productosFactura) {
        this.observaciones = observaciones;
        this.codigoFactura = codigoFactura;
        this.fechaDeFacturacion = fechaDeFacturacion;
        this.cambio = cambio;
        this.total = total;
        this.neto = neto;
        this.saldo = saldo;
        this.descuento = descuento;
        this.monedaCambio = monedaCambio;
        this.productosFactura = productosFactura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(String codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public String getFechaDeFacturacion() {
        return fechaDeFacturacion;
    }

    public void setFechaDeFacturacion(String fechaDeFacturacion) {
        this.fechaDeFacturacion = fechaDeFacturacion;
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getNeto() {
        return neto;
    }

    public void setNeto(float neto) {
        this.neto = neto;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getMonedaCambio() {
        return monedaCambio;
    }

    public void setMonedaCambio(String monedaCambio) {
        this.monedaCambio = monedaCambio;
    }

    public List<String> getProductosFactura() {
        return productosFactura;
    }

    public void setProductosFactura(List<String> productosFactura) {
        this.productosFactura = productosFactura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "observaciones='" + observaciones + '\'' +
                ", codigoFactura='" + codigoFactura + '\'' +
                ", fechaDeFacturacion='" + fechaDeFacturacion + '\'' +
                ", cambio=" + cambio +
                ", total=" + total +
                ", neto=" + neto +
                ", saldo=" + saldo +
                ", descuento='" + descuento + '\'' +
                ", monedaCambio='" + monedaCambio + '\'' +
                ", productosFactura=" + productosFactura +
                '}';
    }
}
