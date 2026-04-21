package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetalleHistorial {

    // --- ATRIBUTOS ---
    private int idDetalle;
    private boolean estaCerrada;
    private LocalDateTime fechaCierre;
    private String notaAclaratoria;
    private CitaMedica citaOrigen;
    private Triaje triaje;
    private Anamnesis anamnesis;
    private List<Diagnostico> listaDiagnosticos;
    private List<Tratamiento> listaTratamientos;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.idDetalle = idDetalle;
    }

    public boolean getEstaCerrada() {
        return estaCerrada;
    }

    public void setEstaCerrada(boolean estaCerrada) {
        // Este setter permite cambiar el estado, aunque lo ideal es usar bloquearDetalle()
        this.estaCerrada = estaCerrada;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.fechaCierre = fechaCierre;
    }

    public String getNotaAclaratoria() {
        return notaAclaratoria;
    }

    public void setNotaAclaratoria(String notaAclaratoria) {
        // Nota: Según la lógica, este es el único que podría editarse tras el cierre
        this.notaAclaratoria = notaAclaratoria;
    }

    public CitaMedica getCitaOrigen() {
        return citaOrigen;
    }

    public void setCitaOrigen(CitaMedica citaOrigen) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.citaOrigen = citaOrigen;
    }

    public Triaje getTriaje() {
        return triaje;
    }

    public void setTriaje(Triaje triaje) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.triaje = triaje;
    }

    public Anamnesis getAnamnesis() {
        return anamnesis;
    }

    public void setAnamnesis(Anamnesis anamnesis) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.anamnesis = anamnesis;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<Tratamiento> getListaTratamientos() {
        return listaTratamientos;
    }

    public void setListaTratamientos(List<Tratamiento> listaTratamientos) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.listaTratamientos = listaTratamientos;
    }

    // --- CONSTRUCTORES ---

    public DetalleHistorial() {
        // Constructor vacío sugerido por la rúbrica
        this.estaCerrada = false;
        this.listaDiagnosticos = new ArrayList<>();
        this.listaTratamientos = new ArrayList<>();
        this.notaAclaratoria = "";
    }

    public DetalleHistorial(CitaMedica citaOrigen) {
        this.idDetalle = (int) (Math.random() * 1000);
        this.citaOrigen = citaOrigen;
        this.estaCerrada = false;
        this.listaDiagnosticos = new ArrayList<>();
        this.listaTratamientos = new ArrayList<>();
        this.notaAclaratoria = "";
    }

    // --- MÉTODOS ---

    public void agregarDiagnostico(Diagnostico d) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.listaDiagnosticos.add(d);
    }

    public void agregarTratamiento(Tratamiento t) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.listaTratamientos.add(t);
    }

    public void agregarTriaje(Triaje t) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.triaje = t;
    }

    public void agregarAnamnesis(Anamnesis a) {
        if (this.estaCerrada) throw new IllegalStateException("Registro cerrado.");
        this.anamnesis = a;
    }

    public void bloquearDetalle() {
        // Cambia el estado a verdadero y sella la fecha de cierre
        this.estaCerrada = true;
        this.fechaCierre = LocalDateTime.now();
    }

    public void redactarAclaratoria(String nota) {
        // Permite agregar información extra solo si el registro ya está cerrado
        if (!this.estaCerrada) {
            throw new IllegalStateException("Solo se pueden redactar aclaratorias en registros cerrados.");
        }
        this.notaAclaratoria += "\n[" + LocalDateTime.now() + "]: " + nota;
    }
}