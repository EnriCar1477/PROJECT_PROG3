package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IEmpleadoBL;
import pe.edu.pucp.kirusmile.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.LogAuditoriaDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.EmpleadoDAO;
import pe.edu.pucp.kirusmile.dao.inter.LogAuditoriaDAO;
import pe.edu.pucp.kirusmile.models.Empleado;

import java.time.LocalDateTime;
import java.util.List;

public class EmpleadoBLImpl implements IEmpleadoBL {

    private EmpleadoDAO empleadoDAO;
    private LogAuditoriaDAO auditoriaDAO;

    public EmpleadoBLImpl() {
        this.empleadoDAO = new EmpleadoDAOImpl();
        this.auditoriaDAO = new LogAuditoriaDAOImpl();
    }

    @Override
    public int registrar(Empleado empleado) {
        if (!validarDatosPersonales(empleado) || !validarDatosLaborales(empleado)) {
            return 0; // Bloqueamos si fallan las validaciones
        }

        // Regla 1: El DNI debe ser único
        Empleado existenteDNI = empleadoDAO.obtenerPorDni(empleado.getDni());
        if (existenteDNI != null) {
            System.err.println("Error BL: Ya existe un empleado registrado con el DNI " + empleado.getDni());
            return 0; // Bloqueamos la creación
        }

        // Regla 2: El Username debe ser estrictamente ÚNICO
        Empleado existenteUser = empleadoDAO.obtenerPorUsername(empleado.getUsername());
        if (existenteUser != null) {
            System.err.println("Error BL: El nombre de usuario '" + empleado.getUsername() + "' ya está en uso.");
            return 0;
        }

        // Seguridad: Aquí deberías encriptar la contraseña antes de mandarla al DAO.
        // Por ejemplo, usando una librería como BCrypt:
        // String hash = BCrypt.hashpw(empleado.getPasswordHash(), BCrypt.gensalt());
        // empleado.setPasswordHash(hash);

        // Por defecto, entra como laborando
        empleado.setEstadoLaboral(true);

        return empleadoDAO.save(empleado);
    }

    @Override
    public int actualizar(Empleado empleado) {
        if (empleado.getIdEmpleado() == 0) {
            System.err.println("Error BL: No se puede actualizar un empleado sin ID.");
            return 0;
        }

        if (!validarDatosPersonales(empleado) || !validarDatosLaborales(empleado)) {
            return 0;
        }

        // --- NUEVA REGLA (Agregada para evitar caídas de BD por DNI duplicado) ---
        Empleado existenteDNI = empleadoDAO.obtenerPorDni(empleado.getDni());
        if (existenteDNI != null && existenteDNI.getIdEmpleado() != empleado.getIdEmpleado()) {
            System.err.println("Error BL: El DNI ingresado ya pertenece a otro empleado en el sistema.");
            return 0;
        }

        // Regla: Si cambia su username, verificar que no le quite el de otro
        Empleado existenteUser = empleadoDAO.obtenerPorUsername(empleado.getUsername());
        if (existenteUser != null && existenteUser.getIdEmpleado() != empleado.getIdEmpleado()) {
            System.err.println("Error BL: El nombre de usuario ya pertenece a otra cuenta.");
            return 0;
        }

        return empleadoDAO.update(empleado);
    }

    @Override
    public int eliminar(int idEmpleado) {
        if (idEmpleado <= 0) return 0;
        return empleadoDAO.delete(idEmpleado);
    }

    @Override
    public Empleado autenticar(String username, String passwordPlana) {
        Empleado empleado = empleadoDAO.obtenerPorUsername(username);

        if (empleado != null) {
            if (empleado.getPasswordHash().equals(passwordPlana)) {
                if (!empleado.isEstadoLaboral()) {
                    System.err.println("Error Login: El usuario ya no labora en la clínica.");
                    return null;
                }

                // Usamos el método optimizado en lugar del update completo
                LocalDateTime ahora = LocalDateTime.now();
                empleadoDAO.registrarUltimoAcceso(empleado.getIdEmpleado(), ahora);
                empleado.setUltimoAcceso(ahora);

                return empleado;
            }
        }
        System.err.println("Error Login: Credenciales incorrectas.");
        return null;
    }

    @Override
    public Empleado obtenerPorId(int idEmpleado) {
        Empleado empleado = empleadoDAO.load(idEmpleado);

        // ENSAMBLAJE: Le inyectamos su lista de acciones (Auditorías)
        if (empleado != null) {
            empleado.setAuditorias(auditoriaDAO.listarPorFidEmpleado(idEmpleado));
        }

        return empleado;
    }

    @Override
    public Empleado obtenerPorUsername(String username) {
        return empleadoDAO.obtenerPorUsername(username);
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoDAO.listALL();
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /*
     * Valida los datos heredados de la clase Persona (DRY Principle)
     */

    private boolean validarDatosPersonales(Empleado empleado) {
        if (empleado.getDni() == null || empleado.getDni().trim().length() != 8) {
            System.err.println("Error BL: DNI inválido.");
            return false;
        }
        if (empleado.getNombres() == null || empleado.getNombres().trim().isEmpty()) {
            System.err.println("Error BL: Nombres obligatorios.");
            return false;
        }
        if (empleado.getApellidoPaterno() == null || empleado.getApellidoPaterno().trim().isEmpty()) {
            System.err.println("Error BL: Apellido paterno obligatorio.");
            return false;
        }
        return true;
    }

    private boolean validarDatosLaborales(Empleado empleado) {
        if (empleado.getCodigoEmpleado() == null || empleado.getCodigoEmpleado().trim().isEmpty()) {
            System.err.println("Error BL: El código de empleado es obligatorio.");
            return false;
        }
        if (empleado.getUsername() == null || empleado.getUsername().trim().isEmpty()) {
            System.err.println("Error BL: El nombre de usuario es obligatorio.");
            return false;
        }
        if (empleado.getPasswordHash() == null || empleado.getPasswordHash().trim().isEmpty()) {
            System.err.println("Error BL: La contraseña es obligatoria.");
            return false;
        }
        if (empleado.getRol() == null) {
            System.err.println("Error BL: Debe asignar un Rol (ADMINISTRADOR, DOCTOR o SECRETARIA).");
            return false;
        }
        return true;
    }




}
