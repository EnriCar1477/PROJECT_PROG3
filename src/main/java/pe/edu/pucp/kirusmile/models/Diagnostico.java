package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Diagnostico {

    // --- ATRIBUTOS ---
    private String tipo; // Ej: "PRESUNTIVO", "DEFINITIVO", "DESCARTADO"
    private String observaciones;
    private EnfermedadCIE10 enfermedadBase;
    private LocalDateTime fechaHoraRegistro;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de diagnóstico no puede estar vacío.");
        }
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EnfermedadCIE10 getEnfermedadBase() {
        return enfermedadBase;
    }

    public void setEnfermedadBase(EnfermedadCIE10 enfermedadBase) {
        if (enfermedadBase == null) {
            throw new IllegalArgumentException("Todo diagnóstico debe tener una enfermedad CIE-10 asociada.");
        }
        this.enfermedadBase = enfermedadBase;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        // En la vida real este setter casi no se usa para evitar alterar auditorías,
        // pero se incluye por estructura.
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    // --- CONSTRUCTORES ---

    public Diagnostico() {
        // Constructor vacío
        this.fechaHoraRegistro = LocalDateTime.now(); // Se sella la hora automáticamente
    }

    public Diagnostico(String tipo, String observaciones, EnfermedadCIE10 enfermedadBase) {
        this.setTipo(tipo); // Usamos el setter para la validación
        this.observaciones = observaciones;
        this.setEnfermedadBase(enfermedadBase); // Usamos el setter para la validación
        this.fechaHoraRegistro = LocalDateTime.now(); // Captura exacta del momento del diagnóstico
    }

    // --- MÉTODOS ---

    /**
     * Valida si el diagnóstico es concluyente (Definitivo).
     * Esto es útil si el sistema necesita bloquear ciertos tratamientos
     * hasta que no haya un diagnóstico definitivo.
     */
    public boolean esDiagnosticoDefinitivo() {
        return "DEFINITIVO".equalsIgnoreCase(this.tipo);
    }

    /**
     * Devuelve una cadena formateada lista para ser impresa en la receta 
     * o en la vista del historial clínico del paciente.
     */
    public String obtenerResumenDiagnostico() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fecha = (this.fechaHoraRegistro != null) ? this.fechaHoraRegistro.format(formato) : "Sin fecha";
        
        String nombreEnfermedad = (this.enfermedadBase != null) ? this.enfermedadBase.getNombre() : "No especificada";
        String codigoCIE10 = (this.enfermedadBase != null) ? this.enfermedadBase.getCodigo() : "S/C";

        return String.format("[%s] %s - %s (%s)\nObservaciones: %s", 
                fecha, 
                this.tipo.toUpperCase(), 
                nombreEnfermedad, 
                codigoCIE10, 
                (this.observaciones != null ? this.observaciones : "Ninguna"));
    }
}