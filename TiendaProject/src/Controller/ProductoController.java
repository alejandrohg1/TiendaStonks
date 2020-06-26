/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.UsuarioController.usuarioObservableList;
import Pojo.Producto;
import Pojo.Usuario;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

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
    
    public static ObservableList<Producto> productoObservableList;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }
    
    public void loadData(){
        loadFromGson();
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
    
    
    
    public  void loadFromGson() {
        Gson gson = new Gson();
        productoObservableList = FXCollections.observableArrayList();

        try {
            productoObservableList.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/productos.json"), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public  void addToGson(ObservableList<Usuario> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/resources/Data/productos.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

            ObservableList<Usuario> jsonArray = newData;
            gson.toJson(jsonArray, flw);

        try {
            flw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent event) {
       ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
