package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
public class Persona {

    // --- ATRIBUTOS PROPIOS ---
    private int idPersona;
    private String dni; // Identificador principal
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento; // Usamos LocalDate para la fecha exacta del calendario
    private String telefono;
    private String correo;

    // --- CONSTRUCTORES ---
    public Persona() {
    }

    public Persona(String dni, String nombres, String apellidoPaterno, String apellidoMaterno,
                   LocalDate fechaNacimiento, String telefono, String correo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
    }

    // --- GETTERS Y SETTERS ---


    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
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
	
	/*
	
	Sobre el String dni en Persona.java

	Observación: El DNI está como String, lo cual es la decisión correcta 
	(si fuera un int, los DNIs que empiezan con cero como "0456..." perderían el cero inicial).

	Recomendación en el BL: Asegúrate de que, en tu lógica de negocio, recortes los espacios 
	con .trim() y valides que dni.length() == 8.
	
	
	
	*/
	
	
	
	
	
	
	
}
