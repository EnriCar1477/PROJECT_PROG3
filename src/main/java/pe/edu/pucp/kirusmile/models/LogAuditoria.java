package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogAuditoria {

    // --- ATRIBUTOS ---
    private int idLog;
    private LocalDateTime fechaHora;
    private String accionRealizada;
    private String ipTerminal;

    // --- GETTERS Y SETTERS INTERCALADOS ---

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        // En un sistema real de auditoría, este setter rara vez se usa 
        // para evitar que alguien altere la fecha del log.
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

    // --- CONSTRUCTORES ---

    public LogAuditoria() {
        // Constructor vacío
    }

    public LogAuditoria(String accionRealizada, String ipTerminal) {
        this.idLog = (int) (Math.random() * 10000); // Simulando generación de ID en base de datos
        this.fechaHora = LocalDateTime.now(); // Captura el momento exacto e inalterable
        this.accionRealizada = accionRealizada;
        this.ipTerminal = ipTerminal;
    }

    // --- MÉTODOS ---

    /**
     * Registra una nueva acción en el sistema capturando el momento exacto.
     * Este método es vital para rastrear quién hizo qué (ej. "Secretaria canceló cita").
     */
    public void registrarAccion(String accion, String ip) {
        if (accion == null || accion.trim().isEmpty()) {
            throw new IllegalArgumentException("La acción a auditar no puede estar vacía.");
        }
        
        this.fechaHora = LocalDateTime.now();
        this.accionRealizada = accion;
        this.ipTerminal = ip;
        
        System.out.println("Log registrado: " + obtenerResumenLog());
    }

    /**
     * Genera una cadena de texto formateada con el resumen del evento 
     * ideal para ser exportado a un reporte de seguridad o consola.
     */
    public String obtenerResumenLog() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = (this.fechaHora != null) ? this.fechaHora.format(formato) : "Desconocida";
        
        return String.format("[LOG-%04d] %s | IP: %s | Acción: %s", 
                this.idLog, 
                fechaFormateada, 
                (this.ipTerminal != null ? this.ipTerminal : "Desconocida"), 
                this.accionRealizada);
    }
}