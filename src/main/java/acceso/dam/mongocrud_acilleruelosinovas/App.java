package acceso.dam.mongocrud_acilleruelosinovas;

import acceso.dam.mongocrud_acilleruelosinovas.Controller.AppController;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.DBManager;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.R;
import com.mongodb.client.MongoClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

/**
 * La clase {@code App} es la clase principal de la aplicación JavaFX.
 * Inicializa y muestra la interfaz gráfica de usuario para gestionar un CRUD de coches.
 */
public class App extends Application {
    private DBManager bd = new DBManager();
    private MongoClient conexion;

    /**
     * Metodo de inicialización de la aplicación.
     *
     * @throws Exception si ocurre un error durante la inicialización.
     */
    @Override
    public void init() throws Exception {
        super.init();
        bd.conectar();
    }

    /**
     * Metodo principal que configura y muestra la ventana de la aplicación.
     *
     * @param stage el escenario principal de la aplicación.
     * @throws Exception si ocurre un error al cargar la interfaz.
     */
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

        stage.setOnCloseRequest((WindowEvent _) -> bd.desconectar());
    }

    /**
     * Metodo principal de ejecución de la aplicación.
     *
     * @param args los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}