import java.util.Date;

public class Persona{
	private String dni; // El id lo tiene tanto paciente, médico y secretario/a
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Date fechaNacimiento;
	private String telefono;
	private String correo;
	
	public Persona(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,String telefono,String correo){
		this.dni=dni;
		this.nombres=nombres;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
		this.fechaNacimiento=fechaNacimiento;
		this.telefono=telefono;
		this.correo=correo;
	}
	
	public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
	

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
	
	public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }	

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
	
	public void actualizarInformacion(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,String telefono,String correo){
		/*
		this.dni=dni;
		this.nombres=nombres;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
		this.fechaNacimiento=fechaNacimiento;
		this.telefono=telefono;
		this.correo=correo;
		*/
	}
	
	public void mostrarDatos(){
		
		
	}
	
	
}