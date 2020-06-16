package Controller;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMain implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Pane pnlTemp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    public void ToUserScene(ActionEvent event) throws IOException {

            Node child = FXMLLoader.load(getClass().getResource("/Views/UsuarioScene.fxml"));
            pnlTemp.getChildren().clear();
            pnlTemp.getChildren().add(child);

    }
    
    public void ToProductoScene(ActionEvent event) throws IOException {

        Node child = FXMLLoader.load(getClass().getResource("/Views/ProductoScene.fxml"));
        pnlTemp.getChildren().clear();
        pnlTemp.getChildren().add(child);

    }
}
