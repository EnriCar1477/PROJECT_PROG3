package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.DetalleHistorialDAO;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DetalleHistorialDAOImpl implements DetalleHistorialDAO {

    @Override
    public DetalleHistorial load(Integer id) {
        String sql = "SELECT idDetalle, estaCerrada, fechaCierre, notaAclaratoria FROM DetalleHistorial WHERE idDetalle = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DetalleHistorial detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt(1));
                    detalle.setEstaCerrada(rs.getBoolean(2));
                    if(rs.getTimestamp(3) != null) detalle.setFechaCierre(rs.getTimestamp(3).toLocalDateTime());
                    detalle.setNotaAclaratoria(rs.getString(4));
                    return detalle;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DetalleHistorial save(DetalleHistorial t) {
        String sql = "INSERT INTO DetalleHistorial (estaCerrada, fechaCierre, notaAclaratoria) VALUES (?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, t.getEstaCerrada());
            if(t.getFechaCierre() != null) ps.setTimestamp(2, java.sql.Timestamp.valueOf(t.getFechaCierre()));
            else ps.setNull(2, java.sql.Types.TIMESTAMP);
            ps.setString(3, t.getNotaAclaratoria());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdDetalle(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DetalleHistorial update(DetalleHistorial t) {
        String sql = "UPDATE DetalleHistorial SET estaCerrada=?, fechaCierre=?, notaAclaratoria=? WHERE idDetalle=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getEstaCerrada());
            if(t.getFechaCierre() != null) ps.setTimestamp(2, java.sql.Timestamp.valueOf(t.getFechaCierre()));
            else ps.setNull(2, java.sql.Types.TIMESTAMP);
            ps.setString(3, t.getNotaAclaratoria());
            ps.setInt(4, t.getIdDetalle());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(DetalleHistorial t) {
        throw new UnsupportedOperationException("La entidad DetalleHistorial no admite eliminacion (su modelo solo permite cerrarla).");
    }
}
