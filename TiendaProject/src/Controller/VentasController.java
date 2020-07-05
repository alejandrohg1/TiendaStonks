package Controller;

import DataBase.ProductoData;
import Pojo.Producto;
import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class VentasController implements Initializable {
    @FXML
    private TextField txtProducts;
    @FXML
    private TableView<Producto> tblProducto;
    @FXML
    private AnchorPane anchorPaneMain;
    private ObservableList<Producto> productos;
    public ProductoData productoData = new ProductoData();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productoData.loadFromGson();

    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    public void dialogProducts(ActionEvent event) throws IOException {

    }
}
