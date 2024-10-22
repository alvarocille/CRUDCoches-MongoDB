package acceso.dam.mongocrud_acilleruelosinovas.domain;

/**
 * Clase que representa un coche con atributos como matrícula, marca, modelo y tipo.
 * Coincide con la colección coches de la base de datos.
 *
 * <p>Esta clase proporciona métodos para acceder y modificar los atributos de un coche.</p>
 */
public class Coche {

    private String matricula;
    private String marca;
    private String modelo;
    private String tipo;

    /**
     * Constructor que inicializa un objeto {@link Coche} con los valores especificados.
     *
     * @param matricula la matrícula del coche.
     * @param marca     la marca del coche.
     * @param modelo    el modelo del coche.
     * @param tipo      el tipo del coche (por ejemplo, SUV, familiar, etc.).
     */
    public Coche(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    /**
     * Obtiene la matrícula del coche.
     *
     * @return la matrícula del coche.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del coche.
     *
     * @param matricula la nueva matrícula del coche.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene la marca del coche.
     *
     * @return la marca del coche.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del coche.
     *
     * @param marca la nueva marca del coche.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene el modelo del coche.
     *
     * @return el modelo del coche.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo del coche.
     *
     * @param modelo el nuevo modelo del coche.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el tipo del coche.
     *
     * @return el tipo del coche.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del coche.
     *
     * @param tipo el nuevo tipo del coche.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve una representación en forma de cadena del coche,
     * que consiste en la matrícula junto a la marca y el modelo, añade el tipo.
     *
     * @return una cadena que representa el coche.
     */
    @Override
    public String toString() {
        return matricula + " - " + marca + " " + modelo + " (" + tipo + ")";
    }
}