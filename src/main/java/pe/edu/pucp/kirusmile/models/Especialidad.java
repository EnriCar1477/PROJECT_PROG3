package pe.edu.pucp.kirusmile.models;

public class Especialidad {

    // --- ATRIBUTOS ---
    private int idEspecialidad;
    private String nombreEspecialidad;
    private double costoEspecialidad;
    private boolean activo;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public double getCostoEspecialidad() {
        return costoEspecialidad;
    }

    public void setCostoEspecialidad(double costoEspecialidad) {
        if (costoEspecialidad < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        this.costoEspecialidad = costoEspecialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // --- CONSTRUCTORES ---

    /**
     * Constructor privado solicitado. 
     * Útil si se desea controlar la creación mediante métodos de fábrica (Factory).
     */
    private Especialidad(double costo) {
        this.costoEspecialidad = costo;
        this.activo = true;
    }

    /**
     * Constructor público para uso general del sistema.
     */
    public Especialidad(int id, String nombre, double costo) {
        this.idEspecialidad = id;
        this.nombreEspecialidad = nombre;
        this.costoEspecialidad = costo;
        this.activo = true;
    }

    // --- MÉTODOS ---

    /**
     * Retorna el costo base de la consulta para esta especialidad.
     * Este valor es el que consumirá la clase CitaMedica.
     */
    public double obtenerCostoBase() {
        if (!this.activo) {
            System.out.println("Advertencia: La especialidad no está activa actualmente.");
            return 0.0;
        }
        return this.costoEspecialidad;
    }

    /**
     * Método para desactivar una especialidad sin borrarla de la base de datos,
     * manteniendo así la integridad de los historiales antiguos.
     */
    public void darDeBaja() {
        this.activo = false;
        System.out.println("Especialidad " + nombreEspecialidad + " desactivada.");
    }
}