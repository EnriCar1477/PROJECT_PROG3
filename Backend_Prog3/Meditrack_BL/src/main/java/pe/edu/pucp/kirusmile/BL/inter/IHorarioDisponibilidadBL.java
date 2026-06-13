package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;

import java.util.List;

public interface IHorarioDisponibilidadBL {

    // Registra un nuevo bloque de horario para un médico
    int registrar(HorarioDisponibilidad horario);

    // Actualiza las horas (por ejemplo, si el doctor avisa que llegará más tarde)
    int actualizar(HorarioDisponibilidad horario);

    // Desactiva el horario (Borrado lógico, útil si el doctor pide permiso ese día)
    int eliminar(int idHorario);

    // Obtiene un horario específico por su ID
    HorarioDisponibilidad obtenerPorId(int idHorario);

    // Método vital: Trae toda la agenda futura de un médico en específico
    List<HorarioDisponibilidad> listarPorFidMedico(int fidMedico);

}
