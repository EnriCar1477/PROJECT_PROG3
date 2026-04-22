package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.AnamnesisDAO;
import pe.edu.pucp.kirusmile.models.Anamnesis;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class AnamnesisDAOImpl implements AnamnesisDAO {

    @Override
    public Anamnesis load(Integer id) {
        String sql = "SELECT idAnamnesis, motivoPrincipal, tiempoEnfermedad, formaInicio, relatoClinico, antecedentesImportantes, desactivado FROM Anamnesis WHERE idAnamnesis = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Anamnesis(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Anamnesis save(Anamnesis t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO Anamnesis (motivoPrincipal, tiempoEnfermedad, formaInicio, relatoClinico, antecedentesImportantes, desactivado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getMotivoPrincipal());
            ps.setString(2, t.getTiempoEnfermedad());
            ps.setString(3, t.getFormaInicio());
            ps.setString(4, t.getRelatoClinico());
            ps.setString(5, t.getAntecedentesImportantes());
            ps.setBoolean(6, t.getDesactivado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdAnamnesis(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Anamnesis update(Anamnesis t) {
        String sql = "UPDATE Anamnesis SET motivoPrincipal=?, tiempoEnfermedad=?, formaInicio=?, relatoClinico=?, antecedentesImportantes=?, desactivado=? WHERE idAnamnesis=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getMotivoPrincipal());
            ps.setString(2, t.getTiempoEnfermedad());
            ps.setString(3, t.getFormaInicio());
            ps.setString(4, t.getRelatoClinico());
            ps.setString(5, t.getAntecedentesImportantes());
            ps.setBoolean(6, t.getDesactivado());
            ps.setInt(7, t.getIdAnamnesis());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Anamnesis t) {
        t.setDesactivado(true);
        String sql = "UPDATE Anamnesis SET desactivado = ? WHERE idAnamnesis = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getIdAnamnesis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
