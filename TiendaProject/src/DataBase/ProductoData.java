package DataBase;

import Pojo.Producto;
import Pojo.Usuario;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ProductoData {
    public  ObservableList<Producto> productos;

    public ProductoData() {

    }

    public void loadFromGson() {
        Gson gson = new Gson();
        productos = FXCollections.observableArrayList();

        try {
            productos.addAll(Arrays.asList(gson.fromJson(new FileReader(getClass().getResource("/resources/Data/ProductoData.json").getPath()), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToGson(ObservableList<Usuario> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter(getClass().getResource("/resources/Data/ProductoData.json").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Usuario> jsonArray = newData;
        gson.toJson(jsonArray, flw);

        try {
            flw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Producto> getProductos() {

        return productos;
    }


}
