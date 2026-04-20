package pe.edu.pucp.kirusmile.models;

import java.util.Date;
import java.time.LocalTime;

public class Cita{
	
	private int idCita;
	private Date fechaCita;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private Paciente paciente;
	private Doctor doctor;
	private String estadoCita;
	
	//private double costoCita;
	
	
	public Cita(int idCita, Date fechaCita, LocalTime horaInicio,LocalTime horaFin,Paciente paciente,
			Doctor doctor, String estadoCita){
				
		this.idCita=idCita;
		this.fechaCita=fechaCita;
		this.horaInicio=horaInicio;
		this.horaFin=horaFin;
		this.paciente=paciente;
		this.doctor=doctor;
		this.estadoCita=estadoCita;
		
	}
	
	
	//getter y setter
	
	public int getIdCita(){
		return idCita;
	}
	
	public void getIdCita(int idCita){
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
	
	public Doctor getDoctor(){
		return doctor;
	}
	
	public void setDoctor(Doctor doctor){
		this.doctor=doctor;
	}
	
	public String getEstadoCita(){
		return estadoCita;
	}
	
	public void setEstadoCita(String estadoCita){
		this.estadoCita=estadoCita;
	}
	
	
	
	
	//metodos
	public void actualizarEstado(){
		
	}
	
	public void mostrarCita(){
		
	}
	
		
}