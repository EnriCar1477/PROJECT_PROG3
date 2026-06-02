package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.HistorialMedico;

public interface HistorialMedicoDAO extends BaseDAO<HistorialMedico>{
    // Método extra necesario: Buscar la historia por el ID del paciente
    HistorialMedico obtenerPorIdPaciente(int idPaciente);
}