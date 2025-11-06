package com.juego.howmanyburgers.dao;

import com.juego.howmanyburgers.model.Juego;
import com.juego.howmanyburgers.model.Record;
import com.juego.howmanyburgers.model.User;
import com.juego.howmanyburgers.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class RecordsDAOImpl implements RecordsDAO {
    
    public RecordsDAOImpl() {
    }

    @Override
    public Record crear(Record r) {
        String sql = "INSERT INTO records (id_usuario, id_juego, puntaje, posicion, fecha) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, r.getUsuario().getId());
            pstmt.setLong(2, r.getJuego().getId());
            pstmt.setInt(3, r.getPuntaje());
            pstmt.setInt(4, r.getPosicion());
            pstmt.setTimestamp(5, new Timestamp(r.getFecha().getTime()));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                r.setId(rs.getLong("id"));
                return r;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Record> consultar(Juego j) {
        ArrayList<Record> records = new ArrayList<>();
        String sql = "SELECT r.id, r.id_usuario, r.puntaje, r.fecha, r.posicion, " +
                    "u.login, u.correo " +
                    "FROM records r " +
                    "INNER JOIN usuarios u ON r.id_usuario = u.id " +
                    "WHERE r.id_juego = ? " +
                    "ORDER BY r.puntaje DESC, r.fecha ASC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, j.getId());
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                record.setId(rs.getLong("id"));
                record.setPuntaje(rs.getInt("puntaje"));
                record.setFecha(rs.getTimestamp("fecha"));
                record.setPosicion(rs.getInt("posicion"));
                
                User user = new User();
                user.setId(rs.getLong("id_usuario"));
                user.setUsername(rs.getString("login"));
                user.setEmail(rs.getString("correo"));
                record.setUsuario(user);
                
                record.setJuego(j);
                
                records.add(record);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public Record consultar(Juego j, User u) {
        String sql = "SELECT r.id, r.puntaje, r.fecha, r.posicion " +
                    "FROM records r " +
                    "WHERE r.id_juego = ? AND r.id_usuario = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, j.getId());
            pstmt.setLong(2, u.getId());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Record record = new Record();
                record.setId(rs.getLong("id"));
                record.setPuntaje(rs.getInt("puntaje"));
                record.setFecha(rs.getTimestamp("fecha"));
                record.setPosicion(rs.getInt("posicion"));
                record.setUsuario(u);
                record.setJuego(j);
                return record;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Record consultar(Record r) {
        String sql = "SELECT r.id, r.id_usuario, r.id_juego, r.puntaje, r.fecha, r.posicion, " +
                    "u.login, u.correo, j.nombre as juego_nombre " +
                    "FROM records r " +
                    "INNER JOIN usuarios u ON r.id_usuario = u.id " +
                    "INNER JOIN juegos j ON r.id_juego = j.id " +
                    "WHERE r.id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, r.getId());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Record record = new Record();
                record.setId(rs.getLong("id"));
                record.setPuntaje(rs.getInt("puntaje"));
                record.setFecha(rs.getTimestamp("fecha"));
                record.setPosicion(rs.getInt("posicion"));
                
                User user = new User();
                user.setId(rs.getLong("id_usuario"));
                user.setUsername(rs.getString("login"));
                user.setEmail(rs.getString("correo"));
                record.setUsuario(user);
                
                Juego juego = new Juego();
                juego.setId(rs.getLong("id_juego"));
                juego.setNombre(rs.getString("juego_nombre"));
                record.setJuego(juego);
                
                return record;
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int consultarNumeroDeRecords(Juego j) {
        String sql = "SELECT COUNT(*) as total FROM records WHERE id_juego = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, j.getId());
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public ArrayList<Record> obtenerTop10Records(Juego juego) {
        ArrayList<Record> records = new ArrayList<>();
        String sql = "SELECT r.id, r.id_usuario, r.puntaje, r.fecha, r.posicion, " +
                    "u.login, u.correo " +
                    "FROM records r " +
                    "INNER JOIN usuarios u ON r.id_usuario = u.id " +
                    "WHERE r.id_juego = ? " +
                    "ORDER BY r.puntaje DESC, r.fecha ASC " +
                    "LIMIT 10";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, juego.getId());
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Record record = new Record();
                record.setId(rs.getLong("id"));
                record.setPuntaje(rs.getInt("puntaje"));
                record.setFecha(rs.getTimestamp("fecha"));
                record.setPosicion(rs.getInt("posicion"));
                
                User user = new User();
                user.setId(rs.getLong("id_usuario"));
                user.setUsername(rs.getString("login"));
                user.setEmail(rs.getString("correo"));
                record.setUsuario(user);
                
                record.setJuego(juego);
                
                records.add(record);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public Record actualizar(Record r) {
        String sql = "UPDATE records SET id_usuario = ?, id_juego = ?, puntaje = ?, posicion = ?, fecha = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, r.getUsuario().getId());
            pstmt.setLong(2, r.getJuego().getId());
            pstmt.setInt(3, r.getPuntaje());
            pstmt.setInt(4, r.getPosicion());
            pstmt.setTimestamp(5, new Timestamp(r.getFecha().getTime()));
            pstmt.setLong(6, r.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return r;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
