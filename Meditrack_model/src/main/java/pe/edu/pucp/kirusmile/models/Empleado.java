package pe.edu.pucp.kirusmile.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Empleado extends Persona {

    // --- ATRIBUTOS LABORALES ---
    private int idEmpleado;
    private String codigoEmpleado;
    private LocalDate fechaVinculacion;
    private boolean estadoLaboral;
    private boolean activo; // NUEVO: Para el borrado lógico (TINYINT(1) en MySQL)

    // --- DATOS DE: CUENTAUSUARIO---
    private String username;
    private String passwordHash;
    private RolUsuario rol;
    private LocalDateTime ultimoAcceso;
    private List<LogAuditoria> auditorias;

    // --- CONSTRUCTORES ---
    public Empleado() {
        super(); // Llama al constructor de Persona
        this.auditorias = new ArrayList<>();
        this.estadoLaboral = true; // Al registrar un nuevo empleado, entra trabajando
    }

    public Empleado(String codigoEmpleado, LocalDate fechaVinculacion, boolean estadoLaboral,
                    String username, String passwordHash, RolUsuario rol) {
        super();
        this.codigoEmpleado = codigoEmpleado;
        this.fechaVinculacion = fechaVinculacion;
        this.estadoLaboral = estadoLaboral;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.auditorias = new ArrayList<>();
    }

    // --- GETTERS Y SETTERS ---


    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public LocalDate getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDate fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public boolean isEstadoLaboral() {
        return estadoLaboral;
    }

    public void setEstadoLaboral(boolean estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public List<LogAuditoria> getAuditorias() {
        return auditorias;
    }

    public void setAuditorias(List<LogAuditoria> auditorias) {
        this.auditorias = auditorias;
    }
}