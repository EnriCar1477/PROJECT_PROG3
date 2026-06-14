package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IPacienteBL;
import pe.edu.pucp.kirusmile.dao.impl.PacienteDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.PacienteDAO;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.util.List;

public class PacienteBLImpl implements IPacienteBL {

    private PacienteDAO pacienteDAO;

    public PacienteBLImpl() {
        this.pacienteDAO = new PacienteDAOImpl();
    }

    @Override
    public int registrar(Paciente paciente) {
        if (!validarDatosPersonales(paciente) || !validarDatosMedicos(paciente)) {
            return 0; // Si alguna validación falla, bloqueamos el registro
        }

        // Regla de Negocio: Evitar duplicidad de pacientes
        Paciente existente = pacienteDAO.obtenerPorDni(paciente.getDni());
        if (existente != null) {
            System.err.println("Error BL: Ya existe un paciente registrado con el DNI " + paciente.getDni() + ".");
            return 0;
        }

        return pacienteDAO.save(paciente);
    }

    @Override
    public int actualizar(Paciente paciente) {
        // Para actualizar, necesitamos saber a quién estamos actualizando
        if (paciente.getIdPaciente() == 0 && paciente.getIdPersona() == 0) {
            System.err.println("Error BL: No se puede actualizar un paciente sin su ID.");
            return 0;
        }

        if (!validarDatosPersonales(paciente) || !validarDatosMedicos(paciente)) {
            return 0;
        }

        // Regla: Si intenta cambiar su DNI, debemos verificar que el nuevo DNI no choque con otro paciente
        Paciente existente = pacienteDAO.obtenerPorDni(paciente.getDni());
        if (existente != null && existente.getIdPaciente() != paciente.getIdPaciente()) {
            System.err.println("Error BL: El nuevo DNI ingresado ya pertenece a otro paciente en el sistema.");
            return 0;
        }

        return pacienteDAO.update(paciente);
    }

    @Override
    public int eliminar(int idPaciente) {
        if (idPaciente <= 0) {
            System.err.println("Error BL: ID de paciente inválido.");
            return 0;
        }
        return pacienteDAO.delete(idPaciente);
    }

    @Override
    public Paciente obtenerPorId(int idPaciente) {
        return pacienteDAO.load(idPaciente);
    }

    @Override
    public Paciente obtenerPorDni(String dni) {
        if (dni == null || dni.trim().length() < 8 || dni.trim().length() > 12) {
            System.err.println("Error BL: El DNI debe tener entre 8 y 12 caracteres para la búsqueda.");
            return null;
        }
        return pacienteDAO.obtenerPorDni(dni.trim());
    }

    @Override
    public List<Paciente> listarTodos() {
        return pacienteDAO.listALL();
    }

    @Override
    public List<Paciente> listarPorFidMedico(int fidMedico) {
        if (fidMedico <= 0) {
            System.err.println("Error BL: El ID del médico no es válido para realizar la búsqueda.");
            // Retornamos una lista vacía en lugar de null para evitar que Blazor falle iterando
            return new java.util.ArrayList<>();
        }
        return pacienteDAO.listarPorFidMedico(fidMedico);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida los datos heredados de la clase padre (Persona).
     */

    private boolean validarDatosPersonales(Paciente paciente) {
        if (paciente.getDni() == null || paciente.getDni().trim().length() < 8 || paciente.getDni().trim().length() > 12) {
            System.err.println("Error BL: El paciente debe tener un DNI o Carnet de Extranjería válido (entre 8 y 12 caracteres).");
            return false;
        }
        if (paciente.getNombres() == null || paciente.getNombres().trim().isEmpty()) {
            System.err.println("Error BL: Los nombres son obligatorios.");
            return false;
        }
        if (paciente.getApellidoPaterno() == null || paciente.getApellidoPaterno().trim().isEmpty()) {
            System.err.println("Error BL: El apellido paterno es obligatorio.");
            return false;
        }
        if (paciente.getFechaNacimiento() == null) {
            System.err.println("Error BL: La fecha de nacimiento es obligatoria para armar el historial médico.");
            return false;
        }
        if (paciente.getCorreo() != null && !paciente.getCorreo().trim().isEmpty()) {
            String correo = paciente.getCorreo().trim();
            if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.err.println("Error BL: El formato del correo electrónico no es válido.");
                return false;
            }
            paciente.setCorreo(correo);
        }

        // Limpiamos espacios
        paciente.setDni(paciente.getDni().trim());
        paciente.setNombres(paciente.getNombres().trim());
        paciente.setApellidoPaterno(paciente.getApellidoPaterno().trim());
        if (paciente.getApellidoMaterno() != null) {
            paciente.setApellidoMaterno(paciente.getApellidoMaterno().trim());
        }
        if (paciente.getTelefono() != null) {
            paciente.setTelefono(paciente.getTelefono().trim());
        }

        return true;
    }

    /**
     * Valida los datos propios de la especialización médica (Paciente).
     */
    private boolean validarDatosMedicos(Paciente paciente) {
        // En odontología o medicina, a veces no saben su tipo de sangre en la primera cita.
        // Pero si lo ingresan, debemos validar que sea lógico.
        if (paciente.getGrupoSanguineo() != null && !paciente.getGrupoSanguineo().trim().isEmpty()) {
            String grupo = paciente.getGrupoSanguineo().trim().toUpperCase();
            if (!grupo.equals("A") && !grupo.equals("B") && !grupo.equals("AB") && !grupo.equals("O")) {
                System.err.println("Error BL: El grupo sanguíneo debe ser A, B, AB u O.");
                return false;
            }
            paciente.setGrupoSanguineo(grupo);
        }

        if (paciente.getFactorRh() != null && !paciente.getFactorRh().trim().isEmpty()) {
            String rh = paciente.getFactorRh().trim();
            if (!rh.equals("+") && !rh.equals("-")) {
                System.err.println("Error BL: El factor Rh debe ser '+' o '-'.");
                return false;
            }
            paciente.setFactorRh(rh);
        }

        // Grado de instrucción de acuerdo a restricciones del frontend
        if (paciente.getGradoInstruccion() != null && !paciente.getGradoInstruccion().trim().isEmpty()) {
            String grado = paciente.getGradoInstruccion().trim();
            if (!grado.equals("Sin instrucción") && !grado.equals("Inicial") && !grado.equals("Primaria") &&
                    !grado.equals("Secundaria") && !grado.equals("Superior técnico") && !grado.equals("Universitario")) {
                System.err.println("Error BL: El grado de instrucción no es válido.");
                return false;
            }
            paciente.setGradoInstruccion(grado);
        }

        // Limpiamos espacios para campos opcionales
        if (paciente.getOcupacion() != null) {
            paciente.setOcupacion(paciente.getOcupacion().trim());
        }
        if (paciente.getEtnia() != null) {
            paciente.setEtnia(paciente.getEtnia().trim());
        }

        return true;
    }
}
