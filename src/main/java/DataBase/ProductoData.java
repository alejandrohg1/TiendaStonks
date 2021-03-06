package DataBase;

import Pojo.Producto;
import Pojo.Proveedor;
import Pojo.Usuario;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProductoData {

    public  ObservableList<Producto> productos;

    public ProductoData() {
        productos = FXCollections.observableArrayList();
    }

    public ObservableList<Producto> getProducto() throws FileNotFoundException{
        if(productos.isEmpty()){
            loadFromGson();
        }
        return productos;
    }

    public void loadFromGson() {
        Gson gson = new Gson();
        productos = FXCollections.observableArrayList();

        try {
            productos.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/main/resources/Data/ProductoData.json"), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToGson(ObservableList<Producto> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/main/resources/Data/ProductoData.json");
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

    public List<Producto> getProductsAsList() {
        Gson gson = new Gson();
        List<Producto> products = null;

        try {
            products = (Arrays.asList(gson.fromJson(new FileReader("./src/main/resources/Data/ProductoData.json"), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }
    public ObservableList<Producto> getProductos() { return productos; }

    public void updateProductList(){
        addToGson(productos);
    }

    public void saveProducto(Producto p) {
        productos.add(p);
        addToGson(productos);
    }
}
