/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Yiin Anton
 */
public class ProductoTemplateController implements Initializable {

    @FXML
    private BorderPane borderPaneProducts;
    @FXML
    private Button btnImage;
    @FXML
    private ImageView imgvProduct;

    public void setInfoProducto(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL("/resources/images/iconTienda.png"));            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProductoTemplateController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(FXMLProductoTemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image icon = null;
        if (image == null) {
            icon = new Image(getClass().getResourceAsStream("/resources/images/iconTienda.png"));
            if (icon == null) {
                System.out.println("Null");
                return;
            }
            imgvProduct.setImage(icon);
            
        }else{
            imgvProduct.setImage(SwingFXUtils.toFXImage(image, null));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnImage.setStyle("-fx-background-color: #ffffff;\n"
                + "    -fx-box-border: #ffffff;");
        borderPaneProducts.setStyle("-fx-background-color: #ffffff;\n"
                + "    -fx-box-border: #ffffff;");
    } 
    
    
    
    
}
