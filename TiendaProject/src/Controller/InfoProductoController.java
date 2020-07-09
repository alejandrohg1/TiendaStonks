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
import java.io.*;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private TextField txtStock;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtSeccion;
    @FXML
    private ComboBox<String> cboNombreProv;
    @FXML
    private TableView<Proveedor> tblProveedor;
    @FXML
    private TableColumn<Proveedor, String> columnID;
    @FXML
    private TableColumn<Proveedor, String> columnNombre;
    @FXML
    private TableColumn<Proveedor, String> columnApellido;
    @FXML
    private TableColumn<Proveedor, String> columnTelefono;
    @FXML
    private TableColumn<Proveedor, String> columnRUC;
    @FXML
    private TableColumn<Proveedor, String> columnCedula;
    @FXML
    private TableColumn<Proveedor, String> columnCorreo;
    @FXML
    private Button btnExistente;
    @FXML
    private Button btnDescargar;
    @FXML
    private Button btnNuevoProv;
    @FXML
    private Label lblNombre;

    @FXML
    private ImageView imgProducto;
    @FXML
    private TextField txtURL;
    
    public static ObservableList<Producto> prodTemp;
    public static ObservableList<Proveedor> provTemp;
    private ObservableList<String> Nombres = FXCollections.observableArrayList();
    private boolean isEdit;
    private Producto editado;
    private ObservableList<Proveedor>seleccionado = FXCollections.observableArrayList();
    private ProveedorData proveedorData = new ProveedorData();
    private ProductoData productoData = new ProductoData();
    private BufferedImage image = null;
    private Image icon = null;
    private File selectedImage;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
            starColumns();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InfoProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void loadData() throws FileNotFoundException{
        proveedorData.loadFromGson();
        productoData.loadFromGson();
        prodTemp = FXCollections.observableArrayList(productoData.getProductos());
        provTemp = FXCollections.observableArrayList(proveedorData.getProveedores());
        llenarItems();
    }    
    @FXML
    private void btnNuevoProvAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ProveedorRegister.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.setTitle("Registrar Proveedor");
        stage.show();
        ProveedorRegisterController temp = fxmlLoader.getController();
        temp.isStageProducto(true);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void btnGuardar_Action(ActionEvent event) throws IOException {
        if(txtDescripcion.getText().isEmpty() || txtURL.getText().isEmpty() || txtIDProducto.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtSeccion.getText().isEmpty() || txtStock.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Guardar Producto");
            a.setContentText("Por Favor Ingrese todos los Datos");
            a.showAndWait();
            return;
        }

        if(isEdit){
            for(int i = 0; i < prodTemp.size(); i++){
                if(prodTemp.get(i).getIdprducto().equals(this.editado.getIdprducto())){
                    prodTemp.get(i).setDescripcion(txtDescripcion.getText());
                    prodTemp.get(i).setFotoUrl(txtURL.getText());
                    prodTemp.get(i).setPrecio(Float.parseFloat(txtPrecio.getText()));
                    prodTemp.get(i).setPrecio(Float.parseFloat(txtPrecio.getText()));
                    prodTemp.get(i).setSeccion(txtSeccion.getText());
                    prodTemp.get(i).setStock(txtStock.getText());
                    if(txtURL.getText() != prodTemp.get(i).getFotoUrl()){
                        saveToFile(imgProducto);
                        String folderPath = "C:/GitHubVictor/TiendaStonks/TiendaProject/src/resources/productos"+txtDescripcion.getText()+".png";
                        prodTemp.get(i).setFotoUrl(folderPath);
                    }
                }   
            }
            productoData.addToGson(prodTemp);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Guardar Producto");
            a.setContentText("Se guardo Exitosamente");
            a.showAndWait();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }else{
            Producto nuevo = new Producto();
            Proveedor datosProv = new Proveedor();
            datosProv = tblProveedor.getSelectionModel().getSelectedItem();
            nuevo.setDescripcion(txtDescripcion.getText());
            nuevo.setIdProveedor(datosProv.getIdProveedor());
            nuevo.setIdprducto(txtIDProducto.getText());
            nuevo.setPrecio(Float.parseFloat(txtPrecio.getText()));
            nuevo.setSeccion(txtSeccion.getText());
            nuevo.setStock(txtStock.getText());
            nuevo.setFotoUrl(txtURL.getText());
            nuevo.setProveedor(datosProv);
            productoData.saveProducto(nuevo);
            if(txtURL.getText().isEmpty()){
                icon = new Image(getClass().getResourceAsStream("/resources/images/iconTienda.png"));
                imgProducto.setImage(icon);
                saveToFile(imgProducto);
            }else{
                saveToFile(imgProducto);
            }
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

    @FXML
    private void llenarDatosProv(ActionEvent event){
            for (int i = 0; i < provTemp.size(); i++){
                if(cboNombreProv.getSelectionModel().getSelectedItem().equals(provTemp.get(i).getNombreProv())){
                    System.out.println(seleccionado.size() - 1);
                    if(seleccionado.size() == 0){
                        seleccionado.add(provTemp.get(i));
                    }else{
                        seleccionado.clear();
                        seleccionado.add(provTemp.get(i));
                    }
                }
            }
            tblProveedor.setItems(seleccionado);
    }

    @FXML
    private void cargarImgProducto(KeyEvent event) throws IOException {
        if(event.getCode().equals(event.getCode().ENTER)){
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

    @FXML
    void cargarImagenLocal(ActionEvent event) throws IllegalArgumentException, IOException  {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File selectedImage = fc.showOpenDialog(null);
        if (selectedImage == null)
            return;
        Image img = SwingFXUtils.toFXImage(ImageIO.read(selectedImage), null);
        imgProducto.setImage(img);
        txtURL.setText(selectedImage.getPath());


    }

    @FXML
    void descargarImagen(ActionEvent event) {
        try {
            image = ImageIO.read(new URL(txtURL.getText()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProductoTemplateController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se hallo imagen");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(image == null){
            icon = new Image(getClass().getResourceAsStream("/resources/images/iconTienda.png"));
            imgProducto.setImage(icon);
        }else{
            imgProducto.setImage(SwingFXUtils.toFXImage(image, null));
            System.out.println("Se cargo exitosamente la imagen");
        }
    }

    public void setNewProveedor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/InfoProducto.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.setTitle("Registrar Producto");
        stage.show();
    }

    public void llenarItems(){
            cboNombreProv.getItems().clear();
            provTemp.forEach(p -> {
                cboNombreProv.getItems().add(p.getNombreProv());
            });
    }

    public void starColumns(){
        columnID.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        columnID.setMinWidth(150);
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProv"));
        columnNombre.setMinWidth(150);
        columnApellido.setCellValueFactory(new PropertyValueFactory<>("apellidoProv"));
        columnApellido.setMinWidth(150);
        columnCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        columnCedula.setMinWidth(150);
        columnCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        columnCorreo.setMinWidth(150);
        columnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnTelefono.setMinWidth(150);
        columnRUC.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        columnRUC.setMinWidth(150);
    }
    public void modificarProv(boolean state){
        btnNuevoProv.setVisible(state);
        //cboNombreProv.setEditable(state);
        //cboNombreProv.disabledProperty();
        txtIDProducto.setEditable(state);
        btnNuevoProv.setVisible(state);
        cboNombreProv.setVisible(state);
        lblNombre.setVisible(state);

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
        this.editado = p;
        if(seleccionado.size() == 0){
            seleccionado.add(p.getProveedor());
            tblProveedor.setItems(seleccionado);
        }else{
            seleccionado.remove(0);
            seleccionado.add(p.getProveedor());
            tblProveedor.setItems(seleccionado);
        }
    }

    public void saveToFile(ImageView image) throws IOException {
        String folderPath = "C:/GitHubVictor/TiendaStonks/TiendaProject/src/resources/productos";
        File imageFile = new File(folderPath, txtDescripcion.getText() + ".png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
        ImageIO.write(bImage, "png", imageFile);
    }
}
