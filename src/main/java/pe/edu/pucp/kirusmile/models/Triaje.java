package pe.edu.pucp.kirusmile.models;

public class Triaje {

    // --- ATRIBUTOS ---
    private double peso; // en kilogramos (kg)
    private double talla; // en metros (m)
    private String presionArterial; // ej. "120/80"
    private double temperatura; // en grados Celsius (°C)
    private double saturacion; // en porcentaje (%)

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0.");
        }
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        if (talla <= 0) {
            throw new IllegalArgumentException("La talla debe ser mayor a 0.");
        }
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
        if (saturacion < 0 || saturacion > 100) {
            throw new IllegalArgumentException("La saturación debe estar entre 0% y 100%.");
        }
        this.saturacion = saturacion;
    }

    // --- CONSTRUCTORES ---

    public Triaje() {
        // Constructor vacío
    }

    public Triaje(double peso, double talla, String presionArterial, double temperatura, double saturacion) {
        this.peso = peso;
        this.talla = talla;
        this.presionArterial = presionArterial;
        this.temperatura = temperatura;
        this.saturacion = saturacion;
    }

    // --- MÉTODOS ---

    /**
     * Calcula el Índice de Masa Corporal (IMC) del paciente.
     * Fórmula: peso / (talla * talla)
     */
    public double calcularIMC() {
        if (this.talla > 0) {
            double imc = this.peso / (this.talla * this.talla);
            return Math.round(imc * 100.0) / 100.0; // Retorna con 2 decimales
        }
        return 0.0;
    }

    /**
     * Evalúa si la temperatura registrada indica fiebre.
     * Según parámetros médicos generales, > 38.0°C es fiebre.
     */
    public boolean tieneFiebre() {
        return this.temperatura > 38.0;
    }

    /**
     * Genera un resumen rápido de los signos vitales para la vista de la historia clínica.
     */
    public String obtenerResumenTriaje() {
        return String.format("Peso: %.2f kg | Talla: %.2f m | PA: %s | Temp: %.1f °C | Sat: %.1f %%", 
                this.peso, this.talla, this.presionArterial, this.temperatura, this.saturacion);
    }
}