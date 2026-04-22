package pe.edu.pucp.kirusmile.models;

import java.util.Date;
import java.time.LocalTime;

public class CitaMedica {

    private Integer idCita;
    private Date fechaCita;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Paciente paciente;
    private Medico medico;
    private String estadoCita;
    private boolean desactivado;

    //private double costoCita;


    public CitaMedica(Integer idCita, Date fechaCita, LocalTime horaInicio, LocalTime horaFin, Paciente paciente,
                      Medico medico, String estadoCita,boolean desactivado){

        this.idCita=idCita;
        this.fechaCita=fechaCita;
        this.horaInicio=horaInicio;
        this.horaFin=horaFin;
        this.paciente=paciente;
        this.medico = medico;
        this.estadoCita=estadoCita;
        this.desactivado=desactivado;

    }


    //getter y setter

    public Integer getIdCita(){
        return idCita;
    }

    public void setIdCita(Integer idCita){
        this.idCita=idCita;
    }

    public Date getFechaCita(){
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita){
        this.fechaCita=fechaCita;
    }

    public LocalTime getHoraInicio(){
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio){
        this.horaInicio=horaInicio;
    }

    public LocalTime getHoraFin(){
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin){
        this.horaFin=horaFin;
    }

    public Paciente getPaciente(){
        return paciente;
    }

    public void setPaciente(Paciente paciente){
        this.paciente=paciente;
    }

    public Medico getDoctor(){
        return medico;
    }

    public void setDoctor(Medico medico){
        this.medico = medico;
    }

    public String getEstadoCita(){
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita){
        this.estadoCita=estadoCita;
    }

    public boolean getDesactivado() {
        return desactivado;
    }

    public void setDesactivado(boolean desactivado) {
        this.desactivado = desactivado;
    }

    //metodos
    public void actualizarEstado(){

    }
    public void mostrarCita(){

    }


}
