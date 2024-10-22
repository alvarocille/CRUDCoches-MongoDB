package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import java.util.regex.Pattern;

public class Validador {
    // Metodo para validar que un campo de texto no esté vacío
    public static boolean validarTextoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarMatricula(String matricula) {
        // Expresión regular para el formato NNNNXXX (4 dígitos seguidos de 3 letras)
        String regexMatricula = "^[0-9]{4}[A-Z]{3}$";
        return validarTextoNoVacio(matricula) && Pattern.matches(regexMatricula, matricula);
    }

}
