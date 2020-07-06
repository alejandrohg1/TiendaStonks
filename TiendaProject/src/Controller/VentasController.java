package Controller;

import DataBase.ProductoData;
import Pojo.Producto;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class VentasController implements Initializable {
    @FXML
    private TextField txtProducts;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtCodigoArticulo;
    @FXML
    private TableView<Producto> tblProducto;
    @FXML
    private TextField txtStockDisponible;
    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private DatePicker datePickerFactura;
    private ObservableList<Producto> productos;
    public ProductoData productoData = new ProductoData();
    public List<String> suggestions = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productoData.loadFromGson();
        List<Producto> products = productoData.getProductsAsList();

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
                System.out.println(txtProducts.getText());
                if(oldValue){
                    Producto p = products.stream().filter(c -> c.getDescripcion().equals(txtProducts.getText())).findFirst().orElse(null);
                    if(p != null){
                       txtPrecio.setText(Float.toString(p.getPrecio()));
                        txtCodigoArticulo.setText(p.getIdprducto());
                        txtStockDisponible.setText(p.getStock());

                    }else{
                        System.out.println("Hey");
                    }
                }
            }
        });

    }

    public void closeWindow(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    public void dialogProducts(ActionEvent event) throws IOException {
        final String[] temp = new String[1];
        txtProducts.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //focus in
                if (newValue) {
                    temp[0] = txtProducts.getText();
                }

                //focus out
                if (oldValue) {

                    if (!(temp[0].equals(txtProducts.getText()))) {
                        System.out.println("New String");
                    }
                }

            }
        });
    }


    public void txtProductEvents(InputMethodEvent inputMethodEvent) {
        System.out.println("hola");
    }

    public void txtProductKey(KeyEvent keyEvent) {
        System.out.println(txtProducts.getText());
    }
    public void getName(TextField t){
        System.out.println(t.getText());
    }
    public void txtProductChange(InputMethodEvent inputMethodEvent) {
        //txtProducts.setOnAction(c-> getName(txtProducts));

    }
}
