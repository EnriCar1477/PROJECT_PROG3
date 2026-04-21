package pe.edu.pucp.kirusmile.models;

public class EnfermedadCIE10 {

    // --- ATRIBUTOS ---
    private String codigoCIE; // Ej: "J00", "E11.9"
    private String descripcionOficial; // Ej: "Rinofaringitis aguda [resfriado común]"

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public String getCodigoCIE() {
        return codigoCIE;
    }

    public void setCodigoCIE(String codigoCIE) {
        if (codigoCIE == null || codigoCIE.trim().isEmpty()) {
            throw new IllegalArgumentException("El código CIE-10 no puede estar vacío.");
        }
        this.codigoCIE = codigoCIE;
    }

    public String getDescripcionOficial() {
        return descripcionOficial;
    }

    public void setDescripcionOficial(String descripcionOficial) {
        if (descripcionOficial == null || descripcionOficial.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción oficial de la enfermedad es obligatoria.");
        }
        this.descripcionOficial = descripcionOficial;
    }

    // --- CONSTRUCTORES ---

    public EnfermedadCIE10() {
        // Constructor vacío para frameworks o base de datos
    }

    public EnfermedadCIE10(String codigoCIE, String descripcionOficial) {
        this.setCodigoCIE(codigoCIE); // Usamos los setters para aprovechar las validaciones
        this.setDescripcionOficial(descripcionOficial);
    }

    // --- MÉTODOS ---

    /**
     * Retorna la representación formateada de la enfermedad, ideal 
     * para mostrarla en las listas desplegables del Front-end o en reportes.
     */
    public String obtenerNombreFormateado() {
        return String.format("[%s] - %s", this.codigoCIE, this.descripcionOficial);
    }
}