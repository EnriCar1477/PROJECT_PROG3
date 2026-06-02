package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Diagnostico;

import java.util.List;

public interface IDiagnosticoBL {
    // Registra un diagnóstico validando su CIE-10 y la consulta de origen
    int registrar(Diagnostico diagnostico);

    // Actualiza el diagnóstico (ej. cambiar de Presuntivo a Definitivo)
    int actualizar(Diagnostico diagnostico);

    // Obtiene un diagnóstico específico por su ID
    Diagnostico obtenerPorId(int idDiagnostico);

    // Método vital: Devuelve la lista de diagnósticos de una consulta específica
    List<Diagnostico> listarPorFidDetalle(int fidDetalle);
}
