package pe.edu.pucp.kirusmile.models;

public class Especialidad {
    // --- ATRIBUTOS ---
    private int idEspecialidad;         // ID como tipo primitivo int
    private String nombreEspecialidad;
    private double costoEspecialidad;
    private boolean activo;             // Flag para borrado lógico administrativo

    // --- CONSTRUCTORES ---
    public Especialidad() {
        this.activo = true; // Toda especialidad nueva nace activa por defecto
    }

    public Especialidad(String nombreEspecialidad, double costoEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
        this.costoEspecialidad = costoEspecialidad;
        this.activo = true;
    }

    // --- GETTERS Y SETTERS ---


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
        this.costoEspecialidad = costoEspecialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
