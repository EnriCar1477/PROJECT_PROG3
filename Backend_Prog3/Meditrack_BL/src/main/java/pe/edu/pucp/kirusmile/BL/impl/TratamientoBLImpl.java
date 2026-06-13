package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.ITratamientoBL;
import pe.edu.pucp.kirusmile.dao.impl.TratamientoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.TratamientoDAO;
import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.time.LocalDate;
import java.util.List;

public class TratamientoBLImpl implements ITratamientoBL {

    private TratamientoDAO tratamientoDAO;

    public TratamientoBLImpl() {
        this.tratamientoDAO = new TratamientoDAOImpl();
    }


    @Override
    public int registrar(Tratamiento tratamiento) {
        // 1. Validaciones estructurales
        if (tratamiento.getDetalleHistorial() == null || tratamiento.getDetalleHistorial().getIdDetalle() == 0) {
            System.err.println("Error BL: El tratamiento debe estar asociado a una consulta (DetalleHistorial).");
            return 0;
        }

        // 2. Autocompletado inteligente: Si no mandan fecha de inicio, asumimos que es HOY.
        if (tratamiento.getFechaInicio() == null) {
            tratamiento.setFechaInicio(LocalDate.now());
        }

        // 3. Validaciones clínicas y cronológicas
        if (!validarReglasTratamiento(tratamiento)) {
            return 0;
        }

        return tratamientoDAO.save(tratamiento);
    }

    @Override
    public int actualizar(Tratamiento tratamiento) {
        if (tratamiento.getIdTratamiento() == 0) {
            System.err.println("Error BL: No se puede actualizar un tratamiento sin su ID.");
            return 0;
        }

        if (!validarReglasTratamiento(tratamiento)) {
            return 0;
        }

        return tratamientoDAO.update(tratamiento);
    }

    @Override
    public Tratamiento obtenerPorId(int idTratamiento) {
        return tratamientoDAO.load(idTratamiento);
    }

    @Override
    public List<Tratamiento> listarPorFidDetalle(int fidDetalle) {
        // Método invocado por el DetalleHistorialBL para armar el objeto completo de la cita
        return tratamientoDAO.listarPorFidDetalle(fidDetalle);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida la obligatoriedad de campos y la coherencia en la línea de tiempo.
     */
    private boolean validarReglasTratamiento(Tratamiento tratamiento) {
        if (tratamiento.getTipo() == null) {
            System.err.println("Error BL: Debe especificar el tipo de tratamiento (Enum).");
            return false;
        }

        if (tratamiento.getIndicaciones() == null || tratamiento.getIndicaciones().trim().isEmpty()) {
            System.err.println("Error BL: Las indicaciones del tratamiento no pueden estar vacías.");
            return false;
        }

        // REGLA CRONOLÓGICA: La fecha de fin (si existe) no puede ser anterior a la fecha de inicio
        if (tratamiento.getFechaFin() != null) {
            if (tratamiento.getFechaFin().isBefore(tratamiento.getFechaInicio())) {
                System.err.println("Error BL: Incoherencia temporal. La fecha de fin (" +
                        tratamiento.getFechaFin() + ") no puede ser anterior a la fecha de inicio (" +
                        tratamiento.getFechaInicio() + ").");
                return false;
            }
        }

        // Limpiamos los espacios en blanco adicionales
        tratamiento.setIndicaciones(tratamiento.getIndicaciones().trim());

        return true;
    }



}
