package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import java.util.regex.Pattern;

/**
 * La clase {@code Validador} proporciona métodos estáticos para validar campos de texto y formatos específicos
 * en la aplicación.
 */
public class Validador {

    /**
     * Constructor predeterminado para la clase {@code Validador}.
     * Privado para evitar que su instancia, ya que solo contiene métodos estáticos.
     */
    private Validador() {}

    /**
     * Valida que un campo de texto no esté vacío.
     *
     * @param texto el texto a validar.
     * @return {@code true} si el texto no es {@code null} y no está vacío (después de eliminar espacios en blanco);
     *         {@code false} en caso contrario.
     */
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Valida el formato de una matrícula de vehículo.
     * El formato esperado es NNNNXXX, donde N es un dígito y X es una letra.
     *
     * @param matricula la matrícula a validar.
     * @return {@code true} si la matrícula no está vacía y coincide con el formato requerido;
     *         {@code false} en caso contrario.
     */
    public static boolean validarMatricula(String matricula) {
        // Expresión regular para el formato NNNNXXX (4 dígitos seguidos de 3 letras)
        String regexMatricula = "^[0-9]{4}[A-Z]{3}$";
        return validarTextoNoVacio(matricula) && Pattern.matches(regexMatricula, matricula);
    }
}