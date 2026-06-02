package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Especialidad;

import java.util.List;

public interface EspecialidadDAO extends BaseDAO<Especialidad>{
    // Método útil para buscar si una especialidad ya existe antes de registrarla
    Especialidad obtenerPorNombre(String nombreEspecialidad);

    // Método para listar solo las especialidades que siguen activas en la clínica
    List<Especialidad> listarActivas();
}