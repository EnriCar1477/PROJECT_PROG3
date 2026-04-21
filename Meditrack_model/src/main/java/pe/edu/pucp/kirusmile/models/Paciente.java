package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Paciente extends Persona{
	private String estado;//ACTIVO(ES PACIENTE REGISTRADO), INACTIVO(NO REGISTRADO), BETADO (NO ESTA PERMITIDO)
	private boolean tieneSeguro;
	
	
	public Paciente(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,String telefono,String correo,
					String estado, boolean tieneSeguro){
		super(dni,nombres,apellidoPaterno,apellidoMaterno,fechaNacimiento,telefono,correo);
		this.estado=estado;
		this.tieneSeguro=tieneSeguro;
	
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