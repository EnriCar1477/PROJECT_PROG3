package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.DiagnosticoDAO;
import pe.edu.pucp.kirusmile.models.Diagnostico;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DiagnosticoDAOImpl implements DiagnosticoDAO {

    @Override
    public Diagnostico load(Integer id) {
        String sql = "SELECT idDiagnostico, tipo, observaciones, fechaHoraRegistro, desactivado FROM Diagnostico WHERE idDiagnostico = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Diagnostico(
                        rs.getInt(1), rs.getString(2), rs.getString(3), null, 
                        rs.getTimestamp(4) != null ? rs.getTimestamp(4).toLocalDateTime() : null, rs.getBoolean(5)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Diagnostico save(Diagnostico t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO Diagnostico (tipo, observaciones, fechaHoraRegistro, desactivado) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getObservaciones());
            if(t.getFechaHoraRegistro() != null) ps.setTimestamp(3, java.sql.Timestamp.valueOf(t.getFechaHoraRegistro()));
            else ps.setNull(3, java.sql.Types.TIMESTAMP);
            ps.setBoolean(4, t.getDesactivado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdDiagnostico(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Diagnostico update(Diagnostico t) {
        String sql = "UPDATE Diagnostico SET tipo=?, observaciones=?, fechaHoraRegistro=?, desactivado=? WHERE idDiagnostico=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getTipo());
            ps.setString(2, t.getObservaciones());
            if(t.getFechaHoraRegistro() != null) ps.setTimestamp(3, java.sql.Timestamp.valueOf(t.getFechaHoraRegistro()));
            else ps.setNull(3, java.sql.Types.TIMESTAMP);
            ps.setBoolean(4, t.getDesactivado());
            ps.setInt(5, t.getIdDiagnostico());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Diagnostico t) {
        t.setDesactivado(true);
        String sql = "UPDATE Diagnostico SET desactivado = ? WHERE idDiagnostico = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getIdDiagnostico());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
