package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;

public class Diagnostico {
    // --- ATRIBUTOS PROPIOS ---
    private int idDiagnostico;
    private String tipo;                  // Ej: "PRESUNTIVO" o "DEFINITIVO"
    private String observaciones;
    private LocalDateTime fechaHoraRegistro; // Mapeado de DateTime a LocalDateTime

    // --- RELACIONES ---
    private EnfermedadCIE10 enfermedadBase;  // Asociación con el catálogo de la OMS
    private DetalleHistorial detalleHistorial;
    // --- CONSTRUCTORES ---
    public Diagnostico() {
        // Truco pro: Cuando se crea un diagnóstico en Java, capturamos automáticamente el instante exacto
        this.fechaHoraRegistro = LocalDateTime.now();
    }

    public Diagnostico(String tipo, String observaciones, EnfermedadCIE10 enfermedadBase) {
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.enfermedadBase = enfermedadBase;
        // Capturamos el instante exacto en que el doctor hizo el diagnóstico
        this.fechaHoraRegistro = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---


    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public EnfermedadCIE10 getEnfermedadBase() {
        return enfermedadBase;
    }

    public void setEnfermedadBase(EnfermedadCIE10 enfermedadBase) {
        this.enfermedadBase = enfermedadBase;
    }

    public DetalleHistorial getDetalleHistorial() {
        return detalleHistorial;
    }

    public void setDetalleHistorial(DetalleHistorial detalleHistorial) {
        this.detalleHistorial = detalleHistorial;
    }
	
	
	/*
	
		Tienes private String tipo; // Ej: "PRESUNTIVO" o "DEFINITIVO"
		Como solo hay dos opciones reales en medicina, sería ideal convertirlo en un Enum (así como hiciste con EstadoCita).

		Sugerencia: Crea un public enum TipoDiagnostico { PRESUNTIVO, DEFINITIVO } y úsalo aquí. 
		Esto evita que alguien escriba "Presunto" o "Definitiboo" por error.
	
	*/
	
	
}
