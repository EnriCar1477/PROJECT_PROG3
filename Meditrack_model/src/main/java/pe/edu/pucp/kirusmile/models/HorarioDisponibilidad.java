package pe.edu.pucp.kirusmile.models;

import java.time.LocalTime;
import java.util.Date;

public class HorarioDisponibilidad {
    private int idHorario;
    private Date fechaEspecifica;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;

    public HorarioDisponibilidad(int idHorario, Date fechaEspecifica, LocalTime horaInicio, LocalTime horaFin, String estado) {
        this.idHorario = idHorario;
        this.fechaEspecifica = fechaEspecifica;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

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
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void marcarComoOcupado(){

    }
    public void liberarHorario(){}

    /*public boolean verificarCruce(LocalTime horaBusqueda){}*/
}
