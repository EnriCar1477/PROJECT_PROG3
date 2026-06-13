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

        if (cita.getFecha().isBefore(LocalDate.now())) {
            System.err.println("Error BL: No se pueden agendar citas en fechas pasadas.");
            return 0;
        }

        cita.setEstado(EstadoCita.PROGRAMADA);
        cita.setMonto(0.0);
        cita.setActivo(true); // Aseguramos que nace activa

        return citaDAO.save(cita);
    }

    @Override
    public int actualizar(CitaMedica cita) {
        if (cita.getIdCitaMedica() == 0) return 0;
        if (!validarReglasBasicas(cita)) return 0;
        return citaDAO.update(cita);
    }

    @Override
    public int cancelarCita(int idCita) {
        CitaMedica cita = citaDAO.load(idCita);
        if (cita == null) return 0;

        // CORRECCIÓN 1: No cancelar si ya fue atendida
        if (cita.getEstado() == EstadoCita.ATENDIDA) {
            System.err.println("Error BL: No se puede cancelar una cita que ya fue atendida.");
            return 0;
        }

        if (cita.getEstado() == EstadoCita.CANCELADA) {
            System.err.println("Aviso: La cita ya estaba cancelada.");
            return 0;
        }

        cita.setEstado(EstadoCita.CANCELADA);
        return citaDAO.update(cita);
    }

    @Override
    public int registrarPago(int idCita, double monto, String metodoPago) {
        CitaMedica cita = citaDAO.load(idCita);
        if (cita == null) return 0;

        // Regla: No pagar citas canceladas
        if (cita.getEstado() == EstadoCita.CANCELADA) {
            System.err.println("Error BL: No se puede registrar pago de una cita cancelada.");
            return 0;
        }

        cita.setMonto(monto);
        cita.setMetodoPago(metodoPago.trim().toUpperCase());
        cita.setFechaHoraPago(LocalDateTime.now());

        // Regla: Si se registra el pago, la cita pasa a CONFIRMADA
        cita.setEstado(EstadoCita.CONFIRMADA);

        return citaDAO.update(cita);
    }

    @Override
    public CitaMedica obtenerPorId(int idCita) {
        return citaDAO.load(idCita);
    }

    @Override
    public List<CitaMedica> listarPorFidPaciente(int fidPaciente) {
        return citaDAO.listarPorFidPaciente(fidPaciente);
    }

    @Override
    public List<CitaMedica> listarPorFidMedico(int fidMedico) {
        return citaDAO.listarPorFidMedico(fidMedico);
    }

    private boolean validarReglasBasicas(CitaMedica cita) {
        if (cita.getPaciente() == null || cita.getPaciente().getIdPaciente() == 0) return false;
        if (cita.getMedicoAsignado() == null || cita.getMedicoAsignado().getIdMedico() == 0) return false;

        // Validación de coherencia de tiempo
        if (cita.getFecha() == null || cita.getHoraInicio() == null || cita.getHoraFin() == null) return false;
        if (!cita.getHoraInicio().isBefore(cita.getHoraFin())) return false;

        return true;
    }
}
