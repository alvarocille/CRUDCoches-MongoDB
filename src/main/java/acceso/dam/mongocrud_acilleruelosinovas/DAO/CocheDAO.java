package acceso.dam.mongocrud_acilleruelosinovas.DAO;

import acceso.dam.mongocrud_acilleruelosinovas.Utils.DBManager;
import acceso.dam.mongocrud_acilleruelosinovas.domain.Coche;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class CocheDAO {
    private MongoCollection<Document> coches;

    public CocheDAO() {
        try {
            MongoClient conDB = DBManager.conectar();
            MongoDatabase db = conDB.getDatabase("taller");
            db.createCollection("coches");
            this.coches = db.getCollection("coches");
        } catch (Exception e) {
            System.err.println("Error al inicializar CRUDCoche: " + e.getMessage());
        }
    }

    public List<Coche> obtenerTodosLosCoches() {
        List<Coche> cochesList = new ArrayList<>();
        if (this.coches != null) {
            try (MongoCursor<Document> cursor = this.coches.find().iterator()) {
                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    Coche coche = new Coche(doc.getString("matricula"),
                            doc.getString("marca"),
                            doc.getString("modelo"),
                            doc.getString("tipo"));
                    cochesList.add(coche);
                }
            } catch (Exception e) {
                System.err.println("Error al obtener los coches: " + e.getMessage());
            }
        }
        return cochesList;
    }

    public void insertCoche(Coche coche) {
        try {
            Document doc = new Document("matricula", coche.getMatricula())
                    .append("marca", coche.getMarca())
                    .append("modelo", coche.getModelo())
                    .append("tipo", coche.getTipo());
            coches.insertOne(doc);
        } catch (Exception e) {
            System.err.println("Error al insertar el coche: " + e.getMessage());
        }
    }

    public void updateCoche(Coche anteriorCoche, Coche nuevoCoche) {
        try {
            Bson filter = eq("matricula", anteriorCoche.getMatricula());
            Bson updateMatricula = set("matricula", nuevoCoche.getMatricula());
            Bson updateMarca = set("marca", nuevoCoche.getMarca());
            Bson updateModelo = set("modelo", nuevoCoche.getModelo());
            Bson updateTipo = set("tipo", nuevoCoche.getTipo());

            coches.updateOne(filter, updateMarca);
            coches.updateOne(filter, updateModelo);
            coches.updateOne(filter, updateTipo);
            coches.updateOne(filter, updateMatricula);
        } catch (Exception e) {
            System.err.println("Error al actualizar el coche: " + e.getMessage());
        }
    }

    public void deleteCoche(Coche coche) {
        try {
            coches.deleteOne(eq("matricula", coche.getMatricula()));
        } catch (Exception e) {
            System.err.println("Error al eliminar el coche: " + e.getMessage());
        }
    }

    public Coche getCocheByMatricula(String matricula) {
        try {
            Document doc = coches.find(eq("matricula", matricula)).first();
            if (doc != null) {
                return new Coche(doc.getString("matricula"), doc.getString("marca"), doc.getString("modelo"), doc.getString("tipo"));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el coche: " + e.getMessage());
        }
        return null;
    }

    public List<String> getTipos() {
        List<String> tipos = new ArrayList<>();
        try {
            MongoCursor<String> cursor = coches.distinct("tipo", String.class).iterator();
            while (cursor.hasNext()) {
                tipos.add(cursor.next());
            }
            cursor.close();
        } catch (Exception e) {
            System.err.println("Error al obtener los tipos: " + e.getMessage());
        }
        return tipos;
    }
}
