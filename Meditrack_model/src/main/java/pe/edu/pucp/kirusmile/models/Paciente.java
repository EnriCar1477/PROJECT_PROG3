package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Paciente extends Persona{
	private int id;
	private String estado;//ACTIVO(ES PACIENTE REGISTRADO), INACTIVO(NO REGISTRADO), BETADO (NO ESTA PERMITIDO)
	private boolean tieneSeguro;
	private boolean desactivado;
	
	
	public Paciente(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,
					String telefono,String correo,int id,String estado, boolean tieneSeguro,boolean desactivado){
		super(dni,nombres,apellidoPaterno,apellidoMaterno,fechaNacimiento,telefono,correo);
		this.id=id;
		this.estado=estado;
		this.tieneSeguro=tieneSeguro;
		this.desactivado=desactivado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getDesactivado() {
		return desactivado;
	}

	public void setDesactivado(boolean desactivado) {
		this.desactivado = desactivado;
	}

	public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
	
	

	public boolean getTieneSeguro() {
        return tieneSeguro;
    }

    public void setTieneSeguro(boolean tieneSeguro) {
        this.tieneSeguro = tieneSeguro;
    }

	
	@Override
	public void mostrarDatos(){
		
	}
}
