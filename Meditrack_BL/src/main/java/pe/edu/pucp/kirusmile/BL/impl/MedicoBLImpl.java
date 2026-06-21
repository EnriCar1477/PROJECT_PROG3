package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IMedicoBL;
import pe.edu.pucp.kirusmile.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.HorarioDisponibilidadDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.MedicoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.EmpleadoDAO;
import pe.edu.pucp.kirusmile.dao.inter.HorarioDisponibilidadDAO;
import pe.edu.pucp.kirusmile.dao.inter.MedicoDAO;
import pe.edu.pucp.kirusmile.models.Medico;

import java.time.LocalDate;
import java.util.List;

public class MedicoBLImpl implements IMedicoBL {

    private MedicoDAO medicoDAO;
    private HorarioDisponibilidadDAO horarioDAO;
    private EmpleadoDAO empleadoDAO;

    public MedicoBLImpl() {
        this.medicoDAO = new MedicoDAOImpl();
        this.horarioDAO = new HorarioDisponibilidadDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    @Override
    public int registrar(Medico medico) {
        // 1. Si es un médico nuevo sin registro base, registramos primero la parte de Empleado y Persona
        if (medico.getIdEmpleado() == 0) {
            // Regla: El DNI debe ser único
            if (empleadoDAO.obtenerPorDni(medico.getDni()) != null) {
                System.err.println("Error BL: Ya existe un empleado/médico registrado con el DNI " + medico.getDni());
                return 0;
            }
            // Regla: El Username debe ser único
            if (empleadoDAO.obtenerPorUsername(medico.getUsername()) != null) {
                System.err.println("Error BL: El nombre de usuario '" + medico.getUsername() + "' ya está en uso.");
                return 0;
            }
            int idEmpleadoGenerado = empleadoDAO.save(medico);
            if (idEmpleadoGenerado == 0) {
                System.err.println("Error BL: No se pudo registrar el Empleado base para el médico.");
                return 0;
            }
            medico.setIdEmpleado(idEmpleadoGenerado);
        }

        // 2. Validaciones Médico-Legales
        if (!validarCredenciales(medico)) {
            return 0;
        }

        // 3. Autocompletado: Si no le ponen fecha de ingreso, asumimos que empieza hoy
        if (medico.getFechaIngreso() == null) {
            medico.setFechaIngreso(LocalDate.now());
        }

        return medicoDAO.save(medico);
    }

    @Override
    public int actualizar(Medico medico) {
        if (medico.getIdMedico() == 0) {
            System.err.println("Error BL: No se puede actualizar un perfil médico sin su ID.");
            return 0;
        }

        if (!validarCredenciales(medico)) {
            return 0;
        }

        // También actualizamos la parte de Persona y Empleado
        int resEmpleado = empleadoDAO.update(medico);
        if (resEmpleado == 0) {
            System.err.println("Error BL: No se pudo actualizar el Empleado base para el médico.");
            return 0;
        }

        return medicoDAO.update(medico);
    }

    @Override
    public Medico obtenerPorId(int idMedico) {
        // 1. Obtenemos los datos del perfil médico
        Medico medico = medicoDAO.load(idMedico);

        // 2. ENSAMBLAJE: Le inyectamos su agenda completa
        if (medico != null) {
            medico.setListaHorarios(horarioDAO.listarPorFidMedico(idMedico));
        }

        return medico;
    }

    @Override
    public Medico obtenerPorFidEmpleado(int fidEmpleado) {
        // Este método es fundamental cuando un usuario tipo "Médico" hace login y necesitas cargar su perfil
        Medico medico = medicoDAO.obtenerPorFidEmpleado(fidEmpleado);

        if (medico != null) {
            medico.setListaHorarios(horarioDAO.listarPorFidMedico(medico.getIdMedico()));
        }

        return medico;
    }

    @Override
    public List<Medico> listarTodos() {
        // Para listados generales no solemos cargar los horarios de todos para no saturar la memoria
        return medicoDAO.listALL();
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida que el médico tenga sus credenciales legales en regla antes de trabajar en la clínica.
     */
    private boolean validarCredenciales(Medico medico) {
        if (medico.getCmp() == null || medico.getCmp().trim().isEmpty()) {
            System.err.println("Error BL: El número del Colegio Médico del Perú (CMP) es obligatorio.");
            return false;
        }

        if (medico.getEspecialidad() == null || medico.getEspecialidad().getIdEspecialidad() == 0) {
            System.err.println("Error BL: El médico debe tener asignada una Especialidad válida.");
            return false;
        }

        // El RNE (Registro Nacional de Especialista) puede ser opcional si es médico general,
        // pero limpiamos los espacios en blanco por buenas prácticas.
        medico.setCmp(medico.getCmp().trim());
        if (medico.getRne() != null) {
            medico.setRne(medico.getRne().trim());
        }

        return true;
    }


}
