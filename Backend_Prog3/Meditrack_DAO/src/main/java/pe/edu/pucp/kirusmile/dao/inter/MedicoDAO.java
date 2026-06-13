package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Medico;

public interface MedicoDAO extends BaseDAO<Medico> {
    // Método valioso para cuando el administrador busque a un médico por su Colegiatura
    Medico obtenerPorCMP(String cmp);

    // Método para recuperar los datos del médico usando el ID de empleado
    Medico obtenerPorFidEmpleado(int fid_empleado);
}
