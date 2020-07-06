package Controller;

import DataBase.FacturaData;
import Pojo.Factura;
import Pojo.FacturaTableModel;
import Pojo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class FacturaSceneController implements Initializable {

    @FXML
    private TableView<Factura> tblFacturas;
    @FXML
    private TableColumn<Factura,String> columnCodigo;
    @FXML
    private TableColumn<Factura,String> columnFecha;
    @FXML
    private TableColumn<Factura,Float> columnNeto;
    @FXML
    private TableColumn<Factura,Float> columnTotal;
    @FXML
    private TableColumn<Factura,Float> columnSaldo;
    @FXML
    private TableColumn<Factura,Float> columnCambio;
    @FXML
    private TableColumn<Factura, List<String>> columnProductos;
    @FXML
    private TextField txtBuscar;

    private FacturaData facturaData = new FacturaData();
    private ObservableList<Factura> facturaObservableList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facturaData.loadFromGson();
        facturaObservableList = FXCollections.observableList(facturaData.getFacturas());
        starColumns();
        filterTextField();
    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void starColumns(){
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoFactura"));
        columnCodigo.setMinWidth(100);
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fechaDeFacturacion"));
        columnFecha.setMinWidth(200);
        columnNeto.setCellValueFactory(new PropertyValueFactory<>("neto"));
        columnNeto.setMinWidth(100);
        columnSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        columnSaldo.setMinWidth(100);
        columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        columnTotal.setMinWidth(100);
        columnCambio.setCellValueFactory(new PropertyValueFactory<>("cambio"));
        columnCambio.setMinWidth(100);
        columnProductos.setCellValueFactory(new PropertyValueFactory<>("productosFactura"));
        columnProductos.setMinWidth(300);

        tblFacturas.setItems(facturaObservableList);

    }

    public void filterTextField() {

        FilteredList<Factura> filteredUsuarios = new FilteredList<>(facturaObservableList);
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsuarios.setPredicate((Predicate<? super Factura>) user -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();

                if (user.getCodigoFactura().toLowerCase().contains(lowerCase)) {
                    return true;
                } else if (user.getFechaDeFacturacion().toLowerCase().contains(lowerCase)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Factura> sortedList = new SortedList<>(filteredUsuarios);
        sortedList.comparatorProperty().bind(tblFacturas.comparatorProperty());
        tblFacturas.setItems(sortedList);


    }
}
