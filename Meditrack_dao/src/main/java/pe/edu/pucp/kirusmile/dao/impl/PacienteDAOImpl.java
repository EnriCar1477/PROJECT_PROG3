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
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, grupoSanguineo, factorRh, gradoInstruccion, ocupacion, etnia FROM Paciente WHERE dni = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente save(Paciente t) {
        String sql = "INSERT INTO Paciente (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, grupoSanguineo, factorRh, gradoInstruccion, ocupacion, etnia, desactivado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombres());
            ps.setString(3, t.getApellidoPaterno());
            ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono());
            ps.setString(7, t.getCorreo());
            ps.setString(8, t.getGrupoSanguineo());
            ps.setString(9, t.getFactorRh());
            ps.setString(10, t.getGradoInstruccion());
            ps.setString(11, t.getOcupacion());
            ps.setString(12, t.getEtnia());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente update(Paciente t) {
        String sql = "UPDATE Paciente SET nombres=?, apellidoPaterno=?, apellidoMaterno=?, fechaNacimiento=?, telefono=?, correo=?, grupoSanguineo=?, factorRh=?, gradoInstruccion=?, ocupacion=?, etnia=? WHERE dni=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombres());
            ps.setString(2, t.getApellidoPaterno());
            ps.setString(3, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(4, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(4, java.sql.Types.DATE);
            ps.setString(5, t.getTelefono());
            ps.setString(6, t.getCorreo());
            ps.setString(7, t.getGrupoSanguineo());
            ps.setString(8, t.getFactorRh());
            ps.setString(9, t.getGradoInstruccion());
            ps.setString(10, t.getOcupacion());
            ps.setString(11, t.getEtnia());
            ps.setString(12, t.getDni());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Paciente t) {
        // En lugar de llamar a setDesactivado(true) que ya no existe en el modelo, 
        // lo hacemos directamente con SQL para proteger los datos (Soft Delete).
        String sql = "UPDATE Paciente SET desactivado = 1 WHERE dni = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
