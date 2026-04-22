package pe.edu.pucp.kirusmile.models;

public class Triaje {
    private Integer idTriaje;
    private double peso;
    private double talla;
    private String presionArterial;
    private double temperatura;
    private double saturacion;
    private boolean desactivado;
  
    public Triaje(Integer idTriaje, double talla, double peso, String presionArterial, double temperatura, double saturacion, boolean desactivado) {
        this.idTriaje = idTriaje;
        this.talla = talla;
        this.peso = peso;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.saturacion = saturacion;
        this.desactivado = desactivado;
    }

    public Integer getIdTriaje() { return idTriaje; }
    public void setIdTriaje(Integer idTriaje) { this.idTriaje = idTriaje; }

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

    public boolean getDesactivado() { return desactivado; }
    public void setDesactivado(boolean desactivado) { this.desactivado = desactivado; }
}
