package pe.edu.pucp.kirusmile.models;

public class Anamnesis {

    // --- ATRIBUTOS ---
    private String motivoPrincipal;
    private String tiempoEnfermedad; // Ej: "3 días", "2 semanas"
    private String formaInicio; // Ej: "Insidioso", "Brusco"
    private String relatoClinico; // La narración de los síntomas
    private String antecedentesImportantes; // Alergias, cirugías previas, etc.

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getMotivoPrincipal() {
        return motivoPrincipal;
    }

    public void setMotivoPrincipal(String motivoPrincipal) {
        if (motivoPrincipal == null || motivoPrincipal.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo principal no puede estar vacío.");
        }
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
        if (relatoClinico == null || relatoClinico.trim().isEmpty()) {
            throw new IllegalArgumentException("El relato clínico es obligatorio según la NTS 139.");
        }
        this.relatoClinico = relatoClinico;
    }

    public String getAntecedentesImportantes() {
        return antecedentesImportantes;
    }

    public void setAntecedentesImportantes(String antecedentesImportantes) {
        this.antecedentesImportantes = antecedentesImportantes;
    }

    // --- CONSTRUCTORES ---

    public Anamnesis() {
        // Constructor vacío
        this.antecedentesImportantes = "Ninguno conocido"; // Valor por defecto seguro
    }

    public Anamnesis(String motivoPrincipal, String tiempoEnfermedad, String formaInicio, 
                     String relatoClinico, String antecedentesImportantes) {
        this.setMotivoPrincipal(motivoPrincipal); // Usamos el setter para aprovechar su validación
        this.tiempoEnfermedad = tiempoEnfermedad;
        this.formaInicio = formaInicio;
        this.setRelatoClinico(relatoClinico); // Usamos el setter para aprovechar su validación
        this.antecedentesImportantes = antecedentesImportantes;
    }

    // --- MÉTODOS ---

    /**
     * Valida que la anamnesis esté completa antes de permitir que el 
     * DetalleHistorial se bloquee/cierre.
     */
    public boolean estaCompleta() {
        return this.motivoPrincipal != null && !this.motivoPrincipal.trim().isEmpty() &&
               this.relatoClinico != null && !this.relatoClinico.trim().isEmpty();
    }

    /**
     * Genera un resumen estructurado para ser mostrado en la vista (Front-end)
     * o para ser impreso en la receta/historia.
     */
    public String obtenerResumen() {
        return String.format("Motivo: %s | Tiempo enf: %s | Inicio: %s\nRelato: %s\nAntecedentes: %s", 
                this.motivoPrincipal, 
                (this.tiempoEnfermedad != null ? this.tiempoEnfermedad : "No especificado"), 
                (this.formaInicio != null ? this.formaInicio : "No especificado"), 
                this.relatoClinico, 
                this.antecedentesImportantes);
    }
}