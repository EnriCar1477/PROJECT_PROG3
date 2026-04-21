package pe.edu.pucp.kirusmile.models;

import java.util.List;
import java.util.ArrayList;

public class HistorialMedico {
	private int idHistorial;
	private String observaciones;
	private Paciente paciente;
	private List<DetalleHistorial> listaDetalles;

	public HistorialMedico() {
		// Constructor vacío
		this.listaDetalles = new ArrayList<>();
	}

	public HistorialMedico(int idHistorial, String observaciones, Paciente paciente,
			List<DetalleHistorial> listaDetalles) {
		this.idHistorial = idHistorial;// por default NINGUNO
		this.observaciones = observaciones;
		this.paciente = paciente;
		this.listaDetalles = new ArrayList<DetalleHistorial>();

	}

	// getters y setters

	public int getIdHostorial() {
		return idHistorial;
	}

	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void agregarDetalle() {

	}

	public void actualizarObservaciones() {

	}

	public void verHistorialMedico() {
		// Pero no hay información del historial médico aquí...
	}

	// Agregué algunos más

	/*
	 * public void agregarDiagnostico(pe.edu.pucp.kirusmile.models.Diagnostico
	 * diagnostico){
	 * // listaDiagnosticos.add(diagnostico);
	 * }
	 * 
	 * public void agregarReceta(Receta receta){
	 * // listaRecetas.add(receta);
	 * }
	 */

}