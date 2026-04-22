package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Paciente extends Persona {
    private String grupoSanguineo;
    private String factorRh;// ACTIVO(ES PACIENTE REGISTRADO), INACTIVO(NO REGISTRADO), BETADO (NO ESTA
                            // PERMITIDO)
    private String gradoInstruccion;
    private String ocupacion;
    private String etnia;

    public Paciente(String dni, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento,
            String telefono, String correo, String grupoSanguineo, String factorRh, String gradoInstruccion,
            String ocupacion, String etnia) {
        super(dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo);
        this.grupoSanguineo = grupoSanguineo;
        this.factorRh = factorRh;
        this.gradoInstruccion = gradoInstruccion;
        this.ocupacion = ocupacion;
        this.etnia = etnia;
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

    /*
     * @Override
     * public void mostrarDatos(){
     * 
     * }
     */
}
