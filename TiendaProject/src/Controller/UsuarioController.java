package Controller;


import Pojo.Usuario;
import animatefx.animation.FadeIn;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

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
    @FXML
    public Label lblWelcome;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnEditar,btnEliminar,buttonRegistrar;
    @FXML
    private ImageView imgPerfil;

    public static String name;
    public static String foto;
    public static String rols;
    public  static ObservableList<Usuario> usuarioObservableList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFromGson();
            starColumns();
            loadData();
            lblWelcome.setText(name);
            imgPerfil.setImage(new Image(foto));

            if(!rols.equals("Admin")){
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
                buttonRegistrar.setDisable(true);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    //cerrar ventana
    public void closeWindow(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    //cargar gson
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
    //inicializar el dato de cada columna a recibir
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
    //añadir al gson
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

    //abre el stage de registrar empleado
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
    //guarda usuario y actualiza el gson
    public  void saveUser(Usuario user){
        usuarioObservableList.add(user);
        addToGson(usuarioObservableList);

    }
    //carga la lista observable a la tabla
    public void loadData(){
        tableUser.setItems(usuarioObservableList);
    }


    //Pone imagen al seleccionar dato de la tabla
    public void setImageView(MouseEvent event) {
        if(tableUser.getSelectionModel().getSelectedItem()==null){
            return;
        }else{
            String foto = tableUser.getSelectionModel().getSelectedItem().getFotoUrl();
            userImage.setImage(new Image(foto));
        }


    }

    //editar usuario
    public void editUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/EditUser.fxml"));
        //crea un dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(anchorPaneMain.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        //carga el fxml de edit
        dialog.getDialogPane().setContent(loader.load());
        //crea boton ok y cancelar
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //llama al controlador editController
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
    //actualizar el gson
    public void updateContact(){
        addToGson(usuarioObservableList);
    }

    //borra usuario y actualiza gson
    public void deleteUser(ActionEvent event) {
        if(tableUser.getSelectionModel().getSelectedItem()==null){
            return;
        }else{
            usuarioObservableList.remove(tableUser.getSelectionModel().getSelectedItem());
            updateContact();
        }

    }
    //pone en la etiqueta username el nombre del usuario
    public void setLblWelcome(String username,String photo,String rol) {
        name = username;
        foto = photo;
        rols = rol;
    }
    //busca en la tabla el escrito en el texfield
    public void findName(ActionEvent event) {

        String key = txtBuscar.getText();
        ObservableList tempList = FXCollections.observableArrayList();
        //crea una nueva lista para agregarla a la table

        for (Usuario u : usuarioObservableList) {
            if (u.getNombre().equals(key) || u.getApellido().equals(key) || u.getCedula().equals(key) || u.getEmail().equals(key) || u.getRol().equals(key) ) {
                tempList.add(u);
            }
        }
        //agrega la lista temporal a la tabla
        tableUser.setItems(tempList);
        buttonRegistrar.setDisable(true);
    }
    //regresa la tabla a su estado inicial
    public void populateTable(MouseEvent event) throws FileNotFoundException {
        if(!rols.equals("Admin")){
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            buttonRegistrar.setDisable(true);
        }else{
            buttonRegistrar.setDisable(false);
            btnEditar.setDisable(false);
            btnEliminar.setDisable(false);
        }

        loadFromGson();
        starColumns();
        loadData();
    }


    public void backToLogin(ActionEvent event) throws IOException {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("resources/images/iconTienda.png"));
        stage.show();
        new FadeIn(root).play();

    }

}
