package Controller;

import Pojo.Usuario;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.plugin.javascript.navig.Anchor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UsuarioController implements Initializable {
    @FXML
    private ImageView userImage;
    @FXML
    private TableView<Usuario> tableUser;
    @FXML
    private TableColumn<Usuario,Integer> IdColumn;
    @FXML
    private TableColumn<Usuario,String> nameColumn;
    @FXML
    private TableColumn<Usuario,String> secondNameColumn;
    @FXML
    private TableColumn<Usuario,String> cedulaColumn;
    @FXML
    private TableColumn<Usuario,String> rolColumn;
    @FXML
    private TableColumn<Usuario,String> emailColumn;
    @FXML
    private AnchorPane anchorPaneMain;

    public  static ObservableList<Usuario> usuarioObservableList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFromGson();
            starColumns();
            loadData();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //


    }


    public void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public  void loadFromGson() {
        Gson gson = new Gson();
        usuarioObservableList = FXCollections.observableArrayList();

        try {
            usuarioObservableList.addAll(Arrays.asList(gson.fromJson(new FileReader("./src/resources/Data/usuarios.json"), Usuario[].class)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<Usuario> getUsuarioObservableList() throws FileNotFoundException {
        return usuarioObservableList;
    }

    public void starColumns() throws FileNotFoundException {

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        IdColumn.setMinWidth(50);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nameColumn.setMinWidth(200);
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        secondNameColumn.setMinWidth(200);
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        cedulaColumn.setMinWidth(200);
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        rolColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMinWidth(200);


    }

    public  void addToGson(ObservableList<Usuario> newData) {
        FileWriter flw = null;

        Gson gson = new Gson();

        try {
            flw = new FileWriter("./src/resources/Data/usuarios.json");
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

    public void listPrueba(){
        usuarioObservableList = FXCollections.observableArrayList();
        usuarioObservableList.add(new Usuario(1,"Alejandro","Hernandez","Cedula","fotoURL","rol","email","username","password"));
        tableUser.setItems(usuarioObservableList);
    }


    public void registrarEmpleado(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/RegisterUser.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.setTitle("Registrar Usuarios");
        stage.show();
    }

    public  void saveUser(Usuario user){
        usuarioObservableList.add(user);
        addToGson(usuarioObservableList);

    }

    public void loadData(){
        tableUser.setItems(usuarioObservableList);
    }


    public void setImageView(MouseEvent event) {
       String foto = tableUser.getSelectionModel().getSelectedItem().getFotoUrl();
        userImage.setImage(new Image(foto));

    }


    public void editUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/EditUser.fxml"));
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPaneMain.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.getDialogPane().setContent(loader.load());
        //dialog.initStyle(StageStyle.UNIFIED);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        EditController editController = loader.getController();
        editController.initData(tableUser.getSelectionModel().getSelectedItem());

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            editController.setUser(tableUser.getSelectionModel().getSelectedItem());
            updateContact();
        }

        tableUser.refresh();


    }



    public TableView<Usuario> getTableUser() {
        return tableUser;
    }

    public void updateContact(){
        addToGson(usuarioObservableList);
    }


    public void deleteUser(ActionEvent event) {
        if(tableUser.getSelectionModel().getSelectedItem()==null){
            return;
        }else{
            usuarioObservableList.remove(tableUser.getSelectionModel().getSelectedItem());
            updateContact();
        }

    }
}
