package DataBase;

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

public class ProveedorData {

    public  ObservableList<Proveedor> proveedores;

    public ProveedorData() {
        proveedores = FXCollections.observableArrayList();
    }
    
    public static ObservableList<Proveedor> getProveedor() throws FileNotFoundException{
       if(proveedores.isEmpty()){
           loadFromGson();
       }
       return proveedores;
   }

    public static void loadFromGson() {
        Gson gson = new Gson();
        proveedores = FXCollections.observableArrayList();

        try {
            proveedores.addAll(Arrays.asList(gson.fromJson(new FileReader(getClass().getResource("/resources/Data/ProveedorData.json").getPath()), Proveedor[].class)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addToGson(ObservableList<Proveedor> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter(getClass().getResource("/resources/Data/ProveedorData.json").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Proveedor> jsonArray = newData;
        gson.toJson(jsonArray, flw);

        try {
            flw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateProveedorList(){
        addToGson(proveedores);
    }

    public ObservableList<Proveedor> getProveedores() {
        return proveedores;
    }
}
