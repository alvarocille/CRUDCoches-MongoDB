package acceso.dam.mongocrud_acilleruelosinovas.Utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.InputStream;
import java.util.Properties;

public class DBManager {

    public static MongoClient conectar() {
        try {
            System.out.println("Conectando a la base de datos...");

            // Cargar las propiedades desde el archivo
            Properties configuration = new Properties();
            InputStream input = R.getProperties("database.properties");
            configuration.load(input);

            // Extraer propiedades
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String database = configuration.getProperty("name"); // En MongoDB se llama "database"
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");

            // Construir la URI de conexión para MongoDB
            String uri = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + database + "?authSource=admin";

            // Crear la conexión con MongoDB
            final MongoClient conexion = MongoClients.create(uri);

            System.out.println("Conectado correctamente a la BD");

        } catch (Exception e) {
            System.out.println("Conexión fallida");
            System.out.println(e);
        }
        return null;
    }

    public static void desconectar(MongoClient conexion) {
        if (conexion != null) {
            conexion.close();
            conexion = null;
        }
    }
}



