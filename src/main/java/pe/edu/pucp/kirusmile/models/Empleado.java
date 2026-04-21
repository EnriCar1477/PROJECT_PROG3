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

    public Empleado(String dni, String nombres, String apellidoPaterno, String apellidoMaterno, 
                    String codigoEmpleado, Date fechaVinculacion) {
        super(dni, nombres, apellidoPaterno, apellidoMaterno); // Datos de Persona
        this.codigoEmpleado = codigoEmpleado;
        this.fechaVinculacion = fechaVinculacion;
        this.estadoLaboral = true;
    }

    // --- MÉTODOS ---

    /**
     * Método para asignar rápidamente los datos contractuales del empleado.
     */
    public void registrarDatosLaborales(String codigo, Date fecha) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de empleado no puede estar vacío.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha de vinculación es obligatoria.");
        }
        
        this.codigoEmpleado = codigo;
        this.fechaVinculacion = fecha;
        System.out.println("Datos laborales registrados correctamente para el código: " + codigo);
    }

    /**
     * Método para cambiar el estado del empleado (ej. despido, renuncia o recontratación).
     */
    public void actualizarEstadoLaboral(boolean estado) {
        this.estadoLaboral = estado;
        if (estado) {
            System.out.println("El empleado " + this.codigoEmpleado + " ahora está ACTIVO.");
        } else {
            System.out.println("El empleado " + this.codigoEmpleado + " ha sido DADO DE BAJA.");
        }
    }
}