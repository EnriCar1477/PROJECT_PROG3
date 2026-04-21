package pe.edu.pucp.kirusmile.models;

public class EnfermedadCIE10 {
    private String codigocCIE;
    private String descripcionOficial;

    public EnfermedadCIE10(String codigocCIE, String descripcionOficial) {
        this.codigocCIE = codigocCIE;
        this.descripcionOficial = descripcionOficial;
    }

    public String getCodigocCIE() {
        return codigocCIE;
    }

    public void setCodigocCIE(String codigocCIE) {
        this.codigocCIE = codigocCIE;
    }

    public String getDescripcionOficial() {
        return descripcionOficial;
    }

    public void setDescripcionOficial(String descripcionOficial) {
        this.descripcionOficial = descripcionOficial;
    }
}
