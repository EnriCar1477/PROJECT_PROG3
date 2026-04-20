package pe.edu.pucp.kirusmile.models;

import java.util.List;
import java.util.ArrayList;

public class Detalle{
	
	private int idDetalle;
	private Cita cita;
	
	private List<Diagnostico> listaDiagnosticos;
	private List<Tratamiento> listaTratamientos;
	
	
	public Detalle(int idDetalle, Cita cita){
		
//		this.detalle;
		this.cita=cita;
		this.listaDiagnosticos=new ArrayList<>();
		this.listaTratamientos=new ArrayList<>();
	}
	
	//getter y setter
	
	public int getIdDetalle(){
		return idDetalle;
	}
	
	public void setIdDetalle(int idDetalle){
		this.idDetalle=idDetalle;
	}
	
	public Cita getCita(){
		return cita;
	}
	
	public void setCita(Cita cita){
		this.cita=cita;
	}
	
	
	public List<Diagnostico> getListaDiagnosticos(){
		return new ArrayList<>(listaDiagnosticos);//nueva lista llenado con listaDiagnosticos
	}
	
//	public void setListaDiagnosticos(List<pe.edu.pucp.kirusmile.models.Diagnostico>(listaDiagnosticos){
//		this.listaDiagnosticos=new ArrayList<>(listaDiagnosticos);
//	}
	
	
	public List<Tratamiento> getListaTratamientos(){
		return new ArrayList<>(listaTratamientos);
	}
	
	public void setListaTratamientos(List<Tratamiento> listaTratamientos){
		this.listaTratamientos=new ArrayList<>(listaTratamientos);
	}
	
	
	//metodos
	
	public void agregarDiagnostico(){
		
	}
	
	public void eliminarDiagnostico(){
		
	}
	
	public void modificarDiagnostico(){
		
	}
	
	
	public void agregarTratamiento(){//si es nuevo paciente
		
	}
	
	public void eliminarTratamiento(){//si esta betado
		
	}
	
	public void modificarTratamiento(){//si agregamos alguna observacion más
		
	}
	
	
}


