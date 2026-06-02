package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Triaje;

public interface ITriajeBL {
    // Registra un nuevo triaje validando signos vitales
    int registrar(Triaje triaje);

    // Actualiza el triaje en caso de error de digitación
    int actualizar(Triaje triaje);

    // Obtiene el triaje por su propio ID
    Triaje obtenerPorId(int idTriaje);

    // Obtiene el triaje usando el ID de la consulta (relación 1:1)
    Triaje obtenerPorFidDetalle(int fidDetalle);
}
