package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.CitaMedica;

import java.util.List;

public interface ICitaMedicaBL {
    // Registra una nueva cita médica (por defecto entrará como AGENDADA)
    int registrar(CitaMedica cita);

    // Actualiza datos generales de la cita (ej. cambiar el motivo o reprogramar la fecha)
    int actualizar(CitaMedica cita);

    // Cambia el estado de la cita a CANCELADA (Borrado lógico / Cambio de estado)
    int cancelarCita(int idCita);

    // Método financiero: Registra el pago de la cita y cambia el estado
    int registrarPago(int idCita, double monto, String metodoPago);

    // Ensamblaje: Trae la cita con los datos básicos del paciente y médico
    CitaMedica obtenerPorId(int idCita);

    // Para la vista del Paciente (Historial de sus citas)
    List<CitaMedica> listarPorFidPaciente(int fidPaciente);

    // Para la vista del Médico (Su agenda del día/mes)
    List<CitaMedica> listarPorFidMedico(int fidMedico);
}
