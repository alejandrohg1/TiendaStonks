package Controller;

import Pojo.Producto;
import Pojo.Proveedor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InfoProvController implements Initializable {

    @FXML
    private TextField txtIdProv,txtNombre,txtApellido,txtCedula,txtRuc,txtTelefono;
    @FXML
    private TextArea txtCorreo;
    @FXML
    private ListView<String> listViewProductos;
    private static Proveedor proveedorNew;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setProveedor(Proveedor proveedor){
        txtIdProv.setText(proveedor.getIdProveedor());
        txtNombre.setText(proveedor.getNombreProv());
        txtApellido.setText(proveedor.getApellidoProv());
        txtCedula.setText(proveedor.getCedula());
        txtCorreo.setText(proveedor.getCorreo());
        txtRuc.setText(proveedor.getRuc());
        txtTelefono.setText(proveedor.getTelefono());
    }

    public void setListViewProductos(ObservableList<String> productos){
        listViewProductos.setItems(productos);
    }
}
