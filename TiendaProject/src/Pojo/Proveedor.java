package Pojo;

public class Proveedor {

    private String nombreProv;
    private String apellidoProv;
    private String cedula;
    private String correo;
    private String telefono;
    private String ruc;
    private String idProveedor;

    public Proveedor() {
    }



    public Proveedor(String nombreProv, String apellidoProv, String cedula, String correo, String telefono, String ruc, String idProveedor) {
        this.nombreProv = nombreProv;
        this.apellidoProv = apellidoProv;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.ruc = ruc;
        this.idProveedor = idProveedor;
    }

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public String getApellidoProv() {
        return apellidoProv;
    }

    public void setApellidoProv(String apellidoProv) {
        this.apellidoProv = apellidoProv;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }
}
