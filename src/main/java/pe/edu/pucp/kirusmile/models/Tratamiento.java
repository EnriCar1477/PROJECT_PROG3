package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Tratamiento {

    // --- ATRIBUTOS ---
    private TipoTratamiento tipo; // Asumiendo que es un Enum (ej. FARMACOLOGICO, TERAPIA, CIRUGIA)
    private String indicaciones;
    private Date fechaInicio;
    private Date fechaFin;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public TipoTratamiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoTratamiento tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de tratamiento es obligatorio.");
        }
        this.tipo = tipo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        if (indicaciones == null || indicaciones.trim().isEmpty()) {
            throw new IllegalArgumentException("Las indicaciones del tratamiento no pueden estar vacías.");
        }
        this.indicaciones = indicaciones;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        // Validación de lógica de negocio: El tratamiento no puede terminar antes de empezar
        if (this.fechaInicio != null && fechaFin != null && fechaFin.before(this.fechaInicio)) {
            throw new IllegalArgumentException("Error: La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.fechaFin = fechaFin;
    }

    // --- CONSTRUCTORES ---

    public Tratamiento() {
        // Constructor vacío
    }

    public Tratamiento(TipoTratamiento tipo, String indicaciones, Date fechaInicio, Date fechaFin) {
        this.setTipo(tipo); // Usamos setter para validación
        this.setIndicaciones(indicaciones); // Usamos setter para validación
        this.fechaInicio = fechaInicio;
        this.setFechaFin(fechaFin); // Usamos setter para validar que fin > inicio
    }

    // --- MÉTODOS ---

    /**
     * Verifica si el tratamiento sigue vigente en la fecha actual.
     * Útil para que el sistema le avise al paciente o al médico si 
     * aún debe seguir tomando la medicación.
     */
    public boolean esTratamientoActivo(Date fechaActual) {
        if (this.fechaInicio == null || this.fechaFin == null || fechaActual == null) {
            return false;
        }
        // Retorna true si la fecha actual está entre el inicio y el fin
        return !fechaActual.before(this.fechaInicio) && !fechaActual.after(this.fechaFin);
    }

    /**
     * Genera un formato de texto limpio para imprimir en la receta médica.
     */
    public String obtenerRecetaFormateada() {
        // En Java antiguo se usaría SimpleDateFormat, pero aquí mostramos un resumen rápido
        return String.format("[%s]\nIndicaciones: %s\nDesde: %tD Hasta: %tD", 
                (this.tipo != null ? this.tipo.name() : "GENERAL"), 
                this.indicaciones, 
                this.fechaInicio, 
                this.fechaFin);
    }
}