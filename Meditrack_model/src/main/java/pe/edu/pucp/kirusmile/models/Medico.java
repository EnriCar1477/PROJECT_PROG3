package pe.edu.pucp.kirusmile.models;

import java.util.Date;
import java.util.List;

public class Medico extends Empleado {
	private String cmp;
	private String rne;
	private Especialidad especialidad;
	private Date fechaIngreso;
	private String firmaDigital;
	private List<HorarioDisponibilidad> listaHorarios;
	private boolean desactivado;

	public Medico(String dni, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento,
			String telefono, String correo, String codigoEmpleado, Date fechaVinculacion, 
			String cmp, String rne, Especialidad especialidad, Date fechaIngreso,
			String firmaDigital, List<HorarioDisponibilidad> listaHorarios, boolean desactivado) {
		super(dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, codigoEmpleado, fechaVinculacion);
		this.cmp = cmp;
		this.rne = rne;
		this.especialidad = especialidad;
		this.fechaIngreso = fechaIngreso;
		this.firmaDigital = firmaDigital;
		this.listaHorarios = listaHorarios;
		this.desactivado = desactivado;
	}

	public boolean getDesactivado() {
		return desactivado;
	}

	public void setDesactivado(boolean desactivado) {
		this.desactivado = desactivado;
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

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public List<HorarioDisponibilidad> getListaHorarios() {
		return listaHorarios;
	}

	public void setListaHorarios(List<HorarioDisponibilidad> listaHorarios) {
		this.listaHorarios = listaHorarios;
	}

	public void registrarCredenciales(String cmp, String rne, Especialidad especialidad) {

	}

	/* public boolean validarEspecialidad(){} */
	public void actualizarFirmarDigital(String nuevoToken) {
		this.setFirmaDigital(firmaDigital);
	}

	/* public double obtenerTarifaConsulta(){} */
}
