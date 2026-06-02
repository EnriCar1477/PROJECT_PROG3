package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;

import java.util.List;

public interface DetalleHistorialDAO extends BaseDAO<DetalleHistorial>{
    // Método necesario para traer todas las consultas de un mismo historial
    List<DetalleHistorial> listarPorHistorial(int idHistorialMedico);

}
