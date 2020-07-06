package DataBase;

import Pojo.Factura;
import Pojo.Producto;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FacturaData {

    public ObservableList<Factura> facturas;

    public FacturaData() {
    }

    public void loadFromGson() {
        Gson gson = new Gson();
        facturas = FXCollections.observableArrayList();

        try {
            facturas.addAll(Arrays.asList(gson.fromJson(new FileReader(getClass().getResource("/resources/Data/FacturaData.json").getPath()), Factura[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToGson(ObservableList<Factura> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter(getClass().getResource("/resources/Data/FacturaData.json").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Factura> jsonArray = newData;
        gson.toJson(jsonArray, flw);

        try {
            flw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Factura> getFacturas() {
        return facturas;
    }

    public void updateFacturaList(){
        addToGson(facturas);
    }
}
