package pe.edu.pucp.kirusmile.models;

import java.time.LocalTime;
import java.util.Date;

public class HorarioDisponibilidad {

    // --- ATRIBUTOS ---
    private int idHorario;
    private Date fechaEspecifica;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado; // Ej: "DISPONIBLE", "OCUPADO", "BLOQUEADO"

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public Date getFechaEspecifica() {
        return fechaEspecifica;
    }

    public void setFechaEspecifica(Date fechaEspecifica) {
        this.fechaEspecifica = fechaEspecifica;
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
        // Validación: La hora de fin no puede ser anterior a la de inicio
        if (this.horaInicio != null && horaFin.isBefore(this.horaInicio)) {
            throw new IllegalArgumentException("Error: La hora de fin debe ser posterior a la hora de inicio.");
        }
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // --- CONSTRUCTORES ---

    public HorarioDisponibilidad() {
        // Constructor vacío
        this.estado = "DISPONIBLE";
    }

    public HorarioDisponibilidad(int idHorario, Date fechaEspecifica, LocalTime horaInicio, LocalTime horaFin) {
        this.idHorario = idHorario;
        this.fechaEspecifica = fechaEspecifica;
        this.horaInicio = horaInicio;
        this.setHoraFin(horaFin); // Usamos el setter para validar la lógica de tiempo
        this.estado = "DISPONIBLE";
    }

    // --- MÉTODOS ---

    /**
     * Cambia el estado del bloque horario a OCUPADO.
     * Se invoca cuando una CitaMedica es confirmada para este horario.
     */
    public void marcarComoOcupado() {
        this.estado = "OCUPADO";
        System.out.println("Horario " + idHorario + " marcado como OCUPADO.");
    }

    /**
     * Restablece el estado a DISPONIBLE.
     * Útil en casos de cancelación de citas o reprogramaciones.
     */
    public void liberarHorario() {
        this.estado = "DISPONIBLE";
        System.out.println("Horario " + idHorario + " ahora está DISPONIBLE.");
    }

    /**
     * Comprueba si una hora determinada se encuentra dentro de este bloque.
     * Esencial para el Requerimiento Funcional de evitar cruces de citas.
     */
    public boolean verificarCruce(LocalTime horaBusqueda) {
        if (horaBusqueda == null || this.horaInicio == null || this.horaFin == null) {
            return false;
        }
        // Retorna true si la hora está entre el inicio (inclusive) y el fin (exclusive)
        return (!horaBusqueda.isBefore(this.horaInicio) && horaBusqueda.isBefore(this.horaFin));
    }
}