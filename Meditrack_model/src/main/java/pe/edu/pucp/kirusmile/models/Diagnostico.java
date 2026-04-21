package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Diagnostico{
	private int idDiagnostico;
	private String descripcion;
	private String gravedad; // Podriamos crear un enum TIPO_GRAVEDAD: leve, INTERMEDIO, grave, muy grave
	private Date fechaDiagnostico;
	
	// cita y diagnóstico y si debería ir aquí la cita
	
	public Diagnostico(int idDiagnostico,String descripcion,String gravedad,Date fechaDiagnostico,Cita cita) {
        this.idDiagnostico=idDiagnostico;
        this.descripcion=descripcion;
        this.gravedad=gravedad;
        this.fechaDiagnostico=fechaDiagnostico;
//        this.cita=cita;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(int idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

//    public pe.edu.pucp.kirusmile.models.Cita getCita() {
//        return cita;
//    }
//
//    public void setCita(pe.edu.pucp.kirusmile.models.Cita cita) {
//        this.cita = cita;
//    }
	
	public void agregarDescripcion(){
		
	}
	
	public void actualizarDescripcion(){
		
	}
	
	
}