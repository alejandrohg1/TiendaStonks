package Controller;

import DataBase.ProveedorData;
import Pojo.Producto;
import Pojo.Proveedor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProveedorRegisterController implements Initializable {

    @FXML
    private TextField txtID,txtRuc,txtNombre,txtApellido,txtCedula,txtTelefono;
    @FXML
    private TextArea txtCorreo;
    @FXML
    private Button buttonGuardar;

    private ProveedorData proveedorData = new ProveedorData();
    private static boolean isEdit =false;
    private static Proveedor proveedorEdit;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        proveedorData.loadFromGson();
    }

    public void saveEmpleado(ActionEvent event) throws IOException {
        if(isEdit){
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            ProveedoresController provCont = new ProveedoresController();
            editUser(proveedorEdit);
            provCont.updateGson();
        }else{
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            ProveedoresController proveedoresController = new ProveedoresController();
            Proveedor proveedor = new Proveedor(txtNombre.getText(),txtApellido.getText(),txtCedula.getText(),txtCorreo.getText(),txtTelefono.getText(),txtRuc.getText(),txtID.getText());
            proveedoresController.saveProveedor(proveedor);
        }

    }

    public void cancelRegister(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public  void initData(Proveedor proveedor, boolean state){
        txtID.setText(proveedor.getIdProveedor());
        txtNombre.setText(proveedor.getNombreProv());
        txtApellido.setText(proveedor.getApellidoProv());
        txtCedula.setText(proveedor.getCedula());
        txtCorreo.setText(proveedor.getCorreo());
        txtRuc.setText(proveedor.getRuc());
        txtTelefono.setText(proveedor.getTelefono());
        isEdit = true;
        proveedorEdit = proveedor;
    }

    public void editUser(Proveedor proveedor){
        proveedor.setIdProveedor(txtID.getText());
        proveedor.setNombreProv(txtNombre.getText());
        proveedor.setApellidoProv(txtApellido.getText());
        proveedor.setCedula(txtCedula.getText());
        proveedor.setCorreo(txtCorreo.getText());
        proveedor.setCorreo(txtCorreo.getText());
        proveedor.setRuc(txtRuc.getText());
    }

    public Button getButtonGuardar() {
        return buttonGuardar;
    }
}
