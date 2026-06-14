package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.LogAuditoriaDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Empleado;
import pe.edu.pucp.kirusmile.models.LogAuditoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogAuditoriaDAOImpl implements LogAuditoriaDAO {

    public LogAuditoriaDAOImpl() {
        // CORRECCIÓN: Constructor vacío, ya no guardamos la conexión global para evitar fugas de memoria
    }

    @Override
    public int save(LogAuditoria objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO LogAuditoria (fid_empleado, fecha_hora, accion_realizada, ip_terminal) " +
                "VALUES (?, ?, ?, ?)";

        // CORRECCIÓN: Abrimos la Connection y el PreparedStatement de forma local y auto-cerrable
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getEmpleado().getIdEmpleado());
            pst.setTimestamp(2, Timestamp.valueOf(objeto.getFechaHora()));
            pst.setString(3, objeto.getAccionRealizada());
            pst.setString(4, objeto.getIpTerminal());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdLogAuditoria(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar LogAuditoria: " + e.getMessage());
        }
        return idGenerado;
    }

    // ¡PERFECTO! REGLA DE SEGURIDAD ESTRICTA: Los logs son inmutables
    @Override
    public int update(LogAuditoria objeto) {
        throw new UnsupportedOperationException("Violación de Seguridad: Un registro de auditoría jamás debe ser modificado.");
    }

    // ¡PERFECTO! REGLA DE SEGURIDAD ESTRICTA: Los logs no se borran
    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Violación de Seguridad: Un registro de auditoría jamás debe ser eliminado.");
    }

    @Override
    public LogAuditoria load(int id) {
        LogAuditoria log = null;
        String sql = "SELECT * FROM LogAuditoria WHERE id_log_auditoria = ?";

        // CORRECCIÓN: Conexión local auto-cerrable
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    log = new LogAuditoria();
                    log.setIdLogAuditoria(rs.getInt("id_log_auditoria"));
                    log.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    log.setAccionRealizada(rs.getString("accion_realizada"));
                    log.setIpTerminal(rs.getString("ip_terminal"));

                    Empleado empleado = new Empleado();
                    empleado.setIdEmpleado(rs.getInt("fid_empleado"));
                    log.setEmpleado(empleado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar LogAuditoria: " + e.getMessage());
        }
        return log;
    }

    @Override
    public List<LogAuditoria> listALL() {
        List<LogAuditoria> lista = new ArrayList<>();
        String sql = "SELECT id_log_auditoria FROM LogAuditoria ORDER BY fecha_hora DESC";

        // CORRECCIÓN: Conexión local auto-cerrable
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Logs de Auditoría: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<LogAuditoria> listarPorFidEmpleado(int fid_empleado) {
        List<LogAuditoria> lista = new ArrayList<>();
        String sql = "SELECT id_log_auditoria FROM LogAuditoria WHERE fid_empleado = ? ORDER BY fecha_hora DESC";

        // CORRECCIÓN: Conexión local auto-cerrable
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, fid_empleado);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar logs por empleado: " + e.getMessage());
        }
        return lista;
    }
}
