package Controller;

import DataBase.FacturaData;
import DataBase.ProductoData;
import Pojo.Factura;
import Pojo.FacturaTableModel;
import Pojo.Producto;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.*;
import java.util.List;

public class VentasController implements Initializable {
    @FXML
    private TextField txtProducts;
    @FXML
    private TextField txtPrecio,txtFactura,txtCantidad,txtNeto,txtTotal,txtCambio,txtSaldoCliente;
    @FXML
    private TextField txtCodigoArticulo;
    @FXML
    private TableView<FacturaTableModel> tblProducto;
    @FXML
    private TableColumn<FacturaTableModel,String> columnCodigo;
    @FXML
    private TableColumn<FacturaTableModel,String> columnDescripcion;
    @FXML
    private TableColumn<FacturaTableModel,Integer> columnCantidad;
    @FXML
    private TableColumn<FacturaTableModel,Float> columnPrecioUni;
    @FXML
    private TableColumn<FacturaTableModel,Float> columnPrecioTotal;
    @FXML
    private TextField txtStockDisponible;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private DatePicker datePickerFactura;
    @FXML
    private Button buttonCancelar,buttonNuevaFactura,buttonGuardarFac,buttonAdd;
    @FXML
    private ComboBox<String> cmbMoneda;
    @FXML
    private ChoiceBox<String> cmbDescuento;
    @FXML
    private TextArea txtObservaciones;



    public ObservableList<FacturaTableModel> productosFactura;
    public ProductoData productoData = new ProductoData();
    public FacturaData facturaData = new FacturaData();
    public List<String> suggestions = new ArrayList<>();
    public float descuento = 0f;
    public static Producto productoSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productoData.loadFromGson();
        facturaData.loadFromGson();
        productosFactura = FXCollections.observableArrayList();
        List<Producto> products = productoData.getProductsAsList();
        starColumns();

        setTooltip();
        setComboBox();
        setEditableAllText(false);
        listenerContextMenu();

        //AutoBusqueda

