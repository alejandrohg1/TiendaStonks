/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Pojo.Producto;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private ImageView imgvProduct;
    @FXML
    private Label lblCod;
    @FXML
    private Label lblProductDescription;
    @FXML
    private Label lblProductCantidad;
    @FXML
    private Label lblProductPrecio;
    @FXML
    private Label lblCodProveedor;
    @FXML
    private Label lblProductSection;
    @FXML
    private AnchorPane anchorPaneimg;
    @FXML
    private Button btnDetalles;
    @FXML
    private AnchorPane anchorpaneMain;
    
    private Producto producto;
    private boolean state = false;
    
    
    
    public void setInfoProducto(Producto producto){
        
        this.producto = producto;
        if (producto == null) {
            return;
        }
        lblCod.setText("ID Producto: " + producto.getIdprducto());
        lblCodProveedor.setText("ID Proveedor: " + producto.getIdProveedor());
        lblProductDescription.setText("Descripcion: " + producto.getDescripcion());
        lblProductSection.setText("Seccion: " + producto.getSeccion());
        lblProductCantidad.setText(String.valueOf("Cantidad: " + producto.getStock()));
        lblProductPrecio.setText(String.valueOf("Precio: C$" + producto.getPrecio()));
        imgvProduct.setImage(new Image(producto.getFotoUrl()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        anchorPaneimg.setStyle("-fx-background-color: #ffffff;\n"
                + "    -fx-box-border: #ffffff;");
        
        borderPaneProducts.setStyle("-fx-background-color: #ffffff;\n"
                + "    -fx-box-border: #ffffff;");
        
        btnDetalles.hoverProperty().addListener(l->{
         btnDetalles.setStyle("-fx-text-fill: #431fa0; \n"
                 + "-fx-cursor: hand;");
        });
        
        
    } 

    @FXML
    private void verDetalles(ActionEvent event) throws IOException {
        System.out.println(producto.toString());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/InfoProducto.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.setTitle("Editar Producto");
        stage.show();
        InfoProductoController temp = fxmlLoader.getController();
        temp.editProducto(producto, state);
    }
}
