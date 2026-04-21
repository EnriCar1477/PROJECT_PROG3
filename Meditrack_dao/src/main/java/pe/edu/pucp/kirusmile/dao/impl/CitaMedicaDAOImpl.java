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
        String sql = "SELECT idCita, fecha, horaInicio, horaFin, estado, dni_paciente, dni_medico FROM CitaMedica WHERE idCita = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico(rs.getString(7), null, null, null, null, null, null, null, null, null, null, null, null);
                    Paciente paciente = new Paciente(rs.getString(6), null, null, null, null, null, null, null, null, null, null, null);
                    
                    CitaMedica cita = new CitaMedica(
                            rs.getInt(1),
                            rs.getDate(2) != null ? rs.getDate(2).toLocalDate() : null,
                            rs.getTime(3) != null ? rs.getTime(3).toLocalTime() : null,
                            medico
                    );
                    if(rs.getTime(4) != null) cita.setHoraFin(rs.getTime(4).toLocalTime());
                    if(rs.getString(5) != null) cita.setEstado(EstadoCita.valueOf(rs.getString(5)));
                    cita.setPaciente(paciente);
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
        String sql = "INSERT INTO CitaMedica (fecha, horaInicio, horaFin, estado, dni_paciente, dni_medico) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (t.getFecha() != null) ps.setDate(1, java.sql.Date.valueOf(t.getFecha()));
            else ps.setNull(1, java.sql.Types.DATE);
            if (t.getHoraInicio() != null) ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else ps.setNull(2, java.sql.Types.TIME);
            if (t.getHoraFin() != null) ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getEstado() != null ? t.getEstado().name() : null);
            ps.setString(5, t.getPaciente() != null ? t.getPaciente().getDni() : null);
            ps.setString(6, t.getMedicoAsignado() != null ? t.getMedicoAsignado().getDni() : null);
            
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdCita(rs.getInt(1));
            }
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CitaMedica update(CitaMedica t) {
        String sql = "UPDATE CitaMedica SET fecha=?, horaInicio=?, horaFin=?, estado=?, dni_paciente=?, dni_medico=? WHERE idCita = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (t.getFecha() != null) ps.setDate(1, java.sql.Date.valueOf(t.getFecha()));
            else ps.setNull(1, java.sql.Types.DATE);
            if (t.getHoraInicio() != null) ps.setTime(2, java.sql.Time.valueOf(t.getHoraInicio()));
            else ps.setNull(2, java.sql.Types.TIME);
            if (t.getHoraFin() != null) ps.setTime(3, java.sql.Time.valueOf(t.getHoraFin()));
            else ps.setNull(3, java.sql.Types.TIME);
            ps.setString(4, t.getEstado() != null ? t.getEstado().name() : null);
            ps.setString(5, t.getPaciente() != null ? t.getPaciente().getDni() : null);
            ps.setString(6, t.getMedicoAsignado() != null ? t.getMedicoAsignado().getDni() : null);
            ps.setInt(7, t.getIdCita());
            
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(CitaMedica t) {
        throw new UnsupportedOperationException("Error: Método 'remove' deshabilitado por las restricciones de negocio para proteger esta tabla.");
    }
}
