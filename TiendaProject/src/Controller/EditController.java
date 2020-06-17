package Controller;

import Pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable {
    @FXML
    private ComboBox<String> txtRol;
    @FXML
    private TextField txtEmail,txtUsername,txtFoto,txtApellido,txtNombre,txtCedula;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private  Button buttonEditar;

    public static boolean flag;

    public Usuario user;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //inicializa el combo box
        flag = false;
        ObservableList<String> products = FXCollections.observableArrayList();
        products.addAll("Admin","Cajero","Gerente");
        txtRol.setItems(products);
    }

    //inizialar data en los textfields del editController
    public void initData(Usuario user){
        txtEmail.setText(user.getEmail());
        txtNombre.setText(user.getNombre());
        txtApellido.setText(user.getApellido());
        txtCedula.setText(user.getCedula());
        txtFoto.setText(user.getFotoUrl());
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
    }
    //editar usuario seleccionado
    public void setUser(Usuario usuario){
        usuario.setNombre(txtNombre.getText());
        usuario.setApellido(txtApellido.getText());
        usuario.setCedula(txtCedula.getText());
        usuario.setFotoUrl(txtFoto.getText());
        usuario.setRol(txtRol.getSelectionModel().getSelectedItem());
        usuario.setEmail(txtEmail.getText());
        usuario.setUsername(txtUsername.getText());
        usuario.setPassword(txtPassword.getText());
    }

    public Button getButtonEditar() {
        return buttonEditar;
    }

}

