package pe.edu.pucp.kirusmile.models;

public class Triaje {
    private double peso;
    private double talla;
    private String presionArterial;
    private double temperatura;
    private double saturacion;
  
    public Triaje(double talla, double peso, String presionArterial, double temperatura, double saturacion) {
        this.talla = talla;
        this.peso = peso;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.saturacion = saturacion;
    }

    public double getSaturacion() {
        return saturacion;
    }

    public void setSaturacion(double saturacion) {
        this.saturacion = saturacion;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
