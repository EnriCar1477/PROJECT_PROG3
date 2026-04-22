package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.PacienteDAO;
import pe.edu.pucp.kirusmile.models.Paciente;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PacienteDAOImpl implements PacienteDAO {

    @Override
    public Paciente load(Integer id) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, id, estado, tieneSeguro, desactivado FROM Paciente WHERE id = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getDate(5), rs.getString(6), rs.getString(7), rs.getInt(8),
                            rs.getString(9), rs.getBoolean(10), rs.getBoolean(11)
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
        t.setDesactivado(false);
        String sql = "INSERT INTO Paciente (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, estado, tieneSeguro, desactivado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo()); ps.setString(8, t.getEstado());
            ps.setBoolean(9, t.getTieneSeguro()); ps.setBoolean(10, t.getDesactivado());
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setId(rs.getInt(1));
            }
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente update(Paciente t) {
        String sql = "UPDATE Paciente SET dni=?, nombres=?, apellidoPaterno=?, apellidoMaterno=?, fechaNacimiento=?, telefono=?, correo=?, estado=?, tieneSeguro=?, desactivado=? WHERE id=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo()); ps.setString(8, t.getEstado());
            ps.setBoolean(9, t.getTieneSeguro()); ps.setBoolean(10, t.getDesactivado());
            ps.setInt(11, t.getId());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Paciente t) {
        t.setDesactivado(true);
        String sql = "UPDATE Paciente SET desactivado = ? WHERE id = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
