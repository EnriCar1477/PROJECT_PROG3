package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;

public class CuentaUsuario {

    // --- ATRIBUTOS ---
    private String username;
    private String passwordHash; // Contraseña encriptada por seguridad
    private RolUsuario rol; // Asumiendo que es un Enum (MEDICO, SECRETARIA, ADMIN)
    private LocalDateTime ultimoAcceso;

    // --- GETTERS Y SETTERS INTERCALADOS ---

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

    // --- CONSTRUCTORES ---

    public CuentaUsuario() {
        // Constructor vacío
    }

    public CuentaUsuario(String username, String passwordHash, RolUsuario rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.ultimoAcceso = null; // Aún no ha iniciado sesión por primera vez
    }
}