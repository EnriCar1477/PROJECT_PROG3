package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Tratamiento{//TRATAMIENTO
    private TipoTratamiento tipo;
    private String indicaciones;
    private Date fechaInicio;
    private Date fechaFin;

    public Tratamiento(TipoTratamiento tipo, String indicaciones, Date fechaInicio, Date fechaFin) {
        this.tipo = tipo;
        this.indicaciones = indicaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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
        this.fechaFin = fechaFin;
    }
}
