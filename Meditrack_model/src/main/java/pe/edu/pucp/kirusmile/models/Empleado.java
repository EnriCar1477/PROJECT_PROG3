package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Empleado extends Persona {

    // --- ATRIBUTOS ---
    private String codigoEmpleado;
    private Date fechaVinculacion;
    private boolean estadoLaboral; // true = Activo, false = Inactivo/Cesado

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Date getFechaVinculacion() {
        return fechaVinculacion;
    }
    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public boolean isEstadoLaboral() {
        return estadoLaboral;
    }
    public void setEstadoLaboral(boolean estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    // --- CONSTRUCTORES ---

    public Empleado() {
        super(); // Llama al constructor vacío de Persona
        this.estadoLaboral = true; // Por defecto, al crearlo está activo
    }

    public Empleado(String dni, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, String telefono, String correo,
                    String codigoEmpleado, Date fechaVinculacion) {
        super(dni, nombres, apellidoPaterno, apellidoMaterno,fechaNacimiento,telefono,correo); // Datos de Persona
        this.codigoEmpleado = codigoEmpleado;
        this.fechaVinculacion = fechaVinculacion;
        this.estadoLaboral = true;
    }

    // --- MÉTODOS ---

    /**
     * Método para cambiar el estado del empleado (ej. despido, renuncia o recontratación).
     */
    public void actualizarEstadoLaboral() {
        if (!estadoLaboral) {
            this.estadoLaboral = true;
            System.out.println("El empleado " + this.codigoEmpleado + " ahora está ACTIVO.");
        } else {
            this.estadoLaboral = false;
            System.out.println("El empleado " + this.codigoEmpleado + " ha sido DADO DE BAJA.");
        }
    }
}