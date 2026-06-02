package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IEnfermedadCIE10BL;
import pe.edu.pucp.kirusmile.dao.impl.EnfermedadCIE10DAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.EnfermedadCIE10DAO;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.util.List;

public class EnfermedadCIE10BLImpl implements IEnfermedadCIE10BL {

    private EnfermedadCIE10DAO enfermedadDAO;

    public EnfermedadCIE10BLImpl() {
        this.enfermedadDAO = new EnfermedadCIE10DAOImpl();
    }

    @Override
    public int registrar(EnfermedadCIE10 enfermedad) {
        if (!validarCampos(enfermedad)) {
            return 0;
        }

        // Regla de Negocio: Evitar códigos duplicados
        EnfermedadCIE10 existente = enfermedadDAO.obtenerPorCodigoCIE(enfermedad.getCodigoCIE());
        if (existente != null) {
            System.err.println("Error BL: El código CIE-10 '" + enfermedad.getCodigoCIE() + "' ya se encuentra registrado.");
            return 0; // Bloqueamos la inserción
        }

        return enfermedadDAO.save(enfermedad);
    }

    @Override
    public int actualizar(EnfermedadCIE10 enfermedad) {
        if (enfermedad.getIdEnfermedadCIE10() == 0) {
            System.err.println("Error BL: No se puede actualizar una enfermedad sin su ID.");
            return 0;
        }

        if (!validarCampos(enfermedad)) {
            return 0;
        }

        // Regla de Negocio: Verificar que si se cambia el código, no choque con otro existente
        EnfermedadCIE10 existente = enfermedadDAO.obtenerPorCodigoCIE(enfermedad.getCodigoCIE());
        if (existente != null && existente.getIdEnfermedadCIE10() != enfermedad.getIdEnfermedadCIE10()) {
            System.err.println("Error BL: El código CIE-10 '" + enfermedad.getCodigoCIE() + "' ya pertenece a otra enfermedad.");
            return 0;
        }

        return enfermedadDAO.update(enfermedad);
    }

    @Override
    public EnfermedadCIE10 obtenerPorId(int idEnfermedad) {
        return enfermedadDAO.load(idEnfermedad);
    }

    @Override
    public EnfermedadCIE10 obtenerPorCodigoCIE(String codigoCIE) {
        if (codigoCIE == null || codigoCIE.trim().isEmpty()) {
            return null;
        }
        return enfermedadDAO.obtenerPorCodigoCIE(codigoCIE.trim().toUpperCase());
    }

    @Override
    public List<EnfermedadCIE10> listarTodos() {
        return enfermedadDAO.listALL();
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida que los datos obligatorios del catálogo estén presentes y con el formato adecuado.
     */
    private boolean validarCampos(EnfermedadCIE10 enfermedad) {
        if (enfermedad.getCodigoCIE() == null || enfermedad.getCodigoCIE().trim().isEmpty()) {
            System.err.println("Error BL: El código CIE-10 no puede estar vacío.");
            return false;
        }

        if (enfermedad.getDescripcionOficial() == null || enfermedad.getDescripcionOficial().trim().isEmpty()) {
            System.err.println("Error BL: La descripción oficial de la enfermedad es obligatoria.");
            return false;
        }

        // Estandarizamos los textos antes de enviarlos a la base de datos
        enfermedad.setCodigoCIE(enfermedad.getCodigoCIE().trim().toUpperCase());
        enfermedad.setDescripcionOficial(enfermedad.getDescripcionOficial().trim());

        return true;
    }

}
