package acceso.dam.mongocrud_acilleruelosinovas;

import acceso.dam.mongocrud_acilleruelosinovas.Controller.AppController;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AppController controller = new AppController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("coches.fxml"));
        loader.setController(controller);
        VBox vbox = loader.load();

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("CRUD Coches");
        Image icono = new Image(Objects.requireNonNull(R.getImage("coche.png")));
        stage.getIcons().add(icono);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}