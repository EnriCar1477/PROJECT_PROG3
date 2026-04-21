package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistorialMedico {

    // --- ATRIBUTOS ---
    private String numeroHC;
    private LocalDateTime fechaCreacion;
    private String estadoFisico; // Requisito NTS 139 - Num. 6.2.1 (Activo, Pasivo, etc.)
    private List<DetalleHistorial> listaDetalles;
    private Paciente paciente; // Relación necesaria para validar el DNI

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getNumeroHC() {
        return numeroHC;
    }

    public void setNumeroHC(String numeroHC) {
        // Validación: El número de HC debe ser el DNI del paciente (NTS 139)
        if (paciente != null && !numeroHC.equals(paciente.getDni())) {
            throw new IllegalArgumentException("Error: El número de HC debe coincidir con el DNI del paciente.");
        }
        this.numeroHC = numeroHC;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(String estadoFisico) {
        // Por norma, suele ser: Vigente/Activo, Pasivo/Pasivo, o Eliminado
        this.estadoFisico = estadoFisico;
    }

    public List<DetalleHistorial> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetalleHistorial> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // --- CONSTRUCTORES ---

    public HistorialMedico() {
        // Constructor vacío para persistencia/frameworks
        this.fechaCreacion = LocalDateTime.now();
        this.listaDetalles = new ArrayList<>();
        this.estadoFisico = "ACTIVO"; // Estado inicial por defecto
    }

    public HistorialMedico(Paciente paciente) {
        this.paciente = paciente;
        this.numeroHC = paciente.getDni(); // Asignación automática del DNI como número de HC
        this.fechaCreacion = LocalDateTime.now();
        this.estadoFisico = "ACTIVO";
        this.listaDetalles = new ArrayList<>();
    }

    // --- MÉTODOS ---

    /**
     * Agrega un nuevo detalle clínico asegurando que la cita origen 
     * corresponda legalmente al dueño de este historial.
     */
    public void agregarDetalle(DetalleHistorial nuevoDetalle) {
        if (nuevoDetalle == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo.");
        }

        // Validación de integridad: ¿El paciente de la cita es el dueño de esta HC?
        String dniPacienteCita = nuevoDetalle.getCitaOrigen().getPaciente().getDni();
        
        if (dniPacienteCita.equals(this.numeroHC)) {
            this.listaDetalles.add(nuevoDetalle);
        } else {
            throw new SecurityException("Error Crítico: Intento de registrar atención en historial ajeno.");
        }
    }

    /**
     * Devuelve la lista de atenciones en modo solo lectura para 
     * proteger la colección interna del historial.
     */
    public List<DetalleHistorial> obtenerHistorialCompleto() {
        return Collections.unmodifiableList(this.listaDetalles);
    }

    public int totalAtencionesRegistradas() {
        return this.listaDetalles.size();
    }
}