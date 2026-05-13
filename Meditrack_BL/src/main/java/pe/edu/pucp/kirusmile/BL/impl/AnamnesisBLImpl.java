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
        if (anamnesis.getDetalleHistorial() == null || anamnesis.getDetalleHistorial().getIdDetalle() == 0) {
            System.err.println("Error BL: La anamnesis debe estar enlazada a una consulta médica (DetalleHistorial).");
            return 0;
        }

        // 2. Validaciones Médico-Legales (Reglas de Negocio)
        if (!validarCamposObligatorios(anamnesis)) {
            return 0; // Se bloquea la inserción si faltan datos críticos
        }

        return anamnesisDAO.save(anamnesis);
    }

    @Override
    public int actualizar(Anamnesis anamnesis) {
        if (anamnesis.getIdAnamnesis() == 0) {
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
        // Este método es crucial para que el DetalleHistorialBL pueda armar la vista completa
        return anamnesisDAO.obtenerPorFidDetalle(fidDetalle);
    }


    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida que el médico haya llenado los campos que justifican la atención.
     */
    private boolean validarCamposObligatorios(Anamnesis anamnesis) {
        if (anamnesis.getMotivoPrincipal() == null || anamnesis.getMotivoPrincipal().trim().isEmpty()) {
            System.err.println("Error BL: El Motivo Principal de la consulta no puede estar vacío.");
            return false;
        }

        if (anamnesis.getRelatoClinico() == null || anamnesis.getRelatoClinico().trim().isEmpty()) {
            System.err.println("Error BL: El Relato Clínico es obligatorio para el historial.");
            return false;
        }

        // El tiempo de enfermedad, forma de inicio y antecedentes pueden ser "No refiere" o dejarse en blanco
        // dependiendo del nivel de urgencia, pero el Motivo y el Relato son indispensables.

        return true;
    }


}
