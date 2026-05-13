package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Diagnostico;

import java.util.List;


public interface DiagnosticoDAO extends BaseDAO<Diagnostico>{
    // Solo dejamos el método específico para listar, el save genérico viene de BaseDAO
    List<Diagnostico> listarPorFidDetalle(int fid_detalle);
}