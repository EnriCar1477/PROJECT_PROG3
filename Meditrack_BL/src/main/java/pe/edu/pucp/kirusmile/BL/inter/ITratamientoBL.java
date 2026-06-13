package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.util.List;

public interface ITratamientoBL {
    // Registra un tratamiento validando fechas y campos obligatorios
    int registrar(Tratamiento tratamiento);

    // Actualiza el tratamiento (ej. para agregar la fecha de fin cuando el paciente se cura)
    int actualizar(Tratamiento tratamiento);

    // Obtiene un tratamiento específico por su ID
    Tratamiento obtenerPorId(int idTratamiento);

    // Método vital para el ensamblaje: Trae todos los tratamientos recetados en una consulta
    List<Tratamiento> listarPorFidDetalle(int fidDetalle);

    public int eliminar(int idTratamiento);
}
