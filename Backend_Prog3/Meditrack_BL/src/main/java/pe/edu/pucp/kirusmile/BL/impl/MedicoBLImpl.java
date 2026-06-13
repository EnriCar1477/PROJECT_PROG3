package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IMedicoBL;
import pe.edu.pucp.kirusmile.dao.impl.HorarioDisponibilidadDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.MedicoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.HorarioDisponibilidadDAO;
import pe.edu.pucp.kirusmile.dao.inter.MedicoDAO;
import pe.edu.pucp.kirusmile.models.Medico;

import java.time.LocalDate;
import java.util.List;

public class MedicoBLImpl implements IMedicoBL {

    private MedicoDAO medicoDAO;
    private HorarioDisponibilidadDAO horarioDAO;

    public MedicoBLImpl() {
        this.medicoDAO = new MedicoDAOImpl();
        this.horarioDAO = new HorarioDisponibilidadDAOImpl();
    }

    @Override
    public int registrar(Medico medico) {
        // 1. Validar integridad referencial (debe ser un empleado primero)
        if (medico.getIdEmpleado() == 0) {
            System.err.println("Error BL: El médico debe estar vinculado a un registro de Empleado válido.");
            return 0;
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
