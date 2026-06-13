package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class HorarioDisponibilidad {
    // --- ATRIBUTOS PROPIOS ---
    private int idHorario;
    private String diaSemana;           // Ej: "Lunes", "Martes" (Patrón semanal)
    private LocalTime horaInicio;      // Mapea perfectamente con TIME en MySQL
    private LocalTime horaFin;         // Mapea perfectamente con TIME en MySQL
    private boolean activo;            // Borrado lógico para turnos cancelados

    private Medico medico;

    // --- CONSTRUCTORES ---
    public HorarioDisponibilidad() {
        this.activo = true; // Por defecto el horario nace disponible
    }

    public HorarioDisponibilidad(String diaSemana, LocalTime horaInicio, LocalTime horaFin) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.activo = true;
    }

    // --- GETTERS Y SETTERS ---


    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
