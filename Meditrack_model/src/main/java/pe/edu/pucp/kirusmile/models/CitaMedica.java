package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalTime;

public class CitaMedica {

    // --- ATRIBUTOS DE AGENDAMIENTO ---
    private int idCitaMedica;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String motivoAgendamiento;
    private EstadoCita estado;
    private boolean activo;    // AGREGADO: Para el borrado lógico (coherente con SQL)

    // --- ATRIBUTOS DE PAGO ---
    private double monto;
    private LocalDateTime fechaHoraPago;
    private String metodoPago;

    // --- RELACIONES ---
    private Medico medicoAsignado;
    private DetalleHistorial detalle;
    private Empleado empleado;
    private Paciente paciente;

    // --- CONSTRUCTORES ---
    public CitaMedica() {
        // Por defecto, toda cita nueva nace con estado PROGRAMADA
        this.estado = EstadoCita.PROGRAMADA;
        this.activo=true;
        this.fechaHoraPago=LocalDateTime.now();
    }

    public CitaMedica(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                      String motivoAgendamiento, Medico medicoAsignado, Empleado empleado) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.motivoAgendamiento = motivoAgendamiento;
        this.medicoAsignado = medicoAsignado;
        this.empleado = empleado;
        this.estado = EstadoCita.PROGRAMADA;
    }

    // --- GETTERS Y SETTERS ---


    public int getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(int idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
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

    public String getMotivoAgendamiento() {
        return motivoAgendamiento;
    }

    public void setMotivoAgendamiento(String motivoAgendamiento) {
        this.motivoAgendamiento = motivoAgendamiento;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHoraPago() {
        return fechaHoraPago;
    }

    public void setFechaHoraPago(LocalDateTime fechaHoraPago) {
        this.fechaHoraPago = fechaHoraPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
