package pe.edu.pucp.kirusmile.models;

import java.time.LocalDateTime;

public class CuentaUsuario {

    // --- ATRIBUTOS ---
    private String username;
    private String passwordHash; // Contraseña encriptada por seguridad
    private RolUsuario rol; // Asumiendo que es un Enum (MEDICO, SECRETARIA, ADMIN)
    private LocalDateTime ultimoAcceso;
    private int intentosFallidos;
    private boolean estaBloqueado;

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

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public boolean isEstaBloqueado() {
        return estaBloqueado;
    }

    public void setEstaBloqueado(boolean estaBloqueado) {
        this.estaBloqueado = estaBloqueado;
    }

    // --- CONSTRUCTORES ---

    public CuentaUsuario() {
        // Constructor vacío
        this.intentosFallidos = 0;
        this.estaBloqueado = false;
    }

    public CuentaUsuario(String username, String passwordHash, RolUsuario rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.intentosFallidos = 0;
        this.estaBloqueado = false;
        this.ultimoAcceso = null; // Aún no ha iniciado sesión por primera vez
    }

    // --- MÉTODOS ---

    /**
     * Registra un inicio de sesión exitoso, reseteando los fallos
     * y actualizando la marca de tiempo de su último acceso.
     */
    public void registrarAccesoExitoso() {
        if (this.estaBloqueado) {
            throw new SecurityException("Error: La cuenta está bloqueada. Contacte al administrador.");
        }
        this.intentosFallidos = 0;
        this.ultimoAcceso = LocalDateTime.now();
        System.out.println("Acceso concedido para el usuario: " + this.username);
    }

    /**
     * Registra un intento fallido de contraseña. Si llega a 3 intentos,
     * la cuenta se bloquea automáticamente por motivos de seguridad.
     */
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
        System.out.println("Intento fallido registrado. Total de fallos: " + this.intentosFallidos);
        
        // Lógica de seguridad (Bloqueo tras 3 intentos)
        if (this.intentosFallidos >= 3) {
            this.estaBloqueado = true;
            System.out.println("ALERTA DE SEGURIDAD: La cuenta '" + this.username + "' ha sido BLOQUEADA por múltiples intentos fallidos.");
        }
    }

    /**
     * Método exclusivo para que un Administrador devuelva el acceso al usuario.
     */
    public void desbloquearCuenta() {
        this.estaBloqueado = false;
        this.intentosFallidos = 0;
        System.out.println("La cuenta '" + this.username + "' ha sido desbloqueada exitosamente.");
    }
}