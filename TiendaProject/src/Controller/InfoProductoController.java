/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ProductoController.productoObservableList;
import static Controller.UsuarioController.usuarioObservableList;
import DataBase.ProductoData;
import DataBase.ProveedorData;
import Pojo.Producto;
import Pojo.Proveedor;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Yiin Anton
 */
public class InfoProductoController implements Initializable {

    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtIDProducto;
    @FXML
    private TextField txtIDProveedor;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtSeccion;
    @FXML
    private ComboBox<String> cboNombreProv;
    @FXML
    private TextField txtapellidoProv;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtRUC;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCedula;
    @FXML
    private Button btnNuevoProv;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ImageView imgProducto;
    @FXML
    private TextField txtURL;
    
    public static ObservableList<Producto> prodTemp;
    public static ObservableList<Proveedor> provTemp;
    private ObservableList<String> Nombres = FXCollections.observableArrayList();
    private boolean isEdit;
    private Producto editado;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InfoProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void loadData() throws FileNotFoundException{
        loadFromGsonProducto();
        loadFromGsonProveedor();
        llenarItems();
    }    
    @FXML
    private void btnNuevoProvAction(ActionEvent event) {
        //llamar al stage de Nuevo Proveedor
    }

    @FXML
    private void btnGuardar_Action(ActionEvent event) throws FileNotFoundException {
        if(isEdit){
            for(int i = 0; i < prodTemp.size(); i++){
                if(prodTemp.get(i).getIdprducto().equals(this.editado.getIdprducto())){
                    prodTemp.get(i).setDescripcion(txtDescripcion.getText());
                    prodTemp.get(i).setFotoUrl(txtURL.getText());
                    prodTemp.get(i).setPrecio(Float.parseFloat(txtPrecio.getText()));
                    prodTemp.get(i).setPrecio(Float.parseFloat(txtPrecio.getText()));
                    prodTemp.get(i).setSeccion(txtSeccion.getText());
                    prodTemp.get(i).setStock(txtStock.getText());
                }   
            }
            updateProducto();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Guardar Producto");
            a.setContentText("Se guardo Exitosamente");
            a.showAndWait();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }else{
            Producto nuevo = new Producto();
            nuevo.setDescripcion(txtDescripcion.getText());
            nuevo.setIdProveedor(cboNombreProv.getSelectionModel().getSelectedItem());
            nuevo.setIdprducto(txtIDProducto.getText());
            nuevo.setPrecio(Float.parseFloat(txtPrecio.getText()));
            nuevo.setSeccion(txtSeccion.getText());
            nuevo.setStock(txtStock.getText());
            nuevo.setFotoUrl(txtURL.getText());
            saveProducto(nuevo);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Guardar Producto");
            a.setContentText("Se guardo Exitosamente");
            a.showAndWait();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
        isEdit = false;
    }

    @FXML
    private void btnCancelar_Action(ActionEvent event) {
        ButtonType continuar = new ButtonType("Continuar");
        ButtonType cancelar = new ButtonType("Cancelar");
        Alert a = new Alert(Alert.AlertType.WARNING, "Cancelar Operacion:", continuar, cancelar);
        a.setTitle("Cancelar Accion");
        a.setContentText("Esta Seguro de Cancelar esta operacion?");
        a.showAndWait().ifPresent(response -> {
        if (response == continuar) {
            a.close();
        } else if (response == cancelar) {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
        });
    }
    
    private void llenarItems(){
        Nombres.clear();
        provTemp.forEach(p -> {
            Nombres.add(p.getNombreProv());
        });
        cboNombreProv.setItems(Nombres);
    }
    
    @FXML
    private void llenarDatosProv(ActionEvent event){
        provTemp.stream().filter(p -> (cboNombreProv.getSelectionModel().getSelectedItem().equals(p.getNombreProv()))).map(p -> {
            txtIDProveedor.setText(p.getIdProveedor());
            return p;
        }).map(p -> {
            txtapellidoProv.setText(p.getApellidoProv());
            return p;
        }).map(p -> {
            txtCorreo.setText(p.getCorreo());
            return p;
        }).map(p -> {
            txtRUC.setText(p.getRuc());
            return p;
        }).map(p -> {
            txtTelefono.setText(p.getTelefono());
            return p;
        }).forEachOrdered(p -> {
            txtCedula.setText(p.getCedula());
        });
    }
    
    @FXML
    private void cargarImgProducto(KeyEvent event) throws IOException {
        if(event.getCode().equals(event.getCode().ENTER)){
            //imgProducto.setImage(new Image(txtURL.getText()));
            BufferedImage image = null;
            Image icon = null;
            try {
            image = ImageIO.read(new URL(txtURL.getText()));            
            } catch (MalformedURLException ex) {
                Logger.getLogger(ProductoTemplateController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("no se hallo imagen");
            }
            if(image == null){
                icon = new Image(getClass().getResourceAsStream("/resources/images/iconTienda.png"));
                imgProducto.setImage(icon);
            }else{
                imgProducto.setImage(SwingFXUtils.toFXImage(image, null));
                System.out.println("Se cargo exitosamente la imagen");
            }
            
        }
    }
    public void modificarProv(boolean state){
        cboNombreProv.setEditable(state);
        cboNombreProv.disabledProperty();
        txtIDProducto.setEditable(state);
        txtIDProveedor.setEditable(state);
        txtapellidoProv.setEditable(state);
        txtCorreo.setEditable(state);
        txtRUC.setEditable(state);
        txtTelefono.setEditable(state);
        txtCedula.setEditable(state); 
    }
    public void editProducto(Producto p, boolean state){
        isEdit = true;
        modificarProv(state);
        txtDescripcion.setText(p.getDescripcion());
        txtSeccion.setText(p.getSeccion());
        txtStock.setText(p.getStock());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtIDProducto.setText(p.getIdprducto());
        txtURL.setText(p.getFotoUrl());
        imgProducto.setImage(new Image(p.getFotoUrl()));
        for(Proveedor prov: provTemp){
            if(prov.getIdProveedor().equals(p.getIdProveedor())){
                txtIDProveedor.setText(prov.getIdProveedor());
                txtapellidoProv.setText(prov.getApellidoProv());
                txtCorreo.setText(prov.getCorreo());
                txtRUC.setText(prov.getRuc());
                txtTelefono.setText(prov.getTelefono());
                txtCedula.setText(prov.getCedula());
                cboNombreProv.getSelectionModel().select(prov.getNombreProv());
            }
        }
        this.editado = p;
    }
  
    
    //Gestion de Datos
    public void loadFromGsonProducto() {
        Gson gson = new Gson();
        prodTemp = FXCollections.observableArrayList();

        try {
            prodTemp.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/productos.json"), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void loadFromGsonProveedor() {
        Gson gson = new Gson();
        provTemp = FXCollections.observableArrayList();

        try {
            provTemp.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/ProveedorData.json"), Proveedor[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public  void addToGsonProducto(ObservableList<Producto> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/resources/Data/productos.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

            ObservableList<Producto> jsonArray = newData;
            gson.toJson(jsonArray, flw);

        try {
            flw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void saveProducto(Producto p) {
        prodTemp.add(p);
        addToGsonProducto(prodTemp);
    }
    public void updateProducto() {
        addToGsonProducto(prodTemp);
    }
}
