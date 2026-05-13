package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.ITriajeBL;
import pe.edu.pucp.kirusmile.dao.impl.TriajeDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.TriajeDAO;
import pe.edu.pucp.kirusmile.models.Triaje;

public class TriajeBLImpl implements ITriajeBL {

    private TriajeDAO triajeDAO;

    public TriajeBLImpl() {
        this.triajeDAO = new TriajeDAOImpl();
    }

    @Override
    public int registrar(Triaje triaje) {
        // 1. Validación de relación: El triaje no puede flotar en el aire
        if (triaje.getDetalleHistorial() == null || triaje.getDetalleHistorial().getIdDetalle() == 0) {
            System.err.println("Error BL: El triaje debe pertenecer a un detalle de historial válido.");
            return 0;
        }

        // 2. Validaciones clínicas (Reglas de Negocio)
        if (!validarSignosVitales(triaje)) {
            return 0; // Si los signos vitales son irreales, bloqueamos el registro
        }

        return triajeDAO.save(triaje);
    }

    @Override
    public int actualizar(Triaje triaje) {
        if (triaje.getIdTriaje() == 0) {
            System.err.println("Error BL: No se puede actualizar un triaje sin ID.");
            return 0;
        }

        // Volvemos a validar por si intentan actualizar con datos basura
        if (!validarSignosVitales(triaje)) {
            return 0;
        }

        return triajeDAO.update(triaje);
    }

    @Override
    public Triaje obtenerPorId(int idTriaje) {
        return triajeDAO.load(idTriaje);
    }

    @Override
    public Triaje obtenerPorFidDetalle(int fidDetalle) {
        // Este método es el que usa el DetalleHistorialBL para ensamblarse
        return triajeDAO.obtenerPorIdDetalle(fidDetalle);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---

    /**
     * Valida que los datos ingresados en el triaje sean humanamente posibles.
     * Esto evita que un error de tipeo (ej. peso: -80) corrompa la base de datos.
     */
    private boolean validarSignosVitales(Triaje triaje) {
        if (triaje.getPeso() <= 0 || triaje.getPeso() > 300) {
            System.err.println("Error BL: El peso ingresado (" + triaje.getPeso() + ") no es válido.");
            return false;
        }
        if (triaje.getTalla() <= 0 || triaje.getTalla() > 2.50) { // Asumiendo talla en metros
            System.err.println("Error BL: La talla ingresada (" + triaje.getTalla() + ") no es válida.");
            return false;
        }
        if (triaje.getTemperatura() < 30 || triaje.getTemperatura() > 45) {
            System.err.println("Error BL: La temperatura corporal es irreal.");
            return false;
        }
        if (triaje.getSaturacion() < 0 || triaje.getSaturacion() > 100) {
            System.err.println("Error BL: La saturación de oxígeno debe estar entre 0 y 100.");
            return false;
        }
        // La presión arterial es un String (ej. "120/80"), podrías aplicar una expresión regular aquí si lo deseas
        if (triaje.getPresionArterial() == null || triaje.getPresionArterial().trim().isEmpty()) {
            System.err.println("Error BL: La presión arterial es obligatoria.");
            return false;
        }
        return true;
    }



}
