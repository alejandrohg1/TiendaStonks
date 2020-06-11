package Pojo;

import javafx.beans.property.SimpleStringProperty;

public class Usuario {
    private int id;
    private SimpleStringProperty nombre;
    private SimpleStringProperty apellido;
    private SimpleStringProperty cedula;
    private SimpleStringProperty fotoUrl;
    private SimpleStringProperty rol;
    private SimpleStringProperty email;
    private SimpleStringProperty username;
    private SimpleStringProperty password;

    public Usuario(int id, SimpleStringProperty nombre, SimpleStringProperty apellido, SimpleStringProperty cedula, SimpleStringProperty fotoUrl, SimpleStringProperty rol, SimpleStringProperty email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.fotoUrl = fotoUrl;
        this.rol = rol;
        this.email = email;
    }


    public Usuario(int id, String nombre, String apellido, String cedula, String fotoUrl, String rol, String email, String username, String password) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido =  new SimpleStringProperty(apellido);
        this.cedula = new SimpleStringProperty(cedula);
        this.fotoUrl = new SimpleStringProperty(fotoUrl);
        this.rol = new SimpleStringProperty(rol);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public SimpleStringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public SimpleStringProperty cedulaProperty() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getFotoUrl() {
        return fotoUrl.get();
    }

    public SimpleStringProperty fotoUrlProperty() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl.set(fotoUrl);
    }

    public String getRol() {
        return rol.get();
    }

    public SimpleStringProperty rolProperty() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol.set(rol);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", apellido=" + apellido +
                ", cedula=" + cedula +
                ", fotoUrl=" + fotoUrl +
                ", rol=" + rol +
                ", email=" + email +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
