package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.EnfermedadCIE10DAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnfermedadCIE10DAOImpl implements EnfermedadCIE10DAO {

    @Override
    public EnfermedadCIE10 load(Integer id) {
        String sql = "SELECT codigoCIE, descripcionOficial FROM EnfermedadCIE10 WHERE codigoCIE = id";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(id));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    EnfermedadCIE10 enf = new EnfermedadCIE10(
                            rs.getString(1), rs.getString(2)
                    );
                    return enf;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EnfermedadCIE10 save(EnfermedadCIE10 t) {
        String sql = "INSERT INTO EnfermedadCIE10 (codigoCIE, descripcionOficial) VALUES (?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getCodigocCIE()); ps.setString(2, t.getDescripcionOficial());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EnfermedadCIE10 update(EnfermedadCIE10 t) {
        String sql = "UPDATE EnfermedadCIE10 SET codigoCIE = ?, descripcionOficial=? WHERE codigoCIE=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getCodigocCIE()); ps.setString(2, t.getDescripcionOficial());
            ps.setString(3, t.getCodigocCIE());

            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(EnfermedadCIE10 t) {
        String sql = "DELETE FROM Empleado  WHERE codigoCIE = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getCodigocCIE());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
