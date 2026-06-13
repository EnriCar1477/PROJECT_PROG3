package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Anamnesis;

public interface AnamnesisDAO extends BaseDAO<Anamnesis>{
    // Método para recuperar la anamnesis usando la llave foránea del detalle
    Anamnesis obtenerPorFidDetalle(int fid_detalle);
}