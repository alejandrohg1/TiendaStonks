package Controller;

import DataBase.ProductoData;
import DataBase.ProveedorData;
import Pojo.Producto;
import Pojo.Proveedor;
import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProductoTableController implements Initializable {

    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> columnIdProducto;
    @FXML
    private TableColumn<Producto, String> columnidProveedor;
    @FXML
    private TableColumn<Producto, String> columnDescripcion;
    @FXML
    private TableColumn<Producto, String> columnSeccion;
    @FXML
    private TableColumn<Producto, Float> columnPrecio;
    @FXML
    private TableColumn<Producto, String> columnStock;
    @FXML
    private TextField txtProducto;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnVolver;
    @FXML
    private ImageView imgProducto;

    public static ObservableList<Producto> productos;
    private ProveedorData proveedorData = new ProveedorData();
    private ProductoData productoData = new ProductoData();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        proveedorData.loadFromGson();
        productoData.loadFromGson();
        productos = FXCollections.observableArrayList(productoData.getProductos());
        starColumns();
        filterTextField();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void editarProducto(ActionEvent event) throws IOException {
        if(tblProductos.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Advertencia!!!!");
            alert.setContentText("Seleccione un producto para realizar esta operacion");
            alert.showAndWait();
        }else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/InfoProducto.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("resources/images/iconTienda.png"));
            stage.setTitle("Editar Producto");
            stage.show();

            fxmlLoader.setLocation(getClass().getResource("/Views/InfoProducto.fxml"));
            InfoProductoController prodReg = fxmlLoader.getController();

            prodReg.editProducto(tblProductos.getSelectionModel().getSelectedItem(), false  );
        }

    }

    @FXML
    void eliminarProducto(ActionEvent event) {
        if(tblProductos.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Advertencia!!!!");
            alert.setContentText("Seleccione un proveedor para realizar esta operacion");
            alert.showAndWait();
        }else{
            ButtonType eliminar = new ButtonType("Eliminar");
            ButtonType cancelar = new ButtonType("Cancelar");
            Alert a = new Alert(Alert.AlertType.ERROR, "Elminar Proveedor:", eliminar, cancelar);
            a.setTitle("Elimar Producto");
            a.setContentText("Esta Seguro que quiere elimanar este Producto? Se Perderan todos los datos de este");
            a.showAndWait().ifPresent(response -> {
                if (response == eliminar) {
                    if (tblProductos.getSelectionModel().getSelectedItem() == null) {
                        return;
                    } else {
                        productos.remove(tblProductos.getSelectionModel().getSelectedItem());
                        productoData.getProductos().removeAll(getProductList(tblProductos.getSelectionModel().getSelectedItem()));
                        productoData.addToGson(productos);
                        productoData.updateProductList();

                    }
                } else if (response == cancelar) {
                    a.close();
                }
            });
        }

    }

    @FXML
    void getProducto(MouseEvent event) {
        if (tblProductos.getSelectionModel().getSelectedItem() == null) {
            return;
        } else {
            String foto = tblProductos.getSelectionModel().getSelectedItem().getFotoUrl();
            System.out.println(foto);
            imgProducto.setImage(new Image(foto));
            imgProducto.resize(200,300);
        }
        tblProductos.getSelectionModel().getSelectedItem();
    }
    @FXML
    void refreshTable(MouseEvent event) {
        tblProductos.refresh();
    }


    //metodos
    public void starColumns(){
        columnIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columnIdProducto.setMinWidth(150);
        columnidProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        columnidProveedor.setMinWidth(150);
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnDescripcion.setMinWidth(150);
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnPrecio.setMinWidth(150);
        columnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        columnStock.setMinWidth(150);
        columnSeccion.setCellValueFactory(new PropertyValueFactory<>("seccion"));
        columnSeccion.setMinWidth(150);
        tblProductos.setItems(productos);
    }

    public void filterTextField() {

        FilteredList<Producto> filteredProductos = new FilteredList<>(productos);
        txtProducto .textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductos.setPredicate((Predicate<? super Producto>) user -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if (user.getDescripcion().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getIdprducto().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getIdProveedor().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getStock().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getSeccion() .toLowerCase().contains(lowerCase)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Producto> sortedList = new SortedList<>(filteredProductos);
        sortedList.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(sortedList);
    }

    public ObservableList<Producto>  getProductList(Producto proveedors){
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        productoData.getProductos().forEach(producto -> {
            if(producto.getIdProveedor().equals(proveedors.getIdProveedor())){
                productos.add(producto);
            }
        });
        return productos;
    }
}