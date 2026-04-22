package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.EspecialidadDAO;
import pe.edu.pucp.kirusmile.models.Especialidad;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class EspecialidadDAOImpl implements EspecialidadDAO {

    @Override
    public Especialidad load(Integer id) {
        String sql = "SELECT idEspecialidad, nombreEspecialidad, costoEspecialidad, activo FROM Especialidad WHERE idEspecialidad = ? AND activo = 1";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Especialidad(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getBoolean(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Especialidad save(Especialidad t) {
        t.setActivo(true);
        String sql = "INSERT INTO Especialidad (nombreEspecialidad, costoEspecialidad, activo) VALUES (?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombreEspecialidad());
            ps.setDouble(2, t.getCostoEspecialidad());
            ps.setBoolean(3, t.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdEspecialidad(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Especialidad update(Especialidad t) {
        String sql = "UPDATE Especialidad SET nombreEspecialidad=?, costoEspecialidad=?, activo=? WHERE idEspecialidad=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombreEspecialidad());
            ps.setDouble(2, t.getCostoEspecialidad());
            ps.setBoolean(3, t.isActivo());
            ps.setInt(4, t.getIdEspecialidad());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Especialidad t) {
        t.setActivo(false); // SOFT DELETE (Lógica)
        String sql = "UPDATE Especialidad SET activo = ? WHERE idEspecialidad = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.isActivo());
            ps.setInt(2, t.getIdEspecialidad());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
