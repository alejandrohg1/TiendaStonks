package Controller;

import DataBase.ProductoData;
import DataBase.ProveedorData;
import Pojo.Producto;
import Pojo.Proveedor;
import Pojo.Usuario;
import animatefx.animation.FadeIn;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProveedoresController implements Initializable {
    private ProveedorData proveedorData = new ProveedorData();
    private ProductoData productoData = new ProductoData();
    @FXML
    private  TableView<Proveedor> tblProveedores;
    @FXML
    private TableColumn<Proveedor,String> columnId;
    @FXML
    private TableColumn<Proveedor,String> columnNombre;
    @FXML
    private TableColumn<Proveedor,String> columnApellido;
    @FXML
    private TableColumn<Proveedor,String> columnCedula;
    @FXML
    private TableColumn<Proveedor,String> columnCorreo;
    @FXML
    private TableColumn<Proveedor,String > columnTelefono;
    @FXML
    private TableColumn<Proveedor,String> columnRuc;
    @FXML
    private TextField txtProveedor;
    @FXML
    private AnchorPane anchorPaneMain;

    public static ObservableList<Proveedor> proveedors;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        proveedorData.loadFromGson();
        productoData.loadFromGson();
        proveedors = FXCollections.observableArrayList(proveedorData.getProveedores());
        starColumns();
        filterTextField();


    }


    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void backToLogin(ActionEvent event) throws IOException {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.show();
        new FadeIn(root).play();
    }

    public void starColumns(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        columnId.setMinWidth(150);
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
        columnRuc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        columnRuc.setMinWidth(150);

        tblProveedores.setItems(proveedors);

    }

    public void getProveedor(MouseEvent event) {
        tblProveedores.getSelectionModel().getSelectedItem();
    }

    public void filterTextField() {

        FilteredList<Proveedor> filteredUsuarios = new FilteredList<>(proveedors);
        txtProveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsuarios.setPredicate((Predicate<? super Proveedor>) user -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if (user.getNombreProv().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getApellidoProv().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getCedula().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getCorreo().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getTelefono().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getIdProveedor().toLowerCase().contains(lowerCase)) {
                    return true;
                }else if(user.getRuc().toLowerCase().contains(lowerCase)){
                    return true;
                }
                return false;
            });
        });

        SortedList<Proveedor> sortedList = new SortedList<>(filteredUsuarios);
        sortedList.comparatorProperty().bind(tblProveedores.comparatorProperty());
        tblProveedores.setItems(sortedList);

    }

    public void registerUser(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ProveedorRegister.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.setTitle("Registrar Proveedores");
        stage.show();

    }

    public ObservableList<Proveedor> getProveedors() {
        return proveedors;
    }

    public void saveProveedor(Proveedor proveedor){
        proveedors.add(proveedor);
        proveedorData.addToGson(proveedors);
    }

    public void eliminarProveedor(ActionEvent event) {
        if(tblProveedores.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Advertencia!!!!");
            alert.setContentText("Seleccione un proveedor para realizar esta operacion");
            alert.showAndWait();
        }else{
            ButtonType eliminar = new ButtonType("Eliminar");
            ButtonType cancelar = new ButtonType("Cancelar");
            Alert a = new Alert(Alert.AlertType.ERROR, "Elminar Proveedor:", eliminar, cancelar);
            a.setTitle("Elimar Usuario");
            a.setContentText("Esta Seguro que quiere elimanar este Proveedor? Se Perderan todos los articulos de este");
            a.showAndWait().ifPresent(response -> {
                if (response == eliminar) {
                    if (tblProveedores.getSelectionModel().getSelectedItem() == null) {
                        return;
                    } else {
                        proveedors.remove(tblProveedores.getSelectionModel().getSelectedItem());
                        productoData.getProductos().removeAll(getProductList(tblProveedores.getSelectionModel().getSelectedItem()));
                        proveedorData.addToGson(proveedors);
                        productoData.updateProductList();
                    }
                } else if (response == cancelar) {
                    a.close();
                }
            });
        }

    }

    public void editUser(ActionEvent event) throws IOException {
        if(tblProveedores.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Advertencia!!!!");
            alert.setContentText("Seleccione un proveedor para realizar esta operacion");
            alert.showAndWait();
        }else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/ProveedorRegister.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("resources/images/iconTienda.png"));
            stage.setTitle("Editar Proveedor");
            stage.show();

            fxmlLoader.setLocation(getClass().getResource("/Views/ProveedorRegister.fxml"));
            ProveedorRegisterController provReg = fxmlLoader.getController();

            provReg.initData(tblProveedores.getSelectionModel().getSelectedItem(),true);
        }
    }

    public TableView<Proveedor> getTblProveedores() {
        return tblProveedores;
    }

    public void updateGson(){
        proveedorData.addToGson(proveedors);
    }


    public void refreshTable(MouseEvent event) {
        tblProveedores.refresh();
    }

    public void InfoProveedor(ActionEvent event) throws IOException {

        if(tblProveedores.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Advertencia!!!!");
            alert.setContentText("Seleccione un proveedor para realizar esta operacion");
            alert.showAndWait();
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/InfoProvScene.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("resources/images/iconTienda.png"));
            stage.setTitle("Informacion Proveedor");
            stage.show();

            fxmlLoader.setLocation(getClass().getResource("/Views/InfoProvScene.fxml"));
            InfoProvController infoProvController = fxmlLoader.getController();
            infoProvController.setProveedor(tblProveedores.getSelectionModel().getSelectedItem());
            infoProvController.setListViewProductos(getProductNameList(tblProveedores.getSelectionModel().getSelectedItem()));
        }

    }

    public ObservableList<String>  getProductNameList(Proveedor proveedors){
        ObservableList<String> productos = FXCollections.observableArrayList();
        productoData.getProductos().forEach(producto -> {
            if(producto.getIdProveedor().equals(proveedors.getIdProveedor())){
                productos.add(producto.getDescripcion());
            }
        });
        return productos;
    }

    public ObservableList<Producto>  getProductList(Proveedor proveedors){
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        productoData.getProductos().forEach(producto -> {
            if(producto.getIdProveedor().equals(proveedors.getIdProveedor())){
                productos.add(producto);
            }
        });
        return productos;
    }
}
