package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Paciente extends Persona {
    // --- ATRIBUTOS PROPIOS ---
    private int idPaciente;
    private String grupoSanguineo;
    private String factorRh;
    private String gradoInstruccion;
    private String ocupacion;
    private String etnia;

    // --- CONSTRUCTORES ---
    public Paciente() {
        super(); // Llama al constructor vacío de Persona
    }

    public Paciente(String grupoSanguineo, String factorRh, String gradoInstruccion, String ocupacion) {
        super(); // Llama al constructor de Persona (luego le pasaremos los datos correspondientes)
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
        this.gradoInstruccion = gradoInstruccion;
        this.ocupacion = ocupacion;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactorRh() {
        return factorRh;
    }

    public void setFactorRh(String factorRh) {
        this.factorRh = factorRh;
    }

    public String getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(String gradoInstruccion) {
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
        this.etnia = etnia;
    }
}
