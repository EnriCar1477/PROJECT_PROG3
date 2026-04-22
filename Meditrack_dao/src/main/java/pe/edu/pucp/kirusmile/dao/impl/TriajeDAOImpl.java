package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.TriajeDAO;
import pe.edu.pucp.kirusmile.models.Triaje;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TriajeDAOImpl implements TriajeDAO {

    @Override
    public Triaje load(Integer id) {
        String sql = "SELECT idTriaje, talla, peso, presionArterial, temperatura, saturacion, desactivado FROM Triaje WHERE idTriaje = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Triaje(
                        rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getBoolean(7)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Triaje save(Triaje t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO Triaje (talla, peso, presionArterial, temperatura, saturacion, desactivado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, t.getTalla());
            ps.setDouble(2, t.getPeso());
            ps.setString(3, t.getPresionArterial());
            ps.setDouble(4, t.getTemperatura());
            ps.setDouble(5, t.getSaturacion());
            ps.setBoolean(6, t.getDesactivado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdTriaje(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Triaje update(Triaje t) {
        String sql = "UPDATE Triaje SET talla=?, peso=?, presionArterial=?, temperatura=?, saturacion=?, desactivado=? WHERE idTriaje=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, t.getTalla());
            ps.setDouble(2, t.getPeso());
            ps.setString(3, t.getPresionArterial());
            ps.setDouble(4, t.getTemperatura());
            ps.setDouble(5, t.getSaturacion());
            ps.setBoolean(6, t.getDesactivado());
            ps.setInt(7, t.getIdTriaje());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Triaje t) {
        t.setDesactivado(true);
        String sql = "UPDATE Triaje SET desactivado = ? WHERE idTriaje = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getIdTriaje());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
