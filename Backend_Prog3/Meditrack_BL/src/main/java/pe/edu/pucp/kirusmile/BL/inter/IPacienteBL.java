package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Paciente;

import java.util.List;

public interface IPacienteBL {

    // Registra un nuevo paciente validando sus datos personales y médicos
    int registrar(Paciente paciente);

    // Actualiza los datos (ej. si cambia de teléfono o de grado de instrucción)
    int actualizar(Paciente paciente);

    // Realiza el borrado lógico del paciente en el sistema
    int eliminar(int idPaciente);

    // Obtiene un paciente por su ID interno
    Paciente obtenerPorId(int idPaciente);

    // Método vital: Búsqueda rápida por DNI (lo que más usará la recepcionista)
    Paciente obtenerPorDni(String dni);

    // Lista a todos los pacientes activos
    List<Paciente> listarTodos();


}
