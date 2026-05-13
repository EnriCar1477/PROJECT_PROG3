package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Especialidad;

import java.util.List;

public interface IEspecialidadBL {
    // Registra una nueva especialidad validando nombre y costo
    int registrar(Especialidad especialidad);

    // Actualiza los datos de la especialidad (ej. cambio de tarifa)
    int actualizar(Especialidad especialidad);

    // Desactiva una especialidad (Borrado lógico)
    int eliminar(int idEspecialidad);

    // Obtiene una especialidad por su ID
    Especialidad obtenerPorId(int idEspecialidad);

    // Para la vista de Administración: Trae todas (activas e inactivas)
    List<Especialidad> listarTodas();

    // Para el registro de Médicos/Citas: Trae solo las que están disponibles
    List<Especialidad> listarActivas();
}
