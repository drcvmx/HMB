package com.juego.howmanyburgers.repository;

import com.juego.howmanyburgers.dao.RecordsDAO;
import com.juego.howmanyburgers.dao.RecordsDAOImpl;
import com.juego.howmanyburgers.dao.UsuariosDAO;
import com.juego.howmanyburgers.dao.UsuarioDAOImpl;
import com.juego.howmanyburgers.model.Juego;
import com.juego.howmanyburgers.model.Record;
import com.juego.howmanyburgers.model.User;

import java.util.ArrayList;

public class UserRepository {
    // Instancias de los DAOs para interactuar con la base de datos
    private static final UsuariosDAO usuariosDAO = new UsuarioDAOImpl();
    private static final RecordsDAO recordsDAO = new RecordsDAOImpl();
    private static Juego howManyBurgersGame; // Para almacenar la referencia al juego

    // Constructor privado para evitar instanciación
    private UserRepository() {
        // Inicializar el objeto Juego al inicio de la aplicación
        // En una aplicación real, esto se haría en un listener de ServletContext
        // Por simplicidad, lo haremos aquí. Asumimos que el ID del juego "HowManyBurgers" es 1.
        // Si no existe, deberíamos manejarlo, pero por ahora asumimos que el database.sql lo inserta.
        howManyBurgersGame = new Juego(1L, "HowManyBurgers"); // Asumimos ID 1 y nombre
        // En una implementación más robusta, se consultaría la BD para obtener el Juego por nombre
        // Pero para este ejemplo, dado que el ID 1 ya está insertado, podemos usarlo directamente.
    }

    // Este bloque estático se ejecuta una vez cuando la clase es cargada
    static {
        // Inicializa el juego. Podríamos buscarlo en la base de datos,
        // pero por ahora asumimos que el ID del juego HowManyBurgers es 1.
        howManyBurgersGame = new Juego(1L, "HowManyBurgers");
        howManyBurgersGame.setDescripcion("Juego de adivinanza de hamburguesas");
    }


    public static boolean addUser(User user) {
        // Antes de añadir, verificamos si ya existe por nombre de usuario
        if (findUserByUsername(user.getUsername()) != null) {
            return false; // El usuario ya existe
        }
        // Usamos el DAO para crear el usuario en la base de datos
        User createdUser = usuariosDAO.crear(user);
        return createdUser != null;
    }

    public static User findUserByUsername(String username) {
        // Usamos el DAO para buscar el usuario por nombre de usuario
        User userFilter = new User();
        userFilter.setUsername(username);
        ArrayList<User> users = usuariosDAO.consultar(userFilter);
        if (users != null && !users.isEmpty()) {
            return users.get(0); // Devolvemos el primer usuario encontrado
        }
        return null;
    }

    public static void saveUserRecord(String username, Record newRecord) {
        User user = findUserByUsername(username);
        if (user == null) {
            System.err.println("Error: No se puede guardar el récord. Usuario no encontrado: " + username);
            return;
        }

        // Establecer el usuario y el juego en el récord
        newRecord.setUsuario(user);
        newRecord.setJuego(howManyBurgersGame);

        // Consultar el récord existente del usuario para este juego
        Record existingRecord = recordsDAO.consultar(howManyBurgersGame, user);

        if (existingRecord == null) {
            // No existe récord, creamos uno nuevo
            recordsDAO.crear(newRecord);
        } else {
            // Existe un récord, lo actualizamos si el nuevo es mejor
            if (newRecord.getPuntaje() < existingRecord.getPuntaje()) {
                existingRecord.setPuntaje(newRecord.getPuntaje());
                existingRecord.setFecha(newRecord.getFecha());
                recordsDAO.actualizar(existingRecord);
            }
        }
    }

    public static Record getUserRecord(String username) {
        User user = findUserByUsername(username);
        if (user == null) {
            return null; // Usuario no encontrado
        }
        // Usamos el DAO para obtener el récord del usuario para este juego
        return recordsDAO.consultar(howManyBurgersGame, user);
    }

    // Nuevo método para que el JuegoServlet pueda obtener el objeto Juego
    public static Juego getHowManyBurgersGame() {
        return howManyBurgersGame;
    }

    public static ArrayList<Record> getTop10Records() {
        return recordsDAO.obtenerTop10Records(howManyBurgersGame);
    }
}
