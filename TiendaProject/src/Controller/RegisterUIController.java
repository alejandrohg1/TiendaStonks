package Controller;

import Pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterUIController implements Initializable {
    @FXML
    private ComboBox<String> txtRol;
    @FXML
    private TextField txtEmail,txtUsername,txtFoto,txtApellido,txtNombre,txtCedula;
    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> products = FXCollections.observableArrayList();
        products.addAll("Admin","Cajero","Gerente");
        txtRol.setItems(products);
    }


    public void getNewEmpleado(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        UsuarioController usuarioController = new UsuarioController();
        Usuario user = new Usuario(new Random().nextInt(9999),txtNombre.getText(),txtApellido.getText(),txtCedula.getText(),txtFoto.getText(),txtRol.getSelectionModel().getSelectedItem(),txtEmail.getText(),txtUsername.getText(),txtPassword.getText());
        if(txtFoto.getText().isEmpty()){
            user.setFotoUrl("https://icons-for-free.com/iconfiles/png/512/business+costume+male+man+office+user+icon-1320196264882354682.png");
        }

      usuarioController.saveUser(user);

    }

    public void editEmpleado(Usuario user){


    }


}
