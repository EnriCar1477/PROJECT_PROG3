package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

public interface EnfermedadCIE10DAO extends BaseDAO<EnfermedadCIE10> {
    // Método específico muy útil para catálogos: buscar por el código de la OMS (Ej. "K02.1")
    EnfermedadCIE10 obtenerPorCodigoCIE(String codigoCIE);
}
