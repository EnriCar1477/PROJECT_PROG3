package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Medico;

import java.util.List;

public interface IMedicoBL {
    // Registra los datos médicos (asumiendo que sus datos de Empleado ya existen)
    int registrar(Medico medico);

    // Actualiza datos como su RNE, firma digital o especialidad
    int actualizar(Medico medico);

    // Obtiene al médico y auto-ensambla su lista de horarios
    Medico obtenerPorId(int idMedico);

    // Método vital: Cuando el empleado hace Login, si es médico, buscamos su perfil clínico
    Medico obtenerPorFidEmpleado(int fidEmpleado);

    // Lista a todos los médicos para el módulo de administración
    List<Medico> listarTodos();

}
