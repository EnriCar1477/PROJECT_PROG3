package pe.edu.pucp.kirusmile.models;

import java.util.Date;
import java.util.Calendar;

public class Persona {

    // --- ATRIBUTOS ---
    private String dni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private String telefono;
    private String correo;

    // --- GETTERS Y SETTERS INTERCALADOS ---

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

    // --- CONSTRUCTORES ---

    public Persona() {
        // Constructor vacío
    }

    public Persona(String dni, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    // --- MÉTODOS ---

    /**
     * Retorna el nombre completo concatenando los tres campos de nombre.
     */
    public String obtenerNombreCompleto() {
        return this.nombres + " " + this.apellidoPaterno + " " + this.apellidoMaterno;
    }

    /**
     * Método de utilidad para calcular la edad actual basada en la fecha de nacimiento.
     */
    public int calcularEdad() {
        if (this.fechaNacimiento == null) return 0;

        Calendar hoy = Calendar.getInstance();
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.setTime(this.fechaNacimiento);

        int edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);
        if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        return edad;
    }

    public boolean validarDni() {
        // Lógica básica para verificar que el DNI tenga 8 dígitos
        return this.dni != null && this.dni.length() == 8;
    }
}