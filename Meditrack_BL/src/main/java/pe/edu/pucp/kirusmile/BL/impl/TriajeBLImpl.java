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
        if (triaje.getDetalleHistorial() == null || triaje.getDetalleHistorial().getIdDetalle() <= 0) {
            System.err.println("Error BL: El triaje debe pertenecer a un detalle de historial válido.");
            return 0;
        }

        // --- CORRECCIÓN CRÍTICA: Prevenir inserción duplicada ---
        Triaje existente = triajeDAO.obtenerPorIdDetalle(triaje.getDetalleHistorial().getIdDetalle());
        if (existente != null) {
            System.err.println("Error BL: Ya existe un triaje registrado para esta consulta. Intente actualizar.");
            return 0;
        }

        // 2. Validaciones clínicas
        if (!validarSignosVitales(triaje)) {
            return 0;
        }

        // 3. Estado inicial
        triaje.setActivo(true);

        return triajeDAO.save(triaje);
    }

    @Override
    public int actualizar(Triaje triaje) {
        if (triaje.getIdTriaje() <= 0) {
            System.err.println("Error BL: No se puede actualizar un triaje sin ID.");
            return 0;
        }

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
        if (fidDetalle <= 0) return null;
        return triajeDAO.obtenerPorIdDetalle(fidDetalle);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---

    private boolean validarSignosVitales(Triaje triaje) {
        // Validación de Presión (se limpia el texto)
        if (triaje.getPresionArterial() == null || triaje.getPresionArterial().trim().isEmpty()) {
            System.err.println("Error BL: La presión arterial es obligatoria.");
            return false;
        }
        triaje.setPresionArterial(triaje.getPresionArterial().trim());

        // Rangos clínicos
        if (triaje.getPeso() <= 0 || triaje.getPeso() > 300) {
            System.err.println("Error BL: Peso fuera de rango.");
            return false;
        }
        if (triaje.getTalla() <= 0 || triaje.getTalla() > 2.50) {
            System.err.println("Error BL: Talla fuera de rango.");
            return false;
        }
        if (triaje.getTemperatura() < 30 || triaje.getTemperatura() > 45) {
            System.err.println("Error BL: Temperatura fuera de rango.");
            return false;
        }
        if (triaje.getSaturacion() < 0 || triaje.getSaturacion() > 100) {
            System.err.println("Error BL: Saturación fuera de rango.");
            return false;
        }
        return true;
    }
}