        products.forEach(c -> {
            suggestions.add(c.getDescripcion());
        });
        AutoCompletionBinding<String> p;
        TextFields.bindAutoCompletion(txtProducts,suggestions);
        final String[] temp = new String[1];
        txtProducts.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oldValue){
                    Producto p = products.stream().filter(c -> c.getDescripcion().equals(txtProducts.getText())).findFirst().orElse(null);
                    if(p != null){
                       txtPrecio.setText(Float.toString(p.getPrecio()));
                        txtCodigoArticulo.setText(p.getIdprducto());
                        txtStockDisponible.setText(p.getStock());
                        productoSelected = p;

                    }else{
                        //: )
                        return;
                    }
                }
            }
        });

    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }



    private  void setTooltip(){
        buttonCancelar.setTooltip(new Tooltip("Cancelar Factura"));
        buttonGuardarFac.setTooltip(new Tooltip("Guardar Factura"));
        buttonNuevaFactura.setTooltip(new Tooltip("Nueva Factura"));
    }

    private void setComboBox(){
        ObservableList<String> listMoneda = FXCollections.observableArrayList("Cordoba","Dolar","Euro");
        ObservableList<String> listDescuento = FXCollections.observableArrayList("0%","5%","10%","15%","20%","25%","30%","35%","40%","45%", "50%");
        cmbMoneda.setItems(listMoneda);
        cmbDescuento.setItems(listDescuento);
        cmbMoneda.getSelectionModel().selectFirst();

    }

    public void newFactura(ActionEvent event) {
        Random random = new Random();
        char randomLetter = (char) ((char)random.nextInt(26) + 'A');
        txtFactura.setText(String.valueOf(random.nextInt(99999))+randomLetter);
        setEditableAllText(true);
        txtProducts.requestFocus();

        productosFactura.clear();
        clearAllText();

    }

    public void setProductsIntoTable(KeyEvent keyEvent) {

        if(keyEvent.getCode().equals(keyEvent.getCode().ENTER)){

            FacturaTableModel factura = new FacturaTableModel();
            factura.setCantidad(Integer.parseInt(txtCantidad.getText()));
            factura.setCodigo(txtCodigoArticulo.getText());
            factura.setDescripcion(txtProducts.getText());
            factura.setPrecioUni(Integer.parseInt(txtPrecio.getText()));
            factura.setPrecioTotal(Integer.parseInt(txtPrecio.getText()) * Integer.parseInt(txtCantidad.getText()));

            productosFactura.add(factura);


        }
    }

    public void starColumns(){
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnCodigo.setMinWidth(100);
        columnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnCodigo.setMinWidth(100);
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnDescripcion.setMinWidth(500);
        columnPrecioUni.setCellValueFactory(new PropertyValueFactory<>("precioUni"));
        columnPrecioUni.setMinWidth(100);
        columnPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        columnPrecioTotal.setMinWidth(100);

        tblProducto.setItems(productosFactura);
    }

    public void populateTable(ActionEvent event) {
        boolean isRepited = false;
        FacturaTableModel factura = new FacturaTableModel();
        if(Integer.parseInt(txtCantidad.getText()) > Integer.parseInt(productoSelected.getStock())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cantidad mayor a la que proviene de stock");
            alert.setTitle("Error de stock");
            alert.showAndWait();
            return;
        }
        try{

            factura.setCantidad(Integer.parseInt(txtCantidad.getText()));
            factura.setPrecioUni(Float.parseFloat(txtPrecio.getText()));
            factura.setPrecioTotal(Float.parseFloat(txtPrecio.getText()) * Float.parseFloat(txtCantidad.getText()));
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de cantidad");
            alert.setContentText("Cantidad tiene que ser mayor a 0");
            return;
        }
        factura.setCodigo(txtCodigoArticulo.getText());
        factura.setDescripcion(txtProducts.getText());



        for (FacturaTableModel p: productosFactura) {
            if(p.getDescripcion().equals(factura.getDescripcion())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Elemento Repetido");
                alert.setContentText("Elemento Repetido,no se puede realizar esta accion");
                alert.showAndWait();
                isRepited = true;
                break;
            }
        }

        if(!isRepited){
            productosFactura.add(factura);
            txtProducts.requestFocus();
            float total = 0;
            for ( FacturaTableModel f: tblProducto.getItems()) {
                total = total + f.getPrecioTotal();
            }

            //Set TextFields de Neto,Total,Cambio

            String numeroLetra =null;
            if(cmbDescuento.getSelectionModel().isEmpty()) {
                txtNeto.setText(String.valueOf(total));
                txtTotal.setText(String.valueOf(total*1.15));
                txtCambio.setText(String.valueOf(-total*1.15));
            }else{
                try {
                    numeroLetra =cmbDescuento.getSelectionModel().getSelectedItem();
                    descuento = Float.parseFloat(numeroLetra.substring(0,2))/100;
                }catch (NumberFormatException e){
                    descuento =0;
                }
                txtNeto.setText(String.valueOf(total));
                total= total*1.15f;
                total = total - (total*descuento);
                txtTotal.setText(String.valueOf(total));
                txtCambio.setText(String.valueOf(-total));
            }
        }else
            return;




    }

    public void setEditableAllText(boolean state){
        txtProducts.setEditable(state);
        txtCantidad.setEditable(state);
        txtObservaciones.setEditable(state);
        datePickerFactura.setEditable(state);

    }

    public void clearAllText(){
        txtProducts.clear();
        txtCantidad.clear();
        txtNeto.clear();
        txtTotal.clear();
        txtCambio.clear();
        txtPrecio.clear();
        txtCodigoArticulo.clear();
        txtStockDisponible.clear();
        txtObservaciones.clear();
        txtSaldoCliente.clear();

    }


    public void saldarCuenta(ActionEvent event) {

        float cambio = Float.parseFloat(txtSaldoCliente.getText()) - Float.parseFloat(txtTotal.getText());
        txtCambio.setText(String.valueOf(cambio));
    }

    public void listenerContextMenu(){
        ContextMenu context = new ContextMenu();
        MenuItem itemContext;
        itemContext = new MenuItem(" Eliminar ");
        context.getItems().addAll(itemContext);
        tblProducto.setContextMenu(context);

        itemContext.setOnAction((event) -> {
            productosFactura.remove(tblProducto.getSelectionModel().getSelectedItem());
        });

        tblProducto.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY){
                    context.show(tblProducto, event.getScreenX(), event.getScreenY());
                }
            }
        });

    }

    public void cancelFactura(ActionEvent event) {
        clearAllText();
        productosFactura.clear();
    }

    public void saveFactura(ActionEvent event) {
        Factura factura = new Factura();
        List<String> nombreProductos = new ArrayList<>();
        tblProducto.getItems().forEach(producto -> {
            nombreProductos.add(producto.getDescripcion());
        });

        factura.setObservaciones(txtObservaciones.getText());
        factura.setCodigoFactura(txtFactura.getText());
        factura.setFechaDeFacturacion(datePickerFactura.getValue().toString());
        factura.setDescuento(cmbDescuento.getValue());
        factura.setMonedaCambio(cmbMoneda.getValue());
        factura.setCambio(Float.parseFloat(txtCambio.getText()));
        factura.setNeto(Float.parseFloat(txtNeto.getText()));
        factura.setSaldo(Float.parseFloat(txtSaldoCliente.getText()));
        factura.setTotal(Float.parseFloat(txtTotal.getText()));
        factura.setProductosFactura(nombreProductos);

        facturaData.getFacturas().add(factura);
        facturaData.updateFacturaList();

        clearAllText();


    }
}
