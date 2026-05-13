package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;

import java.util.List;

public interface HorarioDisponibilidadDAO extends BaseDAO<HorarioDisponibilidad>{
    // Método vital: Ver los horarios de 1 medico, no los de todos
    List<HorarioDisponibilidad> listarPorFidMedico(int fid_medico);
}