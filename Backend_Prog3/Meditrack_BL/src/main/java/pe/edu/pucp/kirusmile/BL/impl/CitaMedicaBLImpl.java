package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.ICitaMedicaBL;
import pe.edu.pucp.kirusmile.dao.impl.CitaMedicaDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.CitaMedicaDAO;
import pe.edu.pucp.kirusmile.models.CitaMedica;
import pe.edu.pucp.kirusmile.models.EstadoCita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CitaMedicaBLImpl implements ICitaMedicaBL {

    private CitaMedicaDAO citaDAO;

    public CitaMedicaBLImpl() {
        this.citaDAO = new CitaMedicaDAOImpl();
    }

    @Override
    public int registrar(CitaMedica cita) {
        if (!validarReglasBasicas(cita)) return 0;

        // Regla: No agendar en el pasado
        if (cita.getFecha().isBefore(LocalDate.now())) {
            System.err.println("Error BL: No se pueden agendar citas en fechas pasadas.");
            return 0;
        }

        // Al nacer, una cita siempre entra como "PROGRAMADA" (asumiendo que es el primer valor del Enum)
        cita.setEstado(EstadoCita.PROGRAMADA);

        // Por defecto, aún no hay pago
        cita.setMonto(0.0);
        cita.setFechaHoraPago(null);
        cita.setMetodoPago(null);

        return citaDAO.save(cita);
    }

    @Override
    public int actualizar(CitaMedica cita) {
        if (cita.getIdCitaMedica() == 0) {
            System.err.println("Error BL: No se puede actualizar una cita sin ID.");
            return 0;
        }

        if (!validarReglasBasicas(cita)) return 0;

        return citaDAO.update(cita);
    }

    @Override
    public int cancelarCita(int idCita) {
        CitaMedica cita = citaDAO.load(idCita);
        if (cita == null) return 0;

        // OJO:: No puedes cancelar una cita que ya fue atendida
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            System.err.println("Error BL: No se puede cancelar una cita que ya fue atendida por el médico.");
            return 0;
        }

        cita.setEstado(EstadoCita.CANCELADA);
        return citaDAO.update(cita); // Asumimos que el update del DAO guarda el nuevo estado
    }

    @Override
    public int registrarPago(int idCita, double monto, String metodoPago) {
        CitaMedica cita = citaDAO.load(idCita);
        if (cita == null) return 0;

        if (monto <= 0) {
            System.err.println("Error BL: El monto del pago debe ser mayor a 0.");
            return 0;
        }

        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            System.err.println("Error BL: Debe especificar el método de pago (Ej. Yape, Plin, Tarjeta, Efectivo).");
            return 0;
        }

        // Actualizamos los datos financieros
        cita.setMonto(monto);
        cita.setMetodoPago(metodoPago.trim().toUpperCase());
        cita.setFechaHoraPago(LocalDateTime.now()); // Estampa de tiempo exacta de la transacción

        // Opcional: Cambiar estado a PAGADA o CONFIRMADA según las reglas de tu clínica
        // cita.setEstado(EstadoCita.PAGADA);

        return citaDAO.update(cita);
    }

    @Override
    public CitaMedica obtenerPorId(int idCita) {
        return citaDAO.load(idCita); // El DAO debería traerla con INNER JOINs a Paciente y Medico
    }

    @Override
    public List<CitaMedica> listarPorFidPaciente(int fidPaciente) {
        return citaDAO.listarPorFidPaciente(fidPaciente);
    }

    @Override
    public List<CitaMedica> listarPorFidMedico(int fidMedico) {
        return citaDAO.listarPorFidMedico(fidMedico);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida que todos los actores estén presentes y que la lógica del tiempo sea correcta.
     */
    private boolean validarReglasBasicas(CitaMedica cita) {
        // 1. Validar Actores Principales (Los 3 son obligatorios)
        if (cita.getPaciente() == null || cita.getPaciente().getIdPaciente() == 0) {
            System.err.println("Error BL: La cita debe tener un Paciente asignado.");
            return false;
        }
        if (cita.getMedicoAsignado() == null || cita.getMedicoAsignado().getIdMedico() == 0) {
            System.err.println("Error BL: La cita debe tener un Médico asignado.");
            return false;
        }
        if (cita.getEmpleado() == null || cita.getEmpleado().getIdEmpleado() == 0) {
            System.err.println("Error BL: Debe registrarse qué Empleado (recepcionista) creó la cita.");
            return false;
        }

        // 2. Validar Cronograma
        if (cita.getFecha() == null || cita.getHoraInicio() == null || cita.getHoraFin() == null) {
            System.err.println("Error BL: Fecha y horas de la cita son obligatorias.");
            return false;
        }
        if (!cita.getHoraInicio().isBefore(cita.getHoraFin())) {
            System.err.println("Error BL: La hora de inicio debe ser anterior a la hora de fin.");
            return false;
        }

        // 3. Validar Motivo
        if (cita.getMotivoAgendamiento() == null || cita.getMotivoAgendamiento().trim().isEmpty()) {
            System.err.println("Error BL: El motivo de la cita es obligatorio.");
            return false;
        }

        cita.setMotivoAgendamiento(cita.getMotivoAgendamiento().trim());
        return true;


    }
}
