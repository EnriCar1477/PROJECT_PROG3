package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.PacienteDAO;
import pe.edu.pucp.kirusmile.models.Paciente;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteDAOImpl implements PacienteDAO {

    @Override
    public Paciente load(String dni) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, estado, tieneSeguro FROM Paciente WHERE dni = ?";
        
        // Uso de try-with-resources para garantizar el cierre
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, dni);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Accediendo a través de índices numéricos de izquierda a derecha
                    Paciente paciente = new Paciente(
                            rs.getString(1),  // dni
                            rs.getString(2),  // nombres
                            rs.getString(3),  // apellidoPaterno
                            rs.getString(4),  // apellidoMaterno
                            rs.getDate(5),    // fechaNacimiento
                            rs.getString(6),  // telefono
                            rs.getString(7),  // correo
                            rs.getString(8),  // estado
                            rs.getBoolean(9)  // tieneSeguro
                    );
                    return paciente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente save(Paciente t) {
        String sql = "INSERT INTO Paciente (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, estado, tieneSeguro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombres());
            ps.setString(3, t.getApellidoPaterno());
            ps.setString(4, t.getApellidoMaterno());
            
            // Convertimos java.util.Date a java.sql.Date
            if (t.getFechaNacimiento() != null) {
                ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            
            ps.setString(6, t.getTelefono());
            ps.setString(7, t.getCorreo());
            ps.setString(8, t.getEstado());
            ps.setBoolean(9, t.getTieneSeguro());
            
            ps.executeUpdate();
            return t; 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente update(Paciente t) {
        String sql = "UPDATE Paciente SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, telefono = ?, correo = ?, estado = ?, tieneSeguro = ? " +
                     "WHERE dni = ?";
                     
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, t.getNombres());
            ps.setString(2, t.getApellidoPaterno());
            ps.setString(3, t.getApellidoMaterno());
            
            if (t.getFechaNacimiento() != null) {
                ps.setDate(4, new java.sql.Date(t.getFechaNacimiento().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            ps.setString(5, t.getTelefono());
            ps.setString(6, t.getCorreo());
            ps.setString(7, t.getEstado());
            ps.setBoolean(8, t.getTieneSeguro());
            
            // El DNI va en el WHERE (el índice 9)
            ps.setString(9, t.getDni());
            
            ps.executeUpdate();
            return t;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Paciente t) {
        String sql = "DELETE FROM Paciente WHERE dni = ?";
        
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, t.getDni());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
