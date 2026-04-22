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
    public Paciente load(Integer dni) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, grupoSanguineo, factorRh, gradoInstruccion, ocupacion, etnia FROM Paciente WHERE dni = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(dni));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8),
                            rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)
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
        String sql = "INSERT INTO Paciente (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, grupoSanguineo, factorRh, gradoInstruccion, ocupacion, etnia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo()); ps.setString(8, t.getGrupoSanguineo());
            ps.setString(9, t.getFactorRh()); ps.setString(10, t.getGradoInstruccion()); ps.setString(11,t.getOcupacion()); ps.setString(12,t.getEtnia());
            
            ps.executeUpdate();
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paciente update(Paciente t) {
        String sql = "UPDATE Paciente SET dni=?, nombres=?, apellidoPaterno=?, apellidoMaterno=?, fechaNacimiento=?, telefono=?, correo=?, grupoSanguineo=?, factorRh=?, gradoInstruccion=? , ocupacion=?, etnia=? WHERE dni=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo()); ps.setString(8, t.getGrupoSanguineo());
            ps.setString(9, t.getFactorRh()); ps.setString(10, t.getGradoInstruccion()); ps.setString(11,t.getOcupacion());
            ps.setString(12, t.getEtnia()); ps.setString(13,t.getDni());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Paciente t) {
        String sql = "DELETE Paciente WHERE dni = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
