package Controller;

import animatefx.animation.SlideInDown;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closePane(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    public void loginAction(ActionEvent event) throws IOException {

        String name = txtUsername.getText();
        String password = txtPassword.getText();

        if(name.isEmpty() && password.isEmpty()){
            errorName.setStyle("-fx-text-fill: red");
            errorName.setText("Field id empty");
            errorLabel.setStyle("-fx-text-fill: red");
            errorLabel.setText("Field is empty");
        }

        if(!name.equals("admin") || name.isEmpty()){
            if(name.isEmpty()){
                errorName.setStyle("-fx-text-fill: red");
                errorName.setText("Field id empty");
            }else{
                errorName.setStyle("-fx-text-fill: red");
                errorName.setText("Username is incorrect");
            }
        }else if (!password.equals("123") || password.isEmpty()){
            if(password.isEmpty()){
                errorLabel.setStyle("-fx-text-fill: red");
                errorLabel.setText("Field is empty");
            }else{
                errorLabel.setStyle("-fx-text-fill: red");
                errorLabel.setText("Password is incorrect");
            }

        }else{
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            FXMLLoader  fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainMenu.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("resources/images/iconTienda.png"));
            stage.show();

            new SlideInDown(root).play();
        }

    }

    public void clearText(MouseEvent event) { errorName.setText(""); }
    public void clearText2(MouseEvent event) { errorLabel.setText(""); }

    public void setTime(MouseEvent event) {

    }

}
