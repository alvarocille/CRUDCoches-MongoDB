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

/**
 * La clase {@code App} es la clase principal de la aplicación JavaFX, encargada de inicializar
 * y mostrar la interfaz gráfica de usuario (GUI) para gestionar un CRUD de coches.
 * <p>
 * Utiliza {@link AppController} como el controlador de la aplicación,
 * y el archivo FXML para definir la interfaz de usuario.
 * </p>
 * @author alvaro.cilsin
 */
public class App extends Application {

    /**
     * Metodo principal que configura y muestra la ventana de la aplicación. Carga el archivo FXML,
     * asigna el controlador y muestra la GUI en el escenario principal.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws Exception si ocurre un error al cargar el archivo FXML o durante la inicialización del controlador.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Crear una instancia del controlador y pasarle el manejador de la base de datos.
        AppController controller = new AppController();

        // Cargar la interfaz gráfica desde el archivo FXML.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("coches.fxml"));
        loader.setController(controller);  // Asignar el controlador a la vista.
        VBox vbox = loader.load();  // Cargar el diseño de la interfaz desde el archivo FXML.

        // Crear la escena con el layout cargado.
        Scene scene = new Scene(vbox);
        stage.setScene(scene);  // Asignar la escena al escenario.
        stage.setTitle("CRUD Coches");  // Establecer el título de la ventana.

        // Establecer un ícono para la ventana.
        try {
            Image icono = new Image(Objects.requireNonNull(R.getImage("coche.png")));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("No se ha podido cargar el icono de la aplicación.");
        }

        // Mostrar la ventana.
        stage.show();
    }

    /**
     * Metodo principal de ejecución de la aplicación. Este metodo es el punto de entrada
     * cuando se ejecuta desde la línea de comandos.
     */
    public static void main(String[] args) {
        // Lanza la aplicación JavaFX.
        launch();
    }
}
