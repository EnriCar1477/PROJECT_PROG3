package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.HistorialMedicoDAO;
import pe.edu.pucp.kirusmile.models.HistorialMedico;
import pe.edu.pucp.kirusmile.models.Paciente;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistorialMedicoDAOImpl implements HistorialMedicoDAO {

    @Override
    public HistorialMedico load(Integer id) {
        String sql = "SELECT idHistorial, observaciones, id_paciente FROM HistorialMedico WHERE idHistorial = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(null,null,null,null,null,null,null,rs.getInt(3),null,false,false);
                    return new HistorialMedico(rs.getInt(1), rs.getString(2), paciente, new ArrayList<>());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HistorialMedico save(HistorialMedico t) {
        String sql = "INSERT INTO HistorialMedico (observaciones, id_paciente) VALUES (?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getObservaciones());
            ps.setObject(2, t.getPaciente() != null ? t.getPaciente().getId() : null);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdHistorial(rs.getInt(1));
            }
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HistorialMedico update(HistorialMedico t) {
        String sql = "UPDATE HistorialMedico SET observaciones = ?, id_paciente = ? WHERE idHistorial = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getObservaciones());
            ps.setObject(2, t.getPaciente() != null ? t.getPaciente().getId() : null);
            ps.setInt(3, t.getIdHostorial()); // Typo en modelo getIdHostorial()
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(HistorialMedico t) {
        throw new UnsupportedOperationException("Error: La entidad HistorialMedico no admite eliminacion (ni logica ni fisica).");
    }
}
