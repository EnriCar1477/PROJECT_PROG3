package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class HistorialMedico {

	// --- ATRIBUTOS PROPIOS
	private int idHistorial;
	private LocalDateTime fechaCreacion;
	private String estadoFisico;
	private boolean activo; // ¡NUEVO! Atributo para el borrado lógico

	// --- ATRIBUTOS DE RELACIÓN
	private List<DetalleHistorial> listaDetalles;
	private Paciente paciente;


	// --- CONSTRUCTORES ---
	public HistorialMedico() {
		// Vital: Inicializamos la lista para evitar NullPointerException al crear un historial nuevo
		this.listaDetalles = new ArrayList<>();
		this.activo = true; // Por defecto nace activo
	}

	public HistorialMedico(LocalDateTime fechaCreacion, String estadoFisico, List<DetalleHistorial> listaDetalles, Paciente paciente) {
		this.fechaCreacion = fechaCreacion;
		this.estadoFisico = estadoFisico;
		this.listaDetalles = listaDetalles;
		this.paciente = paciente;
	}

	// --- GETTERS Y SETTERS ---


	public int getIdHistorial() {
		return idHistorial;
	}

	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getEstadoFisico() {
		return estadoFisico;
	}

	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}

	public List<DetalleHistorial> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<DetalleHistorial> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}