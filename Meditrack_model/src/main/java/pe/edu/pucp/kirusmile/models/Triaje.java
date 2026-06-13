package pe.edu.pucp.kirusmile.models;

public class Triaje {

    // --- ATRIBUTOS PROPIOS ---
    private int idTriaje;
    private double peso;
    private double talla;
    private String presionArterial;
    private double temperatura;
    private double saturacion;
    private boolean activo; // ¡NUEVO! Atributo para el borrado lógico

    // --- RELACIONES ---
    private DetalleHistorial detalleHistorial;

    // --- CONSTRUCTORES ---
    public Triaje() {
        this.activo = true; // Por defecto nace activo
    }

    public Triaje(double peso, double talla, String presionArterial, double temperatura, double saturacion) {
        this.peso = peso;
        this.talla = talla;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.saturacion = saturacion;
        this.activo = true;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdTriaje() {
        return idTriaje;
    }

    public void setIdTriaje(int idTriaje) {
        this.idTriaje = idTriaje;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getSaturacion() {
        return saturacion;
    }

    public void setSaturacion(double saturacion) {
        this.saturacion = saturacion;
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
