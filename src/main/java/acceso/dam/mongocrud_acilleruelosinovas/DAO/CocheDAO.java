package acceso.dam.mongocrud_acilleruelosinovas.DAO;

import acceso.dam.mongocrud_acilleruelosinovas.domain.Coche;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

/**
 * Clase Data Access Object (DAO) para gestionar la colección de coches
 * en la base de datos MongoDB.
 *
 * <p>Esta clase proporciona métodos para realizar operaciones CRUD (Crear,
 * Leer, Actualizar, Eliminar) sobre los documentos de la colección "coches".</p>
 */
public class CocheDAO {

    /**
     * Obtiene todos los coches de la colección.
     *
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     * @return una lista de objetos {@link Coche} que representan
     *         todos los coches en la colección.
     */
    public List<Coche> obtenerTodosLosCoches(Session session) {
        Transaction transaction = null;
        List<Coche> coches = null;
        try {
            transaction = session.beginTransaction();
            coches = session.createQuery("from Coche", Coche.class).list();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
        return Objects.requireNonNullElse(coches, Collections.emptyList());

    }

    /**
     * Inserta un nuevo coche en la colección.
     *
     * @param coche el objeto {@link Coche} a insertar en la colección.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void insertCoche(Coche coche, Session session) {
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(coche);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    /**
     * Actualiza un coche existente en la colección.
     *
     * @param coche el nuevo objeto {@link Coche} con los datos actualizados.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void updateCoche(Coche coche, Session session) {
        if (coche == null || session == null) {
            throw new IllegalArgumentException("El coche y la sesión no pueden ser nulos.");
        }
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(coche);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    /**
     * Elimina un coche de la colección.
     *
     * @param id el id del coche {@link Coche} que se desea eliminar.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public void deleteCoche(int id, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Coche coche = session.get(Coche.class, id);
            session.delete(coche);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    /**
     * Obtiene una lista de tipos de coches distintos almacenados en la colección.
     *
     * @return una lista de cadenas que representan los tipos de coches.
     * @param session la sesión de Hibernate usada para interactuar con la base de datos.
     */
    public List<String> getTipos(Session session) {
        List<String> tipos = null;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            tipos = session.createQuery("SELECT DISTINCT tipo FROM Coche WHERE tipo IS NOT NULL AND tipo <> ''", String.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al obtener los tipos: " + e.getMessage());
        }
        return tipos;
    }
}