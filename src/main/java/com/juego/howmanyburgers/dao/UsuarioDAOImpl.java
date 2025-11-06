package com.juego.howmanyburgers.dao;

import com.juego.howmanyburgers.model.User;
import com.juego.howmanyburgers.util.DBUtil; // Importamos nuestra clase de utilidad de conexión
import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAOImpl implements UsuariosDAO { // Ya no es abstracta
    // Ya no usamos DataSource, la conexión se obtiene directamente de DBUtil
    
    // Constructor ya no necesita inicializar DataSource
    public UsuarioDAOImpl() {
    }

    @Override
    public User crear(User u) { // Cambiamos de Usuario a User
        String sql = "INSERT INTO usuarios (login, password, correo) VALUES (?, ?, ?) RETURNING id";
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Ejecutando INSERT para usuario: " + u.getUsername());
            System.out.println("SQL: " + sql);
            
            pstmt.setString(1, u.getUsername()); // Usamos getUsername
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getEmail()); // Usamos getEmail
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                u.setId(rs.getLong("id"));
                System.out.println("Usuario " + u.getUsername() + " creado con ID: " + u.getId());
                return u;
            }
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error al crear usuario " + u.getUsername() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<User> consultar() { // Cambiamos de Usuario a User
        ArrayList<User> usuarios = new ArrayList<>();
        String sql = "SELECT id, login, password, correo, fecha_registro FROM usuarios";
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User(); // Cambiamos a User
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("login")); // Usamos setUsername
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("correo")); // Usamos setEmail
                usuarios.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public ArrayList<User> consultar(User u) { // Cambiamos de Usuario a User
        ArrayList<User> usuarios = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT id, login, password, correo, fecha_registro FROM usuarios WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        
        if (u.getUsername() != null) { // Usamos getUsername
            sql.append(" AND login = ?");
            params.add(u.getUsername());
        }
        if (u.getEmail() != null) { // Usamos getEmail
            sql.append(" AND correo = ?");
            params.add(u.getEmail());
        }
        if (u.getId() != 0) {
            sql.append(" AND id = ?");
            params.add(u.getId());
        }
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(); // Cambiamos a User
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("login")); // Usamos setUsername
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("correo")); // Usamos setEmail
                usuarios.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public User modificar(User u) { // Cambiamos de Usuario a User
        String sql = "UPDATE usuarios SET login = ?, password = ?, correo = ? WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, u.getUsername()); // Usamos getUsername
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getEmail()); // Usamos getEmail
            pstmt.setLong(4, u.getId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return u;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int borrar(User u) { // Cambiamos de Usuario a User
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, u.getId());
            return pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public User autenticar(String login, String password) { // Cambiamos de Usuario a User
        String sql = "SELECT id, login, password, correo, fecha_registro FROM usuarios WHERE login = ? AND password = ?";
        
        try (Connection conn = DBUtil.getConnection(); // Usamos DBUtil
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(); // Cambiamos a User
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("login")); // Usamos setUsername
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("correo")); // Usamos setEmail
                return user;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
