package acceso.dam.mongocrud_acilleruelosinovas.Controller;

import acceso.dam.mongocrud_acilleruelosinovas.DAO.CocheDAO;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.AlertUtils;
import acceso.dam.mongocrud_acilleruelosinovas.Utils.HibernateUtil;
import acceso.dam.mongocrud_acilleruelosinovas.domain.Coche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

import static acceso.dam.mongocrud_acilleruelosinovas.Utils.AlertUtils.*;
import static acceso.dam.mongocrud_acilleruelosinovas.Utils.Validador.*;

/**
 * El controlador principal de la aplicación que gestiona la interacción de la interfaz de usuario
 * con los coches, así como las operaciones CRUD sobre la base de datos MongoDB mediante {@link CocheDAO}.
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

    SessionFactory factory = HibernateUtil.getSessionFactory();
    Session session = HibernateUtil.getSession();
    private final CocheDAO cocheDAO;
    private Coche cocheSeleccionado;
    private boolean editando = false;
    private boolean ejemplo = true; // Decide si CREA (true) o NO (false) datos de ejemplo

    /**
     * Constructor del controlador, que inicializa la instancia de {@link CocheDAO}
     * para interactuar con la base de datos.
     */
    public AppController() {
        cocheDAO = new CocheDAO();
    }

    private enum Accion {
        NUEVO, MODIFICAR
    }
    private Accion accion;

    /**
     * Inicializa la interfaz de usuario, configurando las columnas de la tabla,
     * cargando los coches y los tipos de vehículos.
     */
    @FXML
    public void initialize() {
        modoEdicion(editando);
        configurarColumnas();
        cargarCoches();
        cargarTipos();
    }

    /**
     * Limpia los campos de entrada de texto y restablece el ComboBox, devolviéndolos a su estado inicial.
     */
    @FXML
    void limpiarDatos() {
        matriculaField.clear();
        marcaField.clear();
        modeloField.clear();
        tipoComboBox.getSelectionModel().select(null);
    }

    /**
     * Carga la lista de coches desde la base de datos y los muestra en la tabla.
     * Si no hay coches, genera datos de ejemplo.
     */
    public void cargarCoches() {
        try {
            session.clear();
            ObservableList<Coche> coches = FXCollections.observableArrayList(cocheDAO.obtenerTodosLosCoches(session));
            if (coches.isEmpty() && ejemplo) {
                this.ejemplo = false;
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
     * Configura las columnas de la tabla para mostrar las propiedades de los coches.
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
     * Carga los tipos de vehículos desde la base de datos en el ComboBox correspondiente.
     */
    private void cargarTipos() {
        try {
            List<String> tipos = cocheDAO.getTipos(session);
            tipoComboBox.getItems().clear();
            tipoComboBox.getItems().addAll(tipos);
        } catch (Exception e) {
            System.out.println("Error al rellenar el ComboBox: " + e.getMessage());
        }
    }

    /**
     * Muestra los detalles del coche seleccionado en los campos de texto.
     *
     * @param event Evento de selección de coche en la tabla.
     */
    public void seleccionarCoche(Event event) {
        cocheSeleccionado = tablaCoches.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            cargarCoche(cocheSeleccionado);
        }
    }

    /**
     * Carga los datos del coche seleccionado en los campos de entrada.
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
     * @param event Evento de acción para crear un nuevo coche.
     */
    @FXML
    public void crearNuevo(ActionEvent event) {
        accion = Accion.NUEVO;
        limpiarDatos();
        modoEdicion(!editando);
        editando = !editando;
    }

    /**
     * Habilita el modo de edición para modificar los detalles del coche seleccionado.
     *
     * @param event Evento de acción para modificar un coche.
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
     * @param event Evento de acción para eliminar un coche.
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

        cocheDAO.deleteCoche(coche.getId(), session);
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
     * Valida y guarda los datos del coche, ya sea uno nuevo o uno modificado, en la base de datos.
     *
     * @param event Evento de acción para guardar un coche.
     */
    @FXML
    public void guardarCoche(Event event) {
        int id = cocheSeleccionado != null && accion == Accion.MODIFICAR ? cocheSeleccionado.getId() : 0;

        String matricula = matriculaField.getText();
        if (!validarTextoNoVacio(matricula)) {
            mostrarError("La matrícula es un campo obligatorio.");
            return;
        } else if (!validarMatricula(matricula)) {
            mostrarError("La matrícula debe tener el formato 'NNNNXXX', donde N es un dígito y X es una letra.");
            return;
        }

        if ((!cocheSeleccionado.getMatricula().equals(matricula) && accion == Accion.MODIFICAR) || Accion.NUEVO == accion) {
            if (validarCocheExistente(matricula, session)) {
                mostrarError("La matrícula introducida ya existe en la base de datos.");
                return;
            }
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

        Coche coche = new Coche(id, matricula, marca, modelo, tipo);

        try {
            switch (accion) {
                case NUEVO -> cocheDAO.insertCoche(coche, session);
                case MODIFICAR -> cocheDAO.updateCoche(coche, session);
            }
        } catch (Exception e) {
            AlertUtils.mostrarError("Error al guardar el coche: " + e.getMessage());
        }

        modoEdicion(false);
        cargarCoches();
    }

    /**
     * Genera datos de ejemplo de coches para la tabla.
     */
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

            cocheDAO.insertCoche(coche1, session);
            cocheDAO.insertCoche(coche2, session);
            cocheDAO.insertCoche(coche3, session);
            cocheDAO.insertCoche(coche4, session);
            cocheDAO.insertCoche(coche5, session);
            cocheDAO.insertCoche(coche6, session);
            cocheDAO.insertCoche(coche7, session);
            cocheDAO.insertCoche(coche8, session);
            cocheDAO.insertCoche(coche9, session);
            cocheDAO.insertCoche(coche10, session);
        } catch (Exception e) {
            System.err.println("Error al insertar los coches: " + e.getMessage());
        }
    }

}