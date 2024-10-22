package acceso.dam.mongocrud_acilleruelosinovas.Controller;

import acceso.dam.mongocrud_acilleruelosinovas.DAO.CocheDAO;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.AlertUtils;
import acceso.dam.mongocrud_acilleruelosinovas.domain.Coche;
import com.mongodb.MongoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

import static acceso.dam.mongocrud_acilleruelosinovas.Utils.AlertUtils.*;
import static acceso.dam.mongocrud_acilleruelosinovas.Utils.Validador.validarMatricula;
import static acceso.dam.mongocrud_acilleruelosinovas.Utils.Validador.validarTextoNoVacio;

/**
 * Controlador de la aplicación encargado de gestionar las acciones relacionadas
 * con los coches. Incluye la gestión de la interfaz de usuario, así como la
 * interacción con la base de datos mediante {@link CocheDAO}.
 */
public class AppController {
    @FXML
    private Button nuevoButton, guardarButton, modificarButton, eliminarButton, limpiarButton;
    @FXML
    private TextField matriculaField, marcaField, modeloField;
    @FXML
    private TableView<Coche> tablaCoches;
    @FXML
    private TableColumn<Coche, String> colMatricula;
    @FXML
    private TableColumn<Coche, String> colMarca;
    @FXML
    private TableColumn<Coche, String> colModelo;
    @FXML
    private TableColumn<Coche, String> colTipo;
    @FXML
    private ComboBox<String> tipoComboBox;

    private final CocheDAO cocheDAO = new CocheDAO();
    private Coche cocheSeleccionado;
    private boolean editando = false;

    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    /**
     * Inicializa los componentes de la interfaz de usuario y carga los datos
     * necesarios, como la lista de coches y los tipos de vehículos disponibles.
     */
    @FXML
    public void initialize() {
        modoEdicion(editando);
        configurarColumnas();
        cargarCoches();
        cargarTipos();
    }

    /**
     * Limpia los campos de entrada de la interfaz de usuario, restableciendo su estado inicial.
     */
    @FXML
    void limpiarDatos() {
        matriculaField.clear();
        marcaField.clear();
        modeloField.clear();
        tipoComboBox.getSelectionModel().select(null);
    }

