package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Medico extends Empleado {
	// --- ATRIBUTOS PROPIOS ---
	private int idMedico;
	private String cmp;                // Colegio Médico del Perú
	private String rne;                // Registro Nacional de Especialista
	private LocalDate fechaIngreso;    // Usamos LocalDate para fechas de calendario
	private String firmaDigital;       // Ruta o representación de la firma
	private boolean activo;

	// --- RELACIONES ---
	private Especialidad especialidad; // Un médico tiene una especialidad principal
	private List<HorarioDisponibilidad> listaHorarios;

	// NUEVO: Requerido por el DAO y para que coincida con el @medicoActual.Persona de Blazor
	private Persona persona;

	// --- CONSTRUCTORES ---
	public Medico() {
		super(); // Llama al constructor de Empleado
		this.activo = true; // Por defecto nace activo
		this.listaHorarios = new ArrayList<>();
		this.persona = new Persona(); // Inicializamos para evitar NullReferenceException
	}

	public Medico(String cmp, String rne, Especialidad especialidad,
	              LocalDate fechaIngreso, String firmaDigital) {
		super();
		this.cmp = cmp;
		this.rne = rne;
		this.especialidad = especialidad;
		this.fechaIngreso = fechaIngreso;
		this.firmaDigital = firmaDigital;
		this.listaHorarios = new ArrayList<>();
	}

	// --- GETTERS Y SETTERS ---


	public int getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}

	public String getCmp() {
		return cmp;
	}

	public void setCmp(String cmp) {
		this.cmp = cmp;
	}

	public String getRne() {
		return rne;
	}

	public void setRne(String rne) {
		this.rne = rne;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<HorarioDisponibilidad> getListaHorarios() {
		return listaHorarios;
	}

	public void setListaHorarios(List<HorarioDisponibilidad> listaHorarios) {
		this.listaHorarios = listaHorarios;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public boolean isActivo() {
		return activo;
	}

	@Override
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/*
	Sobre la firmaDigital en Medico.java

	Observación: Tienes private String firmaDigital;.

	Recomendación: Esto está perfecto, pero tenlo en cuenta al crear tu base de datos MySQL. 
	Si vas a guardar la firma como un link o ruta de una imagen (ej. "/imagenes/firmas/medico1.png"), 
	un VARCHAR(255) está bien. Pero si decides guardar la firma convertida a código puro (formato Base64), 
	deberás usar un tipo LONGTEXT en MySQL porque esos textos son larguísimos.
	
	Sobre las Listas Vacías en Medico.java
	
	Observación: En ambos constructores tienes this.listaHorarios = new ArrayList<>();.

	Recomendación: ¡Excelente! Solo recuerda hacer lo mismo si en el futuro agregas nuevas listas 
	en otras clases. Instanciar la lista vacía evita que el sistema se caiga (NullPointerException) 
	si intentas contar los horarios de un médico nuevo.
	
	*/
	
	
}
