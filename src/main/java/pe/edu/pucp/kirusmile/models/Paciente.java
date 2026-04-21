package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Paciente extends Persona {

    // --- ATRIBUTOS (NTS 139 - Num. 6.1.2 y Anexo 2) ---
    private String grupoSanguineo;
    private String factorRh;
    private String gradoInstruccion;
    private String ocupacion;
    private String etnia;
    private HistorialMedico historial; // Relación 1 a 1 con su historial

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        // Ejemplo: A, B, AB, O
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactorRh() {
        return factorRh;
    }

    public void setFactorRh(String factorRh) {
        // Ejemplo: Positivo (+), Negativo (-)
        this.factorRh = factorRh;
    }

    public String getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(String gradoInstruccion) {
        // Primaria, Secundaria, Superior, etc.
        this.gradoInstruccion = gradoInstruccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        // Mestizo, Quechua, Afrodescendiente, etc.
        this.etnia = etnia;
    }

    public HistorialMedico getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialMedico historial) {
        this.historial = historial;
    }

    // --- CONSTRUCTORES ---

    public Paciente() {
        super(); // Llama al constructor de Persona
    }

    public Paciente(String dni, String nombres, String apellidoPaterno,String apellidoMaterno, String grupoSanguineo, String factorRh) {
        super(dni, nombres, apellidoPaterno,apellidoMaterno); // Datos heredados de Persona
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
    }

    // --- MÉTODOS ---

    /**
     * Según la NTS 139, al registrar un paciente nuevo se debe
     * generar automáticamente su Historial Clínico.
     */
    public void generarHistorialAutomatico() {
        if (this.historial == null) {
            this.historial = new HistorialMedico();
            System.out.println("Se ha generado el Historial Clínico N° " + this.getDni());
        }
    }

    public String obtenerNombreCompleto() {
        return this.getNombres() + " " + this.getApellidoPaterno() + " " +this.getApellidoMaterno();
    }

    public boolean tieneHistorialActivo() {
        return this.historial != null;
    }
}