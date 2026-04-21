package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaMedica {

    // --- ATRIBUTOS ---
    private int idCita;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoCita estado; // Enum: PROGRAMADA, ATENDIDA, CANCELADA, PENDIENTE_PAGO
    private Paciente paciente;
    private Medico medicoAsignado;
    private DetalleHistorial detalle;
    private Pago pago;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public int getIdCita() {
        return idCita;
    }
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public EstadoCita getEstado() {
        return estado;
    }
    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedicoAsignado() {
        return medicoAsignado;
    }
    public void setMedicoAsignado(Medico medicoAsignado) {
        this.medicoAsignado = medicoAsignado;
    }

    public DetalleHistorial getDetalle() {
        return detalle;
    }
    public void setDetalle(DetalleHistorial detalle) {
        this.detalle = detalle;
    }

    public Pago getPago() {
        return pago;
    }
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    // --- CONSTRUCTORES ---

    public CitaMedica() {
        this.estado = EstadoCita.PENDIENTE_PAGO; // Estado inicial sugerido
    }

    public CitaMedica(int idCita, LocalDate fecha, LocalTime horaInicio, Medico medicoAsignado) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.medicoAsignado = medicoAsignado;
        this.estado = EstadoCita.PROGRAMADA;
        // La hora fin se podría calcular automáticamente sumando 20 o 30 min
        this.horaFin = horaInicio.plusMinutes(30); 
    }

    // --- MÉTODOS ---

    public void cambiarEstado(EstadoCita nuevoEstado) {
        if (this.estado == EstadoCita.ATENDIDA && nuevoEstado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede cancelar una cita que ya ha sido atendida.");
        }
        this.estado = nuevoEstado;
        System.out.println("Cita N° " + idCita + " cambió su estado a: " + nuevoEstado);
    }

    public double obtenerCostoAtencion() {
        if (medicoAsignado != null && medicoAsignado.getEspecialidad() != null) {
            // El precio estándar está definido en la clase Especialidad
            return medicoAsignado.getEspecialidad().getCostoEspecialidad();
        }
        return 0.0;
    }

    public void finalizarCita(DetalleHistorial detalleFinalizado) {
        this.detalle = detalleFinalizado;
        this.cambiarEstado(EstadoCita.ATENDIDA);
    }
}