    /**
     * Carga la lista de coches desde la base de datos y la muestra en la tabla.
     * Si no hay coches, genera datos de ejemplo.
     */
    public void cargarCoches() {
        try {
            ObservableList<Coche> coches = FXCollections.observableArrayList(cocheDAO.obtenerTodosLosCoches());
            if (coches.isEmpty()) {
                generarDatos();
                cargarCoches();
            } else {
                tablaCoches.setItems(coches);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los coches: " + e.getMessage());
        }
    }

    /**
     * Configura las columnas de la tabla de coches.
     */
    private void configurarColumnas() {
        colMatricula.prefWidthProperty().bind(tablaCoches.widthProperty().multiply(0.25));
        colMarca.prefWidthProperty().bind(tablaCoches.widthProperty().multiply(0.25));
        colModelo.prefWidthProperty().bind(tablaCoches.widthProperty().multiply(0.25));
        colTipo.prefWidthProperty().bind(tablaCoches.widthProperty().multiply(0.245));

        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    /**
     * Carga los tipos de vehículos disponibles desde la base de datos en el ComboBox.
     */
    private void cargarTipos() {
        try {
            List<String> tipos = cocheDAO.getTipos();
            tipoComboBox.getItems().clear();
            tipoComboBox.getItems().addAll(tipos);
        } catch (Exception e) {
            System.out.println("Error al rellenar el ComboBox: " + e.getMessage());
        }
    }

    /**
     * Muestra el coche que el usuario ha seleccionado en la tabla.
     *
     * @param event Evento de selección.
     */
    public void seleccionarCoche(Event event) {
        cocheSeleccionado = tablaCoches.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            cargarCoche(cocheSeleccionado);
        }
    }

    /**
     * Carga los datos de un coche seleccionado en los campos de entrada.
     *
     * @param cocheSeleccionado El coche seleccionado.
     */
    private void cargarCoche(Coche cocheSeleccionado) {
        matriculaField.setText(cocheSeleccionado.getMatricula());
        marcaField.setText(cocheSeleccionado.getMarca());
        modeloField.setText(cocheSeleccionado.getModelo());
        tipoComboBox.setValue(cocheSeleccionado.getTipo());
    }

    /**
     * Habilita el modo de edición para crear un nuevo coche.
     *
     * @param event Evento de acción.
     */
    @FXML
    public void crearNuevo(ActionEvent event) {
        accion = Accion.NUEVO;
        limpiarDatos();
        modoEdicion(!editando);
        editando = !editando;
    }

    /**
     * Habilita el modo de edición para modificar un coche existente.
     *
     * @param event Evento de acción.
     */
    @FXML
    void actualizarCambios(ActionEvent event) {
        accion = Accion.MODIFICAR;
        modoEdicion(!editando);
        editando = !editando;
    }

    /**
     * Elimina el coche seleccionado de la base de datos.
     *
     * @param event Evento de acción.
     */
    @FXML
    void eliminarCoche(ActionEvent event) {
        Coche coche = tablaCoches.getSelectionModel().getSelectedItem();
        if (coche == null) {
            mostrarError("No hay coche seleccionado");
            return;
        }

        Optional<ButtonType> respuesta = pedirConfirmacion("Eliminar coche: ¿Estás seguro?");
        if (respuesta.isPresent()) {
            if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;
        }

        cocheDAO.deleteCoche(coche);
        mostrarConfirmacion("Coche eliminado con éxito");
        cargarCoches();
        limpiarDatos();
        cargarTipos();
    }

    /**
     * Activa o desactiva el modo de edición en la interfaz de usuario.
     *
     * @param activar True para activar el modo de edición, false para desactivarlo.
     */
    private void modoEdicion(boolean activar) {
        if (!activar || accion == Accion.MODIFICAR) {
            nuevoButton.setDisable(activar);
        }
        if (!activar || accion == Accion.NUEVO) {
            modificarButton.setDisable(activar);
        }

        guardarButton.setDisable(!activar);
        eliminarButton.setDisable(activar);

        matriculaField.setEditable(activar);
        marcaField.setEditable(activar);
        modeloField.setEditable(activar);
        tipoComboBox.setEditable(activar);

        tablaCoches.setDisable(activar);
    }

    /**
     * Guarda un coche nuevo o actualizado en la base de datos.
     *
     * @param event Evento de acción.
     */
    @FXML
    public void guardarCoche(Event event) {
        String matricula = matriculaField.getText();
        if (!validarTextoNoVacio(matricula)) {
            mostrarError("La matricula es un campo obligatorio.");
            return;
        } else if (!validarMatricula(matricula)) {
            mostrarError("La matricula debe de tener formato 'NNNNXXX' siendo N un dígito y X una letra.");
            return;
        }

        String marca = marcaField.getText();
        if (!validarTextoNoVacio(marca)) {
            mostrarError("La marca es un campo obligatorio.");
            return;
        }

        String modelo = modeloField.getText();
        if (!validarTextoNoVacio(modelo)) {
            mostrarAviso("Se va a registrar el coche sin modelo.");
        }

        String tipo = tipoComboBox.getSelectionModel().getSelectedItem();
        if (!validarTextoNoVacio(tipo)) {
            mostrarAviso("Se va a registrar el coche sin tipo.");
        }

        Coche coche = new Coche(matricula, marca, modelo, tipo);

        try {
            switch (accion) {
                case NUEVO:
                    cocheDAO.insertCoche(coche);
                    break;
                case MODIFICAR:
                    cocheDAO.updateCoche(cocheSeleccionado, coche);
                    break;
            }
            mostrarConfirmacion("Coche guardado con éxito");
            cargarCoches();
            limpiarDatos();
            cargarTipos();
            modoEdicion(false);
        } catch (MongoException e) {
            AlertUtils.mostrarError("Error al guardar el coche: " + e.getMessage());
        }
    }

    /**
     * Genera datos de ejemplo en la base de datos de coches.
     */
    @FXML
    public void generarDatos() {
        try {
            Coche coche1 = new Coche("1122BBC", "Renault", "Clio", "Familiar");
            Coche coche2 = new Coche("2233DDB", "Seat", "Ibiza", "Familiar");
            Coche coche3 = new Coche("3344FFC", "Seat", "Leon", "Deportivo");
            Coche coche4 = new Coche("4455BBZ", "Ford", "S-Max", "Monovolumen");
            Coche coche5 = new Coche("5566CCR", "Ford", "Kuga", "SUV");
            Coche coche6 = new Coche("6677FFD", "Nissan", "Micra", "Familiar");
            Coche coche7 = new Coche("7788JJZ", "Volkswagen", "Passat", "Familiar");
            Coche coche8 = new Coche("8899BBV", "Volkswagen", "T-ROC", "SUV");
            Coche coche9 = new Coche("9911FFG", "Volkswagen", "Touran", "Monovolumen");
            Coche coche10 = new Coche("8855GFR", "Renault", "Scenic", "Monovolumen");

            cocheDAO.insertCoche(coche1);
            cocheDAO.insertCoche(coche2);
            cocheDAO.insertCoche(coche3);
            cocheDAO.insertCoche(coche4);
            cocheDAO.insertCoche(coche5);
            cocheDAO.insertCoche(coche6);
            cocheDAO.insertCoche(coche7);
            cocheDAO.insertCoche(coche8);
            cocheDAO.insertCoche(coche9);
            cocheDAO.insertCoche(coche10);
        } catch (Exception e) {
            System.err.println("Error al insertar los coches: " + e.getMessage());
        }
    }
}