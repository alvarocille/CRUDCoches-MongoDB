package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * La clase {@code R} proporciona métodos estáticos para acceder a recursos en la aplicación,
 * incluyendo imágenes, archivos de propiedades y elementos de la interfaz de usuario.
 */
public class R {

    /**
     * Constructor predeterminado para la clase {@code R}.
     * Privado para evitar que su instancia, ya que solo contiene métodos estáticos.
     */
    private R() {}

    /**
     * Obtiene un {@code InputStream} para una imagen específica ubicada en el directorio de imágenes.
     *
     * @param name el nombre del archivo de imagen que se desea obtener.
     * @return un {@code InputStream} que representa la imagen solicitada,
     *         o {@code null} si no se encuentra el recurso.
     */
    public static InputStream getImage(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("images" + File.separator + name);
    }

    /**
     * Obtiene un {@code InputStream} para un archivo de propiedades específico ubicado en el directorio de configuración.
     *
     * @param name el nombre del archivo de propiedades que se desea obtener.
     * @return un {@code InputStream} que representa el archivo de propiedades solicitado,
     *         o {@code null} si no se encuentra el recurso.
     */
    public static InputStream getProperties(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration" + File.separator + name);
    }

    /**
     * Obtiene una {@code URL} para un recurso de interfaz de usuario específico ubicado en el directorio de interfaz.
     *
     * @param name el nombre del recurso de interfaz que se desea obtener.
     * @return una {@code URL} que representa el recurso solicitado,
     *         o {@code null} si no se encuentra el recurso.
     */
    public static URL getUI(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("ui" + File.separator + name);
    }

    /**
     * Obtiene una {@code URL} para el archivo de configuración de Hibernate ubicado en su directorio.
     *
     * @param name el nombre del recurso de Hibernate a obtener.
     * @return una {@code URL} que representa el recurso solicitado,
     *         o {@code null} si no se encuentra el recurso.
     */
    public static URL getHibernate(String name) {
        return Thread.currentThread().getContextClassLoader().getResource("configuration" + File.separator + name);
    }
}