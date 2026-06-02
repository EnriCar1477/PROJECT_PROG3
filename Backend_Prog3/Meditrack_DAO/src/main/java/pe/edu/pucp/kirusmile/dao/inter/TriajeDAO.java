package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Triaje;

public interface TriajeDAO extends BaseDAO<Triaje>{
    // Método necesario para obtener el triaje de una atención específica
    Triaje obtenerPorIdDetalle(int fid_detalle);
}