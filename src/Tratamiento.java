import java.util.Date;

public class Tratamiento{//TRATAMIENTO
	private int id;
	private String nombreMedicamento;
	private String dosis;
	private String frecuencia;
	private Date fechaInicio;
	private Date fechaFin;
	// O puede ser, en vez de fecha inicio y fin, periodo o intervalo de tiempo
	private int idCita; // y cómo se explica esto? xd
	
	public Tratamiento(int id,String nombreMedicamento,String dosis,String frecuencia,Date fechaInicio,Date fechaFin,int idCita) {
        this.id=id;
        this.nombreMedicamento=nombreMedicamento;
        this.dosis=dosis;
        this.frecuencia=frecuencia;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.idCita=idCita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }
	
	void emitirTratamiento(){ // iría realmente esto aquí?
		
	}
	
	void refillPrescription(){ // Esto qué es?
		
	}
}