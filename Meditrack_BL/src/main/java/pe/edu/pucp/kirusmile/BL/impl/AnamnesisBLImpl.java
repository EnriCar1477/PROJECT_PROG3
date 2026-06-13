package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IAnamnesisBL;
import pe.edu.pucp.kirusmile.dao.impl.AnamnesisDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.AnamnesisDAO;
import pe.edu.pucp.kirusmile.models.Anamnesis;

public class AnamnesisBLImpl implements IAnamnesisBL {

    private AnamnesisDAO anamnesisDAO;

    public AnamnesisBLImpl() {
        this.anamnesisDAO = new AnamnesisDAOImpl();
    }

    @Override
    public int registrar(Anamnesis anamnesis) {
        // 1. Validar la Integridad Estructural
        if (anamnesis.getDetalleHistorial() == null || anamnesis.getDetalleHistorial().getIdDetalle() <= 0) {
            System.err.println("Error BL: La anamnesis debe estar enlazada a una consulta médica válida.");
            return 0;
        }

        // --- CORRECCIÓN CRÍTICA: Evitar colapso por UNIQUE KEY ---
        Anamnesis existente = anamnesisDAO.obtenerPorFidDetalle(anamnesis.getDetalleHistorial().getIdDetalle());
        if (existente != null) {
            System.err.println("Error BL: Ya existe una anamnesis para esta consulta. Intente actualizar en lugar de registrar.");
            // Truco Pro: Podrías simplemente redirigir la acción a actualizar(anamnesis) aquí,
            // pero es mejor devolver 0 para que el Frontend sepa que intentó hacer la acción incorrecta.
            return 0;
        }

        // 2. Validaciones Médico-Legales
        if (!validarCamposObligatorios(anamnesis)) {
            return 0;
        }

        anamnesis.setActivo(true); // Aseguramos que nazca activo
        return anamnesisDAO.save(anamnesis);
    }

    @Override
    public int actualizar(Anamnesis anamnesis) {
        if (anamnesis.getIdAnamnesis() <= 0) {
            System.err.println("Error BL: No se puede actualizar una anamnesis sin ID.");
            return 0;
        }

        if (!validarCamposObligatorios(anamnesis)) {
            return 0;
        }

        return anamnesisDAO.update(anamnesis);
    }

    @Override
    public Anamnesis obtenerPorId(int idAnamnesis) {
        return anamnesisDAO.load(idAnamnesis);
    }

    @Override
    public Anamnesis obtenerPorFidDetalle(int fidDetalle) {
        if (fidDetalle <= 0) return null;
        return anamnesisDAO.obtenerPorFidDetalle(fidDetalle);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---

    /**
     * Valida que el médico haya llenado los campos.
     * Sincronizado con el comportamiento estricto del Frontend en Blazor.
     */
    private boolean validarCamposObligatorios(Anamnesis anamnesis) {
        if (anamnesis.getMotivoPrincipal() == null || anamnesis.getMotivoPrincipal().trim().isEmpty()) {
            System.err.println("Error BL: El Motivo Principal es obligatorio.");
            return false;
        }
        if (anamnesis.getTiempoEnfermedad() == null || anamnesis.getTiempoEnfermedad().trim().isEmpty()) {
            System.err.println("Error BL: El Tiempo de Enfermedad es obligatorio.");
            return false;
        }
        if (anamnesis.getFormaInicio() == null || anamnesis.getFormaInicio().trim().isEmpty()) {
            System.err.println("Error BL: La Forma de Inicio es obligatoria.");
            return false;
        }
        if (anamnesis.getAntecedentesImportantes() == null || anamnesis.getAntecedentesImportantes().trim().isEmpty()) {
            System.err.println("Error BL: Los Antecedentes son obligatorios.");
            return false;
        }
        if (anamnesis.getRelatoClinico() == null || anamnesis.getRelatoClinico().trim().isEmpty()) {
            System.err.println("Error BL: El Relato Clínico es obligatorio.");
            return false;
        }

        // Limpieza de espacios en blanco al inicio y final para no guardar basura en la BD
        anamnesis.setMotivoPrincipal(anamnesis.getMotivoPrincipal().trim());
        anamnesis.setTiempoEnfermedad(anamnesis.getTiempoEnfermedad().trim());
        anamnesis.setFormaInicio(anamnesis.getFormaInicio().trim());
        anamnesis.setAntecedentesImportantes(anamnesis.getAntecedentesImportantes().trim());
        anamnesis.setRelatoClinico(anamnesis.getRelatoClinico().trim());

        return true;
    }
}