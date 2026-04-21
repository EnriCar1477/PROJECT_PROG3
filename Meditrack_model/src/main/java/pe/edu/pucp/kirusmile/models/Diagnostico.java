package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.util.Date;
public class Diagnostico{
	private String tipo;
    private String observaciones;
    private EnfermedadCIE10 enfermedadPrincial;
    private LocalDateTime fechaHoraRegistro;

    public Diagnostico(String tipo, String observaciones, EnfermedadCIE10 enfermedadPrincial, LocalDateTime fechaHoraRegistro) {
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.enfermedadPrincial = enfermedadPrincial;
        this.fechaHoraRegistro = fechaHoraRegistro;
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
}
