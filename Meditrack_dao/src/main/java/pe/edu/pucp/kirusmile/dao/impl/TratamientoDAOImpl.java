package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.TratamientoDAO;
import pe.edu.pucp.kirusmile.models.Tratamiento;
import pe.edu.pucp.kirusmile.models.TipoTratamiento;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class TratamientoDAOImpl implements TratamientoDAO {

    @Override
    public Tratamiento load(Integer id) {
        String sql = "SELECT IdTratamiento, tipo, indicaciones, fechaInicio, fechaFin, desactivado FROM Tratamiento WHERE IdTratamiento = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tratamiento tratamiento = new Tratamiento(
                            rs.getInt(1),
                            rs.getString(2) != null ? TipoTratamiento.valueOf(rs.getString(2)) : null,
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getDate(5),
                            rs.getBoolean(6)
                    );
                    return tratamiento;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tratamiento save(Tratamiento t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO Tratamiento (tipo, indicaciones, fechaInicio, fechaFin, desactivado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getTipo() != null ? t.getTipo().name() : null);
            ps.setString(2, t.getIndicaciones());
            if (t.getFechaInicio() != null) ps.setDate(3, new java.sql.Date(t.getFechaInicio().getTime()));
            else ps.setNull(3, java.sql.Types.DATE);
            if (t.getFechaFin() != null) ps.setDate(4, new java.sql.Date(t.getFechaFin().getTime()));
            else ps.setNull(4, java.sql.Types.DATE);
            ps.setBoolean(5, t.getDesactivado());
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setIdTratamiento(rs.getInt(1));
                }
            }
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tratamiento update(Tratamiento t) {
        String sql = "UPDATE Tratamiento SET tipo = ?, indicaciones = ?, fechaInicio = ?, fechaFin = ?, desactivado = ? WHERE IdTratamiento = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getTipo() != null ? t.getTipo().name() : null);
            ps.setString(2, t.getIndicaciones());
            if (t.getFechaInicio() != null) ps.setDate(3, new java.sql.Date(t.getFechaInicio().getTime()));
            else ps.setNull(3, java.sql.Types.DATE);
            if (t.getFechaFin() != null) ps.setDate(4, new java.sql.Date(t.getFechaFin().getTime()));
            else ps.setNull(4, java.sql.Types.DATE);
            ps.setBoolean(5, t.getDesactivado());
            ps.setInt(6, t.getIdTratamiento());
            
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Tratamiento t) {
        t.setDesactivado(true);
        String sql = "UPDATE Tratamiento SET desactivado = ? WHERE IdTratamiento = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getIdTratamiento());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
