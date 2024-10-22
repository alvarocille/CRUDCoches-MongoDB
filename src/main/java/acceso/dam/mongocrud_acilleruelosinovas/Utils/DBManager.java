package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.InputStream;
import java.util.Properties;

/**
 * La clase {@code DBManager} gestiona la conexión a la base de datos MongoDB.
 */
public class DBManager {
    private MongoClient conexion;

    /**
     * Constructor que inicializa {@code DBManager}.
     *
     */
    public DBManager() {
    }

    /**
     * Establece una conexión con la base de datos MongoDB utilizando las propiedades definidas en el archivo
     * {@code database.properties}.
     *
     * <p>Este metodo carga las propiedades de conexión desde el archivo de configuración, construye el URI de
     * conexión y establece la conexión a la base de datos. Si la conexión falla, se imprime un mensaje de error
     * en la consola.</p>
     */
    public void conectar() {
        try {
            System.out.println("Conectando a la base de datos...");

            // Cargar las propiedades desde el archivo
            Properties configuration = new Properties();
            InputStream input = R.getProperties("database.properties");
            configuration.load(input);

            // Extraer propiedades
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");

            // Crear el URI de conexión
            String uri = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=admin";

            // Crear la conexión con MongoDB
            conexion = MongoClients.create(uri);
            System.out.println("Conectado correctamente a la BD");

        } catch (Exception e) {
            System.out.println("Conexión fallida: " + e);
        }
    }

    /**
     * Cierra la conexión con la base de datos MongoDB.
     *
     * <p>Este metodo verifica si la conexión está activa y, si es así, cierra la conexión.</p>
     */
    public void desconectar() {
        if (conexion != null) {
            conexion.close();
        }
    }

    /**
     * Obtiene la conexion a la base de datos.
     *
     * @return la conexion a la base de datos.
     */
    public MongoClient getConexion() {
        return conexion;
    }
}