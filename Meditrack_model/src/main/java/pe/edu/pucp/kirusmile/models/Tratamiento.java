package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.util.Date;

public class Tratamiento{//TRATAMIENTO

    // --- ATRIBUTOS PROPIOS ---
    private int idTratamiento;         // ID como tipo primitivo int
    private TipoTratamiento tipo;      // Uso del Enum de Java
    private String indicaciones;       // Ej: "Tomar Paracetamol cada 8 horas"
    private LocalDate fechaInicio;     // Usamos LocalDate (solo fecha, sin hora)
    private LocalDate fechaFin;        // Usamos LocalDate (solo fecha, sin hora)

    private DetalleHistorial detalleHistorial;

    // --- CONSTRUCTORES ---
    public Tratamiento() {
    }

    public Tratamiento(TipoTratamiento tipo, String indicaciones, LocalDate fechaInicio, LocalDate fechaFin) {
        this.tipo = tipo;
        this.indicaciones = indicaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // --- GETTERS Y SETTERS ---


    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public TipoTratamiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoTratamiento tipo) {
        this.tipo = tipo;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public DetalleHistorial getDetalleHistorial() {
        return detalleHistorial;
    }

    public void setDetalleHistorial(DetalleHistorial detalleHistorial) {
        this.detalleHistorial = detalleHistorial;
    }
}

