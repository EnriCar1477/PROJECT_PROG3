package pe.edu.pucp.kirusmile.models;

public class Especialidad {
    private int idEspecialidad;
    private String nombreEspecialidad;
    private double costoEspecialidad;
    private boolean activo;

    public Especialidad(int idEspecialidad, String nombreEspecialidad, double costoEspecialidad, boolean activo) {
        this.idEspecialidad = idEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
        this.costoEspecialidad = costoEspecialidad;
        this.activo = activo;
    }

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

    /*public double obtenerCostoBase(){

    }*/
}
