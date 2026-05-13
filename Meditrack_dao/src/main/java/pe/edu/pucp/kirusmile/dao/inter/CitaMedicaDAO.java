package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.CitaMedica;

import java.util.List;

public interface CitaMedicaDAO extends BaseDAO<CitaMedica> {
    // Métodos especializados necesarios para la gestión de la clínica
    List<CitaMedica> listarPorFidMedico(int fid_medico);
    List<CitaMedica> listarPorFidPaciente(int fid_paciente);
}
