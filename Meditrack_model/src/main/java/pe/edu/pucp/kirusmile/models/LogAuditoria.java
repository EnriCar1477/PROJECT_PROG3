package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogAuditoria {

    // --- ATRIBUTOS PROPIOS ---
    private int idLogAuditoria;
    private LocalDateTime fechaHora;     // Usamos LocalDateTime para tener la estampa de tiempo exacta
    private String accionRealizada;      // Ej: "Login exitoso", "Registro de nueva cita ID 5"
    private String ipTerminal;           // Ej: "192.168.1.15"

    // --- RELACIONES ---
    private Empleado empleado;

    // --- CONSTRUCTORES ---
    public LogAuditoria() {
        // Captura automáticamente el instante en que ocurrió el evento
        this.fechaHora = LocalDateTime.now();
    }

    public LogAuditoria(String accionRealizada, String ipTerminal) {
        this.accionRealizada = accionRealizada;
        this.ipTerminal = ipTerminal;
        // Capturamos el instante exacto automáticamente para evitar manipulaciones
        this.fechaHora = LocalDateTime.now();
    }

    // --- GETTERS Y SETTERS ---


    public int getIdLogAuditoria() {
        return idLogAuditoria;
    }

    public void setIdLogAuditoria(int idLogAuditoria) {
        this.idLogAuditoria = idLogAuditoria;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getAccionRealizada() {
        return accionRealizada;
    }

    public void setAccionRealizada(String accionRealizada) {
        this.accionRealizada = accionRealizada;
    }

    public String getIpTerminal() {
        return ipTerminal;
    }

    public void setIpTerminal(String ipTerminal) {
        this.ipTerminal = ipTerminal;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
	
	/*
	
	Sobre la Inmutabilidad en LogAuditoria.java
	
	Observación: Tienes métodos Setters para fechaHora, accionRealizada y ipTerminal.

	Recomendación Experta: Como hablamos antes, un Log de Auditoría jamás debe modificarse. 
	Para ser 100% estrictos con la seguridad, podrías borrar los métodos set de esta clase. 
	De esa forma, obligas a que los datos solo nazcan a través del constructor y el propio lenguaje 
	Java bloqueará cualquier intento de alterarlos en el futuro. (Nota: Si tu framework o DAO te exige 
	tener setters para sacar los datos de la base de datos, déjalos, pero ten cuidado de no usarlos en el BL).
	
	
	
	
	
	*/
	
	
	
	
	
	
	
	
}