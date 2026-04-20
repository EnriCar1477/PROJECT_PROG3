import java.util.Date;

public class Doctor extends Persona{
	private String especialidad;
	private Date fechaIngreso;
	
	public Doctor(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,String telefono,String correo,
					String especialidad, Date fechaIngreso){
		super(dni,nombres,apellidoPaterno,apellidoMaterno,fechaNacimiento,telefono,correo);
		this.especialidad=especialidad;
		this.fechaIngreso=fechaIngreso;
	}
	
	public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
	
	
	public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
	
	@Override
	public void mostrarDatos(){
		
		
	}
	
	
	
}