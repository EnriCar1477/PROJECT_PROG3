package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IHorarioDisponibilidadBL;
import pe.edu.pucp.kirusmile.dao.impl.HorarioDisponibilidadDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.HorarioDisponibilidadDAO;
import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;

import java.time.LocalDate;
import java.util.List;

public class HorarioDisponibilidadBLImpl implements IHorarioDisponibilidadBL {

    private HorarioDisponibilidadDAO horarioDAO;

    public HorarioDisponibilidadBLImpl() {
        this.horarioDAO = new HorarioDisponibilidadDAOImpl();
    }

    @Override
    public int registrar(HorarioDisponibilidad horario) {
        if (!validarReglasHorario(horario)) {
            return 0;
        }

        // Regla de Negocio Adicional: No permitir crear horarios en el pasado
        if (horario.getFechaEspecifica().isBefore(LocalDate.now())) {
            System.err.println("Error BL: No se pueden registrar disponibilidades en fechas pasadas.");
            return 0;
        }

        // Por defecto, el horario nace activo
        horario.setActivo(true);

        return horarioDAO.save(horario);
    }

    @Override
    public int actualizar(HorarioDisponibilidad horario) {
        if (horario.getIdHorario() == 0) {
            System.err.println("Error BL: No se puede actualizar un horario sin ID.");
            return 0;
        }

        if (!validarReglasHorario(horario)) {
            return 0;
        }

        return horarioDAO.update(horario);
    }

    @Override
    public int eliminar(int idHorario) {
        if (idHorario == 0) {
            System.err.println("Error BL: ID de horario inválido para eliminar.");
            return 0;
        }
        return horarioDAO.delete(idHorario);
    }

    @Override
    public HorarioDisponibilidad obtenerPorId(int idHorario) {
        return horarioDAO.load(idHorario);
    }

    @Override
    public List<HorarioDisponibilidad> listarPorFidMedico(int fidMedico) {
        // Este método será muy usado cuando  la secretaria seleccione a un médico
        // y necesiten ver en qué días y horas está disponible para atender.
        return horarioDAO.listarPorFidMedico(fidMedico);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---

    /**
     * Valida que las horas tengan sentido cronológico y que el horario pertenezca a un doctor.
     */
    private boolean validarReglasHorario(HorarioDisponibilidad horario) {
        // 1. Validar Pertenencia
        if (horario.getMedico() == null || horario.getMedico().getIdMedico() == 0) {
            System.err.println("Error BL: El horario de disponibilidad debe estar asignado a un Médico válido.");
            return false;
        }

        // 2. Validar que no falten datos
        if (horario.getFechaEspecifica() == null) {
            System.err.println("Error BL: La fecha del horario es obligatoria.");
            return false;
        }
        if (horario.getHoraInicio() == null || horario.getHoraFin() == null) {
            System.err.println("Error BL: Las horas de inicio y fin son obligatorias.");
            return false;
        }

        // 3. Regla Cronológica Diaria: La hora de fin debe ser DESPUÉS de la hora de inicio
        // Con LocalTime, la función isBefore() hace esto súper sencillo
        if (!horario.getHoraInicio().isBefore(horario.getHoraFin())) {
            System.err.println("Error BL: Incoherencia de horas. La hora de inicio (" +
                    horario.getHoraInicio() + ") debe ser anterior a la hora de fin (" +
                    horario.getHoraFin() + ").");
            return false;
        }

        return true;
    }


}
