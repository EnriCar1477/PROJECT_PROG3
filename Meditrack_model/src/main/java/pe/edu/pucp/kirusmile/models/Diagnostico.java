package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;

public class Diagnostico {
    private Integer idDiagnostico;
	private String tipo;
    private String observaciones;
    private EnfermedadCIE10 enfermedadPrincial;
    private LocalDateTime fechaHoraRegistro;
    private boolean desactivado;

    public Diagnostico(Integer idDiagnostico, String tipo, String observaciones, EnfermedadCIE10 enfermedadPrincial, LocalDateTime fechaHoraRegistro, boolean desactivado) {
        this.idDiagnostico = idDiagnostico;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.enfermedadPrincial = enfermedadPrincial;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.desactivado = desactivado;
    }

    public Integer getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(Integer idDiagnostico) { this.idDiagnostico = idDiagnostico; }

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

    public EnfermedadCIE10 getEnfermedadPrincial() {
        return enfermedadPrincial;
    }

    public void setEnfermedadPrincial(EnfermedadCIE10 enfermedadPrincial) {
        this.enfermedadPrincial = enfermedadPrincial;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public boolean getDesactivado() { return desactivado; }
    public void setDesactivado(boolean desactivado) { this.desactivado = desactivado; }
}
