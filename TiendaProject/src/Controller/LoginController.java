package Controller;

import Pojo.Usuario;
import animatefx.animation.SlideInDown;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;



public class LoginController implements Initializable {
    @FXML
    private Label errorName;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private AnchorPane anchorPane;
    private ObservableList<Usuario> usuarioObservableList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFromGson();
    }

    //cerrar stage
    public void closePane(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    public void loginAction(ActionEvent event) throws IOException {
        String name = txtUsername.getText();
        String password = txtPassword.getText();
        String photo = null;
        String fullname = null;
        String rol = null;
        boolean flagIntro = false;
        int countUser = 0, countPassword = 0, countBoth = 0;
        //validaciones del login
        for (Usuario u : usuarioObservableList) {
            if (u.getUsername().equals(name) && u.getPassword().equals(password)) {
                flagIntro = true;
                photo = u.getFotoUrl();
                fullname = u.getNombre();
                rol = u.getRol();
            }
            if (!u.getUsername().equals(name)) {
                countUser++;
            }
            if (!u.getPassword().equals(password)) {
                countPassword++;
            }

        }

        if (name.isEmpty() && password.isEmpty()) {
            errorName.setStyle("-fx-text-fill: red");
            errorName.setText("Field id empty");
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Field is empty");
        } else if (name.isEmpty()) {
            errorName.setStyle("-fx-text-fill: red");
            errorName.setText("Field id empty");
        } else if (password.isEmpty()) {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Field is empty");
        } else if (countUser == usuarioObservableList.size() && countPassword == usuarioObservableList.size()) {
            errorName.setStyle("-fx-text-fill: red");
            errorName.setText("Username is incorrect");
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Password is incorrect");
        } else if (countUser == usuarioObservableList.size()) {
            errorName.setStyle("-fx-text-fill: red");
            errorName.setText("Username is incorrect");
        } else if (countPassword == usuarioObservableList.size()) {
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Password is incorrect");
        }
        //abre stage del main
        if (flagIntro) {
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainMenu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("resources/images/iconTienda.png"));
            stage.show();
            new SlideInDown(root).play();
        }
        //pasa el nombre del usuario al main
        UsuarioController controller = new UsuarioController();
        controller.setLblWelcome(fullname,photo,rol);



    }
    //limpia campos de textos
    public void clearText(MouseEvent event) { errorName.setText(""); }
    public void clearText2(MouseEvent event) { errorLabel.setText(""); }

    public void setTime(MouseEvent event) {

    }
    //carga del gson
    public  void loadFromGson() {
        Gson gson = new Gson();
        usuarioObservableList = FXCollections.observableArrayList();

        try {
            usuarioObservableList.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/usuarios.json"), Usuario[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



}
