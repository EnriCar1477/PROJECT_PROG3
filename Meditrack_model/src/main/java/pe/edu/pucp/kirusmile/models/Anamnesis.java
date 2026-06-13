package pe.edu.pucp.kirusmile.models;

public class Anamnesis {

    // --- ATRIBUTOS PROPIOS ---
    private int idAnamnesis;
    private String motivoPrincipal;
    private String tiempoEnfermedad;
    private String formaInicio;
    private String relatoClinico;
    private String antecedentesImportantes;
    private boolean activo;

    private DetalleHistorial detalleHistorial;

    // --- CONSTRUCTORES ---
    public Anamnesis() {
        this.activo = true;
    }

    public Anamnesis(String motivoPrincipal, String tiempoEnfermedad, String formaInicio,
                     String relatoClinico, String antecedentesImportantes) {
        this.motivoPrincipal = motivoPrincipal;
        this.tiempoEnfermedad = tiempoEnfermedad;
        this.formaInicio = formaInicio;
        this.relatoClinico = relatoClinico;
        this.antecedentesImportantes = antecedentesImportantes;
    }

    // --- GETTERS Y SETTERS ---


    public int getIdAnamnesis() {
        return idAnamnesis;
    }

    public void setIdAnamnesis(int idAnamnesis) {
        this.idAnamnesis = idAnamnesis;
    }

    public String getMotivoPrincipal() {
        return motivoPrincipal;
    }

    public void setMotivoPrincipal(String motivoPrincipal) {
        this.motivoPrincipal = motivoPrincipal;
    }

    public String getTiempoEnfermedad() {
        return tiempoEnfermedad;
    }

    public void setTiempoEnfermedad(String tiempoEnfermedad) {
        this.tiempoEnfermedad = tiempoEnfermedad;
    }

    public String getFormaInicio() {
        return formaInicio;
    }

    public void setFormaInicio(String formaInicio) {
        this.formaInicio = formaInicio;
    }

    public String getRelatoClinico() {
        return relatoClinico;
    }

    public void setRelatoClinico(String relatoClinico) {
        this.relatoClinico = relatoClinico;
    }

    public String getAntecedentesImportantes() {
        return antecedentesImportantes;
    }

    public void setAntecedentesImportantes(String antecedentesImportantes) {
        this.antecedentesImportantes = antecedentesImportantes;
    }

    public DetalleHistorial getDetalleHistorial() {
        return detalleHistorial;
    }

    public void setDetalleHistorial(DetalleHistorial detalleHistorial) {
        this.detalleHistorial = detalleHistorial;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
