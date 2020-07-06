package DataBase;

import Pojo.Producto;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ProductoData {
    public static ObservableList<Producto> productos;

    public ProductoData() {
        loadFromGson();
    }
    
    public static ObservableList<Producto> getProductos() throws FileNotFoundException{
       if(productos.isEmpty()){
           loadFromGson();
       }
       return productos;
   }
    
    public static void loadFromGson() {
        Gson gson = new Gson();
        productos = FXCollections.observableArrayList();

        try {
            productos.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/productos.json"), Producto[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void addToGson(ObservableList<Producto> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/resources/Data/productos.json");
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
    
    
    public static void saveUser(Producto p) {
        productos.add(p);
        addToGson(productos);

    }
    
    
}
