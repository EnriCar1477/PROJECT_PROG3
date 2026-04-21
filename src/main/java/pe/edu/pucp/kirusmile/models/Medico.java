package pe.edu.pucp.kirusmile.models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Empleado {

    // --- ATRIBUTOS ---
    private String cmp; // Colegio Médico del Perú
    private String rne; // Registro Nacional de Especialista
    private Especialidad especialidad; 
    private Date fechaIngreso;
    private String firmaDigital; // Token o hash para la firma electrónica (NTS 139)
    private List<HorarioDisponibilidad> listaHorarios;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        if (cmp == null || cmp.trim().isEmpty()) {
            throw new IllegalArgumentException("El CMP es obligatorio para todo médico.");
        }
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

    // --- CONSTRUCTORES ---

    public Medico() {
        super(); // Hereda los atributos vacíos de Empleado y Persona
        this.listaHorarios = new ArrayList<>();
    }

    public Medico(String dni, String nombres, String apellidoPaterno, String apellidoMaterno, 
                  String codigoEmpleado, Date fechaVinculacion,
                  String cmp, String rne, Especialidad especialidad, Date fechaIngreso) {
        // Llama al constructor de la superclase Empleado
        super(dni, nombres, apellidoPaterno, apellidoMaterno, codigoEmpleado, fechaVinculacion);
        this.setCmp(cmp); // Validación incluida
        this.rne = rne;
        this.especialidad = especialidad;
        this.fechaIngreso = fechaIngreso;
        this.listaHorarios = new ArrayList<>();
        this.firmaDigital = "PENDIENTE";
    }

    // --- MÉTODOS ---

    /**
     * Registra de forma masiva las credenciales del médico.
     * Ideal para cuando el Administrador crea el perfil por primera vez.
     */
    public void registrarCredenciales(String cmp, String rne, Especialidad especialidad) {
        this.setCmp(cmp);
        this.rne = rne;
        this.especialidad = especialidad;
        System.out.println("Credenciales registradas con éxito para el CMP: " + cmp);
    }

    /**
     * Valida si el médico cuenta con una especialidad formal y su respectivo 
     * Registro Nacional de Especialista (RNE). Útil para derivaciones médicas.
     */
    public boolean validarEspecialidad() {
        return this.especialidad != null && this.rne != null && !this.rne.trim().isEmpty();
    }

    /**
     * Genera el bloque de texto legal que acompañará al diagnóstico o receta.
     * Requisito fundamental para la validez del acto médico.
     */
    public String obtenerFirmaLegal() {
        String tituloEspecialidad = (this.especialidad != null) ? this.especialidad.getNombre() : "Médico General";
        return String.format("Dr(a). %s\n%s\nCMP: %s | RNE: %s\nFirma Digital: %s", 
                this.obtenerNombreCompleto(), // Método heredado de Persona
                tituloEspecialidad, 
                this.cmp, 
                (this.rne != null ? this.rne : "N/A"),
                this.firmaDigital);
    }

    /**
     * Actualiza el token de seguridad de la firma digital.
     */
    public void actualizarFirmaDigital(String nuevoToken) {
        if (nuevoToken == null || nuevoToken.trim().isEmpty()) {
            throw new IllegalArgumentException("El token de la firma no puede ser nulo.");
        }
        this.firmaDigital = nuevoToken;
    }

    /**
     * Este método cumple con la recomendación del JP: el sistema toma el costo
     * desde la Entidad Especialidad en lugar de quemarlo en el código.
     */
    public double obtenerTarifaConsulta() {
        if (this.especialidad != null) {
            return this.especialidad.getCostoConsulta();
        }
        // Si no tiene especialidad asignada, se podría retornar una tarifa base por defecto
        return 50.00; 
    }
}