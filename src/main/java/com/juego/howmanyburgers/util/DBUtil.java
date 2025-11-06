package com.juego.howmanyburgers.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    // Ya no necesitamos URL, USER, PASSWORD aquí, ya que el DataSource lo gestionará
    private static DataSource dataSource; // Instancia del pool de conexiones

    // Bloque estático para inicializar el DataSource una vez
    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // El nombre del recurso DataSource debe coincidir con la configuración de Tomcat
            dataSource = (DataSource) envContext.lookup("jdbc/HowManyBurgersDB"); 
            System.out.println("DataSource 'jdbc/HowManyBurgersDB' inicializado correctamente.");
        } catch (NamingException e) {
            System.err.println("Error al inicializar el DataSource JNDI: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar el pool de conexiones.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // Obtenemos una conexión del pool
        System.out.println("Intentando obtener una conexión del pool.");
        return dataSource.getConnection();
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión del pool liberada.");
            } catch (SQLException e) {
                System.err.println("Error al liberar la conexión del pool: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
