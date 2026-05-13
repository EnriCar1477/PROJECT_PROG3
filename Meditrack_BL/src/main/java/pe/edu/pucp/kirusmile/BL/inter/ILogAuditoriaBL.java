package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.LogAuditoria;

import java.util.List;

public interface ILogAuditoriaBL {

    // Única acción de escritura permitida: Registrar un nuevo evento
    int registrar(LogAuditoria log);

    // Obtiene el detalle exacto de un evento por su ID
    LogAuditoria obtenerPorId(int idLogAuditoria);

    // Lista el historial completo (para el súper administrador)
    List<LogAuditoria> listarTodos();

    // Método vital: Lista todo lo que ha hecho un empleado específico en el sistema
    List<LogAuditoria> listarPorFidEmpleado(int fidEmpleado);
}
