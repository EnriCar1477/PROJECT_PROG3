package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.util.List;

public interface TratamientoDAO extends BaseDAO<Tratamiento> {
    // Al igual que en Diagnostico, necesitamos listar los tratamientos de un acto médico
    List<Tratamiento> listarPorFidDetalle(int fid_detalle);
}
