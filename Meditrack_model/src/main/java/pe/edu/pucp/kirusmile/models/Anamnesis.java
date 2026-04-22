package pe.edu.pucp.kirusmile.models;

public class Anamnesis {
    private Integer idAnamnesis;
    private String motivoPrincipal;
    private String tiempoEnfermedad;
    private String formaInicio;
    private String relatoClinico;
    private String antecedentesImportantes;
    private boolean desactivado;

    public Anamnesis(Integer idAnamnesis, String motivoPrincipal, String tiempoEnfermedad, String formaInicio, String relatoClinico, String antecedentesImportantes, boolean desactivado) {
        this.idAnamnesis = idAnamnesis;
        this.motivoPrincipal = motivoPrincipal;
        this.tiempoEnfermedad = tiempoEnfermedad;
        this.formaInicio = formaInicio;
        this.relatoClinico = relatoClinico;
        this.antecedentesImportantes = antecedentesImportantes;
        this.desactivado = desactivado;
    }

    public Integer getIdAnamnesis() { return idAnamnesis; }
    public void setIdAnamnesis(Integer idAnamnesis) { this.idAnamnesis = idAnamnesis; }

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

    public boolean getDesactivado() { return desactivado; }
    public void setDesactivado(boolean desactivado) { this.desactivado = desactivado; }
}
