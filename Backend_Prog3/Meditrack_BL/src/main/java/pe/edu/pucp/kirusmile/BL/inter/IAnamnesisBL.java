package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Anamnesis;

public interface IAnamnesisBL {
    // Registra la entrevista clínica validando los campos obligatorios
    int registrar(Anamnesis anamnesis);

    // Actualiza el texto en caso el médico necesite agregar más detalles durante la misma cita
    int actualizar(Anamnesis anamnesis);

    // Obtiene la anamnesis por su ID directo
    Anamnesis obtenerPorId(int idAnamnesis);

    // Método vital: Obtiene la anamnesis vinculada a una consulta específica
    Anamnesis obtenerPorFidDetalle(int fidDetalle);
}
