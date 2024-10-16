package acceso.dam.mongocrud_acilleruelosinovas.Controller;

import acceso.dam.mongocrud_acilleruelosinovas.DAO.CocheDAO;
import acceso.dam.mongocrud_acilleruelosinovas.domain.Coche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class AppController {
    @FXML
    private Button nuevoButton, modificarButton, eliminarButton, limpiarButton;
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

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cargarCoches();
    }

    @FXML
    public void crearNuevo(ActionEvent event) {
        Coche coche = new Coche(matriculaField.getText(), marcaField.getText(), modeloField.getText(), tipoComboBox.getValue());
        cargarCoches();
    }

    @FXML
    void actualizarCambios(ActionEvent event) {

    }

    @FXML
    void eliminarCoche(ActionEvent event) {

    }

    @FXML
    void limpiarDatos(ActionEvent event) {
        matriculaField.clear();
        marcaField.clear();
        modeloField.clear();
        tipoComboBox.getItems().clear();
    }

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