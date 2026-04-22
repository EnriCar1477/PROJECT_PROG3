package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.CitaMedicaDAO;
import pe.edu.pucp.kirusmile.models.CitaMedica;
import pe.edu.pucp.kirusmile.models.EstadoCita;
import pe.edu.pucp.kirusmile.models.Medico;
import pe.edu.pucp.kirusmile.models.Paciente;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class CitaMedicaDAOImpl implements CitaMedicaDAO {

    @Override
    public CitaMedica load(Integer id) {
        String sql = "SELECT idCita, fechaCita, horaInicio, horaFin, id_paciente, id_medico, estadoCita, desactivado FROM CitaMedica WHERE idCita = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(rs.getString(5), null, null, null, null, null, null, null, null, null, null, null);
                    Medico medico = new Medico(null, null, null, null, null, null, null, rs.getString(6), null, null, null, null, null, false);

                    CitaMedica cita = new CitaMedica(
                            rs.getInt(1),
                            rs.getDate(2),
                            rs.getTime(3) != null ? rs.getTime(3).toLocalTime() : null,
                            rs.getTime(4) != null ? rs.getTime(4).toLocalTime() : null,
                            paciente,
                            medico,
                            rs.getString(7),
                            rs.getBoolean(8));
                    return cita;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CitaMedica save(CitaMedica t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO CitaMedica (fechaCita, horaInicio, horaFin, id_paciente, id_medico, estadoCita, desactivado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (t.getFechaCita() != null)
                ps.setDate(1, new java.sql.Date(t.getFechaCita().getTime()));
            else
                ps.setNull(1, java.sql.Types.DATE);
            if (t.getHoraInicio() != null)
                ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else
                ps.setNull(2, java.sql.Types.TIME);
            if (t.getHoraFin() != null)
                ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else
                ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getPaciente() != null ? t.getPaciente().getDni() : null);
            ps.setString(5, t.getDoctor() != null ? t.getDoctor().getCmp() : null);
            ps.setString(6, t.getEstadoCita());
            ps.setBoolean(7, t.getDesactivado());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    t.setIdCita(rs.getInt(1)); 
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CitaMedica update(CitaMedica t) {
        String sql = "UPDATE CitaMedica SET fechaCita=?, horaInicio=?, horaFin=?, id_paciente=?, id_medico=?, estadoCita=?, desactivado=? WHERE idCita = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (t.getFechaCita() != null)
                ps.setDate(1, new java.sql.Date(t.getFechaCita().getTime()));
            else
                ps.setNull(1, java.sql.Types.DATE);
            if (t.getHoraInicio() != null)
                ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else
                ps.setNull(2, java.sql.Types.TIME);
            if (t.getHoraFin() != null)
                ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else
                ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getPaciente() != null ? t.getPaciente().getDni() : null);
            ps.setString(5, t.getDoctor() != null ? t.getDoctor().getCmp() : null);
            ps.setString(6, t.getEstadoCita());
            ps.setBoolean(7, t.getDesactivado());
            ps.setInt(8, t.getIdCita());

            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(CitaMedica t) {
        t.setDesactivado(true);
        String sql = "UPDATE CitaMedica SET desactivado = ? WHERE idCita = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setInt(2, t.getIdCita());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
