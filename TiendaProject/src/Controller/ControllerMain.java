package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Pane pnlTemp;
    @FXML
    private ImageView imageStonks;
    @FXML
    private Button buttonUser;
    @FXML
    private Button buttonProveedores;
    @FXML
    private AnchorPane paneButton;
    @FXML
    private Button buttonFacturas;
    public static String rol;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //cerrar ventana
    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    //cambiar escena a usuario
    public void ToUserScene(ActionEvent event) throws IOException {

        Node child = FXMLLoader.load(getClass().getResource("/Views/UsuarioScene.fxml"));
        pnlTemp.getChildren().clear();
        pnlTemp.getChildren().add(child);

    }

    public void setRol(String roles){
        rol=roles;
    }

    public Button getButtonUser() {
        return buttonUser;
    }

    public Button getButtonProveedores() {
        return buttonProveedores;
    }

    public AnchorPane getPaneButton() {
        return paneButton;
    }

    public Button getButtonFacturas() {
        return buttonFacturas;
    }

    public void toVentasScene(ActionEvent event) throws IOException {
        Node child = FXMLLoader.load(getClass().getResource("/Views/VentasScene.fxml"));
        pnlTemp.getChildren().clear();
        pnlTemp.getChildren().add(child);
    }
}
