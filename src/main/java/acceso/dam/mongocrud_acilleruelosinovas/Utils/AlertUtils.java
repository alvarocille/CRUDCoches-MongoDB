package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

/**
 * La clase {@code AlertUtils} proporciona métodos estáticos para mostrar diferentes tipos de alertas en la aplicación.
 */
public class AlertUtils {

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param mensaje el mensaje de error que se mostrará en la alerta.
     */
    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error.");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    /**
     * Muestra una alerta de confirmación con el mensaje proporcionado.
     *
     * @param mensaje el mensaje de confirmación que se mostrará en la alerta.
     */
    public static void mostrarConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Confirmación.");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();

        ImageView iconoOriginal = (ImageView) alerta.getDialogPane().getGraphic();
        double ancho = iconoOriginal.getImage().getWidth();
        double altura = iconoOriginal.getImage().getHeight();

        Image iconoTick = new Image(Objects.requireNonNull(R.getImage("iconoVerificado.png")));
        ImageView imageView = new ImageView(iconoTick);
        imageView.setFitWidth(ancho);
        imageView.setFitHeight(altura);
        imageView.setPreserveRatio(true);

        alerta.setGraphic(imageView);
        alerta.getButtonTypes().setAll(ButtonType.OK);

        alerta.show();
    }

    /**
     * Pide confirmación al usuario con el mensaje proporcionado.
     *
     * @param mensaje el mensaje que se mostrará en la alerta de confirmación.
     * @return un {@code Optional<ButtonType>} que contiene la respuesta del usuario.
     */
    public static Optional<ButtonType> pedirConfirmacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(mensaje);
        return alerta.showAndWait();
    }

    /**
     * Muestra un aviso con el mensaje proporcionado.
     *
     * @param mensaje el mensaje del aviso que se mostrará en la alerta.
     */
    public static void mostrarAviso(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso.");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}