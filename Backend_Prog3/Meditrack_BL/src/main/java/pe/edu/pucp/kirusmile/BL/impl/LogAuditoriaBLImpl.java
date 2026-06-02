package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.ILogAuditoriaBL;
import pe.edu.pucp.kirusmile.dao.impl.LogAuditoriaDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.LogAuditoriaDAO;
import pe.edu.pucp.kirusmile.models.LogAuditoria;

import java.time.LocalDateTime;
import java.util.List;

public class LogAuditoriaBLImpl implements ILogAuditoriaBL {

    private LogAuditoriaDAO auditoriaDAO;

    public LogAuditoriaBLImpl() {
        this.auditoriaDAO = new LogAuditoriaDAOImpl();
    }

    @Override
    public int registrar(LogAuditoria log) {
        // 1. Validar al autor de la acción (Nunca puede haber un log anónimo)
        if (log.getEmpleado() == null || log.getEmpleado().getIdEmpleado() == 0) {
            System.err.println("Error de Seguridad [BL]: No se puede registrar una auditoría sin identificar al Empleado.");
            return 0;
        }

        // 2. Validar que la acción y la IP estén presentes
        if (log.getAccionRealizada() == null || log.getAccionRealizada().trim().isEmpty()) {
            System.err.println("Error de Seguridad [BL]: La acción realizada no puede estar vacía.");
            return 0;
        }
        if (log.getIpTerminal() == null || log.getIpTerminal().trim().isEmpty()) {
            System.err.println("Error de Seguridad [BL]: No se detectó la IP del terminal.");
            return 0;
        }

        // 3. Autocompletado del Tiempo:
        // Si el controlador web no envió la hora, el servidor estampa la hora exacta actual por seguridad.
        if (log.getFechaHora() == null) {
            log.setFechaHora(LocalDateTime.now());
        }

        // Limpiamos los textos
        log.setAccionRealizada(log.getAccionRealizada().trim());
        log.setIpTerminal(log.getIpTerminal().trim());

        return auditoriaDAO.save(log);
    }

    @Override
    public LogAuditoria obtenerPorId(int idLogAuditoria) {
        if (idLogAuditoria <= 0) return null;
        return auditoriaDAO.load(idLogAuditoria);
    }

    @Override
    public List<LogAuditoria> listarTodos() {
        // El DAO se encarga de traerlos ordenados por fecha de manera descendente (los más nuevos primero)
        return auditoriaDAO.listALL();
    }

    @Override
    public List<LogAuditoria> listarPorFidEmpleado(int fidEmpleado) {
        if (fidEmpleado <= 0) {
            System.err.println("Error BL: ID de empleado inválido para la búsqueda de auditorías.");
            return null;
        }
        return auditoriaDAO.listarPorFidEmpleado(fidEmpleado);
    }
}
