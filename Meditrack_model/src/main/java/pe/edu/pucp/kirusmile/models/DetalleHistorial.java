package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetalleHistorial {

    // --- ATRIBUTOS PROPIOS ---
    private int idDetalle;               // ID como tipo primitivo int
    private boolean estaCerrada;         // Indica si la atención ya finalizó //solo si esta cerrada se pueden añadir notaAclaratoria
    private LocalDateTime fechaCierre;   // Fecha y hora exacta del cierre de la atención
    private String notaAclaratoria;      // Notas posteriores al cierre (si las hubiera)
    private boolean activo; // ¡NUEVO! Atributo para el borrado lógico

    // --- RELACIONES DE ASOCIACIÓN (1 a 1) ---
    private HistorialMedico historialMedico; // A qué historial pertenece
    private CitaMedica citaOrigen;           // De qué cita proviene esta atención
    private Triaje triaje;                   // Datos de signos vitales
    private Anamnesis anamnesis;             // Relato del paciente

    // --- RELACIONES DE COMPOSICIÓN (1 a Muchos) ---
    private List<Diagnostico> listaDiagnosticos;
    private List<Tratamiento> listaTratamientos;

    // --- CONSTRUCTORES ---
    public DetalleHistorial() {
        // Inicializamos las listas para evitar el temido NullPointerException
        this.listaDiagnosticos = new ArrayList<>();
        this.listaTratamientos = new ArrayList<>();
        this.estaCerrada = false; // Por defecto, una atención nace abierta
    }

    // --- GETTERS Y SETTERS ---


    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public boolean isEstaCerrada() {
        return estaCerrada;
    }

    public void setEstaCerrada(boolean estaCerrada) {
        this.estaCerrada = estaCerrada;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getNotaAclaratoria() {
        return notaAclaratoria;
    }

    public void setNotaAclaratoria(String notaAclaratoria) {
        this.notaAclaratoria = notaAclaratoria;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public CitaMedica getCitaOrigen() {
        return citaOrigen;
    }

    public void setCitaOrigen(CitaMedica citaOrigen) {
        this.citaOrigen = citaOrigen;
    }

    public Triaje getTriaje() {
        return triaje;
    }

    public void setTriaje(Triaje triaje) {
        this.triaje = triaje;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        this.anamnesis = anamnesis;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<Tratamiento> getListaTratamientos() {
        return listaTratamientos;
    }

    public void setListaTratamientos(List<Tratamiento> listaTratamientos) {
        this.listaTratamientos = listaTratamientos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /*
	
	Observación: Almacenas estaCerrada (boolean) y fechaCierre (LocalDateTime).

	Sugerencia: Está perfecto para la firma médica. Solo recuerda en tu Front-end que, si estaCerrada == true, 
	todos los campos de texto y botones de "Guardar" de esa consulta 
	deben bloquearse (ponerse en modo Read-Only).
	
	*/
	
	
	
}