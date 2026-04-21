package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Tratamiento{//TRATAMIENTO

    

    private int IdTratamiento;
    private TipoTratamiento tipo;
    private String indicaciones;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean desactivado;

    public Tratamiento(TipoTratamiento tipo, String indicaciones, Date fechaInicio, Date fechaFin,boolean desactivado) {
        this.tipo = tipo;
        this.indicaciones = indicaciones;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.desactivado=desactivado;
    }
    public int getIdTratamiento() {
        return IdTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        IdTratamiento = idTratamiento;
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

    public boolean getDesactivado() {
        return desactivado;
    }

    public void setDesactivado(boolean desactivado) {
        this.desactivado = desactivado;
    }
}

