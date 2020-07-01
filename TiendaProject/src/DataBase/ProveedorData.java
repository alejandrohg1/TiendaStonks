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

    public static ObservableList<Proveedor> proveedores;

    public ProveedorData() {
        loadFromGson();
    }

    public void loadFromGson() {
        Gson gson = new Gson();
        proveedores = FXCollections.observableArrayList();

        try {
            proveedores.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/ProveedorData.json"), Proveedor[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToGson(ObservableList<Usuario> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/resources/Data/ProveedorData.json");
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


}
