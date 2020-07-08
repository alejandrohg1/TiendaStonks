package Controller;

import DataBase.FacturaData;
import DataBase.ProductoData;
import Pojo.Factura;
import Pojo.FacturaTableModel;
import Pojo.Producto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

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
    private Button buttonCancelar,buttonNuevaFactura,buttonGuardarFac,btnSaldar;
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
        cmbDescuento.getSelectionModel().selectFirst();

    }

    public void newFactura(ActionEvent event) {
        Random random = new Random();
        char randomLetter = (char) ((char)random.nextInt(26) + 'A');
        txtFactura.setText(String.valueOf(random.nextInt(999999))+randomLetter);
        setEditableAllText(true);
        txtProducts.requestFocus();

        productosFactura.removeAll(tblProducto.getItems());
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
        try{
            if(Integer.parseInt(txtCantidad.getText()) > Integer.parseInt(productoSelected.getStock())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Cantidad mayor a la que proviene de stock");
                alert.setTitle("Error de stock");
                alert.showAndWait();
                return;
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ningun Producto fue ingresado o Cantidad no Valida");
            alert.setTitle("Error de Entrada");
            alert.showAndWait();
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
        try {
            float cambio = Float.parseFloat(txtSaldoCliente.getText()) - Float.parseFloat(txtTotal.getText());
            txtCambio.setText(String.valueOf(cambio));
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Saldo Invalido");
            alert.setContentText("Ingrese el saldo del cliente para poder continuar");
            alert.showAndWait();
        }

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

    public void saveFactura(ActionEvent event) throws JRException, IOException, DocumentException {
        Factura factura = new Factura();
        if(txtPrecio.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Factura");
            alert.setContentText("Agrege una nueva factura para poder Guardar");
            alert.showAndWait();
        }else if(txtSaldoCliente.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Saldo Invalido");
            alert.setContentText("Ingrese el saldo del cliente para poder continuar");
            alert.showAndWait();
        }else{
            List<String> nombreProductos = new ArrayList<>();
            tblProducto.getItems().forEach(producto -> {
                nombreProductos.add(producto.getDescripcion());
            });

            try {
                String fecha = datePickerFactura.getValue().toString();
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Fecha Invalida");
                alert.setContentText("Fecha no valida o no ingresada");
                alert.showAndWait();
                return;
            }
                factura.setObservaciones(txtObservaciones.getText());
                factura.setCodigoFactura(txtFactura.getText());
                factura.setFechaDeFacturacion(datePickerFactura.getValue().toString());
                factura.setDescuento(cmbDescuento.getValue());
                factura.setMonedaCambio(cmbMoneda.getValue());
                if(Float.parseFloat(txtCambio.getText())<0){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cambio invalido");
                    alert.setContentText("No puede quedar negativo el cambio");
                    alert.showAndWait();
                    return;
                }
                factura.setCambio(Float.parseFloat(txtCambio.getText()));
                factura.setNeto(Float.parseFloat(txtNeto.getText()));
                factura.setSaldo(Float.parseFloat(txtSaldoCliente.getText()));
                factura.setTotal(Float.parseFloat(txtTotal.getText()));
                factura.setProductosFactura(nombreProductos);

                facturaData.getFacturas().add(factura);
                facturaData.updateFacturaList();
                reportPDf();

                productosFactura.removeAll(tblProducto.getItems());
                clearAllText();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Factura Guardada");
                alert.setContentText("Se guardo la Factura en "+"out/production/TiendaStonks/resources/Reporte/"+txtFactura.getText()+"pdf");
                alert.show();

        }




    }


    public void  reportPDf() throws IOException, DocumentException {
        String reportName = txtFactura.getText() +".pdf";
        File file = new File("out/production/TiendaStonks/resources/Reporte/"+reportName);
        //creacion de documento
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream("out/production/TiendaStonks/resources/Reporte/"+reportName);
        PdfWriter.getInstance(document,outputStream);

        document.open();
        Paragraph titulo = new Paragraph("Factura de compras Tienda Stonks",FontFactory.getFont(FontFactory.TIMES_BOLD,26));
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        Paragraph subTitulo = new Paragraph("Gracias por comprar en nuestra tienda!!",FontFactory.getFont(FontFactory.TIMES_BOLD,20));
        subTitulo.setAlignment(Element.ALIGN_CENTER);
        subTitulo.setSpacingBefore(10);
        subTitulo.setSpacingAfter(20);
        document.add(subTitulo);
        Image image = Image.getInstance("TiendaProject/src/resources/images/LogoTienda (5).png");
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);
        Paragraph date = new Paragraph(new Date().toString());
        date.setAlignment(Element.ALIGN_LEFT);
        date.setSpacingBefore(10);
        date.setSpacingAfter(20);
        document.add(date);
        Paragraph noFactura = new Paragraph("N0 Factura: "+txtFactura.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,18));
        noFactura.setAlignment(Element.ALIGN_LEFT);
        document.add(noFactura);
        Paragraph line = new Paragraph("==========================================================================",FontFactory.getFont(FontFactory.TIMES_BOLD));
        document.add(line);


        PdfPTable tabla = new PdfPTable(5);
        tabla.setSpacingBefore(30);
        tabla.setSpacingAfter(60);
        PdfPCell cellCodigo = new PdfPCell(new Paragraph("Codigo"));
        cellCodigo.setBackgroundColor(BaseColor.MAGENTA);
        PdfPCell cellCantidad = new PdfPCell(new Paragraph("Cantidad"));
        cellCantidad.setBackgroundColor(BaseColor.MAGENTA);
        PdfPCell cellDescripcion = new PdfPCell(new Paragraph("Descripcion"));
        cellDescripcion.setBackgroundColor(BaseColor.MAGENTA);
        PdfPCell cellPrecioUni = new PdfPCell(new Paragraph("Precio Unitario"));
        cellPrecioUni.setBackgroundColor(BaseColor.MAGENTA);
        PdfPCell cellPrecioTotal = new PdfPCell(new Paragraph("Precio Total"));
        cellPrecioTotal.setBackgroundColor(BaseColor.MAGENTA);


        tabla.addCell(cellCodigo);
        tabla.addCell(cellCantidad);
        tabla.addCell(cellDescripcion);
        tabla.addCell(cellPrecioUni);
        tabla.addCell(cellPrecioTotal);

        List<PdfPCell> celdas = new ArrayList<>();

        for (FacturaTableModel f: tblProducto.getItems()) {
            tabla.addCell(f.getCodigo());
            tabla.addCell(String.valueOf(f.getCantidad()));
            tabla.addCell(f.getDescripcion());
            tabla.addCell(String.valueOf(f.getPrecioUni()));
            tabla.addCell(String.valueOf(f.getPrecioTotal()));
        }

        int index=txtTotal.getText().indexOf(".");
        int indexCambio = txtCambio.getText().indexOf(".");
        document.add(tabla);
        Paragraph neto = new Paragraph("Neto Factura: "+txtNeto.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        neto.setAlignment(Element.ALIGN_RIGHT);
        document.add(neto);
        Paragraph iva = new Paragraph("IVA: 15%",FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        iva.setAlignment(Element.ALIGN_RIGHT);
        document.add(iva);
        Paragraph descuento = new Paragraph("Descuento de: "+cmbDescuento.getValue(),FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        Paragraph total = new Paragraph("Total Factura: "+txtTotal.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);
        Paragraph saldo = new Paragraph("Saldo: "+txtSaldoCliente.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        saldo.setAlignment(Element.ALIGN_RIGHT);
        document.add(saldo);
        Paragraph cambio = new Paragraph("Cambio Entregado: "+txtCambio.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,16));
        cambio.setAlignment(Element.ALIGN_RIGHT);
        document.add(cambio);
        Paragraph observaciones = new Paragraph("Observaciones en la Factura: "+txtObservaciones.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,14));
        observaciones.setAlignment(Element.ALIGN_LEFT);
        document.add(observaciones);
        document.close();
        System.out.println("reporte creado con exito");
    }


}


