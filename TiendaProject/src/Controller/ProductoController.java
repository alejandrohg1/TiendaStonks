/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flowPaneProducts.getChildren().clear();
        for(int i = 0; i < 10; i++){
            try
            {
                FXMLLoader loader = new FXMLLoader();
                Node child = loader.load(getClass().getResource("/Views/ProductoTemplate.fxml").openStream());
                if (child == null) {
                    return;
                }
                ProductoTemplateController controller = loader.getController();
                controller.setInfoProducto();
                flowPaneProducts.getChildren().add(child);
            } catch (IOException ex) {
                Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }

     public void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
}
