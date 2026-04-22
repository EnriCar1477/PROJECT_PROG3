package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.LogAuditoriaDAO;
import pe.edu.pucp.kirusmile.models.LogAuditoria;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class LogAuditoriaDAOImpl implements LogAuditoriaDAO {

    @Override
    public LogAuditoria load(Integer id) {
        String sql = "SELECT idLog, fechaHora, accionRealizada, ipTerminal FROM LogAuditoria WHERE idLog = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LogAuditoria log = new LogAuditoria();
                    log.setIdLog(rs.getInt(1));
                    if(rs.getTimestamp(2) != null) log.setFechaHora(rs.getTimestamp(2).toLocalDateTime());
                    log.setAccionRealizada(rs.getString(3));
                    log.setIpTerminal(rs.getString(4));
                    return log;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LogAuditoria save(LogAuditoria t) {
        String sql = "INSERT INTO LogAuditoria (fechaHora, accionRealizada, ipTerminal) VALUES (?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if(t.getFechaHora() != null) ps.setTimestamp(1, java.sql.Timestamp.valueOf(t.getFechaHora()));
            else ps.setNull(1, java.sql.Types.TIMESTAMP);
            ps.setString(2, t.getAccionRealizada());
            ps.setString(3, t.getIpTerminal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setIdLog(rs.getInt(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LogAuditoria update(LogAuditoria t) {
        throw new UnsupportedOperationException("Error: Los Logs de Auditoria son inmutables y no pueden ser alterados o actualizados.");
    }

    @Override
    public void remove(LogAuditoria t) {
        throw new UnsupportedOperationException("Error: Los Logs de Auditoria jamas deben eliminarse bajo ninguna circunstancia de negocio.");
    }
}
