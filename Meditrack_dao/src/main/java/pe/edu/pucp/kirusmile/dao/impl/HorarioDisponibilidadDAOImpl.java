package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.HorarioDisponibilidadDAO;
import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class HorarioDisponibilidadDAOImpl implements HorarioDisponibilidadDAO {

    @Override
    public HorarioDisponibilidad load(Integer id) {
        String sql = "SELECT idHorario, fechaEspecifica, horaInicio, horaFin, estado FROM HorarioDisponibilidad WHERE idHorario = ? AND estado != 'INACTIVO'";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new HorarioDisponibilidad(
                        rs.getInt(1), rs.getDate(2),
                        rs.getTime(3) != null ? rs.getTime(3).toLocalTime() : null,
                        rs.getTime(4) != null ? rs.getTime(4).toLocalTime() : null,
                        rs.getString(5)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HorarioDisponibilidad save(HorarioDisponibilidad t) {
        String sql = "INSERT INTO HorarioDisponibilidad (fechaEspecifica, horaInicio, horaFin, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if(t.getFechaEspecifica() != null) ps.setDate(1, new java.sql.Date(t.getFechaEspecifica().getTime()));
            else ps.setNull(1, java.sql.Types.DATE);
            if(t.getHoraInicio() != null) ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else ps.setNull(2, java.sql.Types.TIME);
            if(t.getHoraFin() != null) ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdHorario(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HorarioDisponibilidad update(HorarioDisponibilidad t) {
        String sql = "UPDATE HorarioDisponibilidad SET fechaEspecifica=?, horaInicio=?, horaFin=?, estado=? WHERE idHorario=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if(t.getFechaEspecifica() != null) ps.setDate(1, new java.sql.Date(t.getFechaEspecifica().getTime()));
            else ps.setNull(1, java.sql.Types.DATE);
            if(t.getHoraInicio() != null) ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else ps.setNull(2, java.sql.Types.TIME);
            if(t.getHoraFin() != null) ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getEstado());
            ps.setInt(5, t.getIdHorario());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(HorarioDisponibilidad t) {
        t.setEstado("INACTIVO"); // SOFT DELETE (Lógica)
        String sql = "UPDATE HorarioDisponibilidad SET estado = ? WHERE idHorario = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getEstado());
            ps.setInt(2, t.getIdHorario());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
