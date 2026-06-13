package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.LogAuditoria;

import java.util.List;

public interface LogAuditoriaDAO extends BaseDAO<LogAuditoria>{
    // Método vital para rastrear las acciones de un empleado sospechoso o específico
    List<LogAuditoria> listarPorFidEmpleado(int fid_empleado);
}