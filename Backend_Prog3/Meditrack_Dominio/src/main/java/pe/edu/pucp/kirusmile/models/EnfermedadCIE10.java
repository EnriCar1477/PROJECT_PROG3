package pe.edu.pucp.kirusmile.models;

public class EnfermedadCIE10 {

    // --- ATRIBUTOS ---
    private int idEnfermedadCIE10;   // ID como tipo primitivo int
    private String codigoCIE;         // Ejemplo: "K02.1"
    private String descripcionOficial; // Ejemplo: "Caries de la dentina"

    // --- CONSTRUCTORES ---
    public EnfermedadCIE10() {
    }

    public EnfermedadCIE10(int idEnfermedadCIE10, String codigoCIE, String descripcionOficial) {
        this.idEnfermedadCIE10 = idEnfermedadCIE10;
        this.codigoCIE = codigoCIE;
        this.descripcionOficial = descripcionOficial;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdEnfermedadCIE10() {
        return idEnfermedadCIE10;
    }

    public void setIdEnfermedadCIE10(int idEnfermedadCIE10) {
        this.idEnfermedadCIE10 = idEnfermedadCIE10;
    }

    public String getCodigoCIE() {
        return codigoCIE;
    }

    public void setCodigoCIE(String codigoCIE) {
        this.codigoCIE = codigoCIE;
    }

    public String getDescripcionOficial() {
        return descripcionOficial;
    }

    public void setDescripcionOficial(String descripcionOficial) {
        this.descripcionOficial = descripcionOficial;
    }
}
