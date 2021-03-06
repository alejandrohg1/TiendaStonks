/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DataBase.ProductoData;
import Pojo.Producto;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Yiin Anton
 */
public class ProductoController implements Initializable {

    @FXML
    private FlowPane flowPaneProducts;
    @FXML
    private ScrollPane scrollPaneContent;
    @FXML
    private Button btnBuscar;

    public static ObservableList<Producto> productoObservableList;
    private ProductoData productoData = new ProductoData();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData() throws FileNotFoundException{
        productoData.loadFromGson();
        productoObservableList = FXCollections.observableArrayList(productoData.getProductos());
        flowPaneProducts.getChildren().clear();
        for(Producto p : productoObservableList){
            try
            {
                FXMLLoader loader = new FXMLLoader();
                Node child = loader.load(getClass().getResource("/Views/ProductoTemplate.fxml").openStream());
                if (child == null) {
                    return;
                }
                ProductoTemplateController controller = loader.getController();
                controller.setInfoProducto(p);
                flowPaneProducts.getChildren().add(child);
            } catch (IOException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     @FXML
    private void btnNuevoProducto_Action(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/InfoProducto.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/iconTienda.png")));
        stage.setTitle("Registrar Producto");
        stage.show();
    }

    @FXML
    void buscarProd(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ProductoTable.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/iconTienda.png")));
        stage.setTitle("Buscar Producto");
        stage.show();

    }
    
    @FXML
    public void closeWindow(ActionEvent event) {
       ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
