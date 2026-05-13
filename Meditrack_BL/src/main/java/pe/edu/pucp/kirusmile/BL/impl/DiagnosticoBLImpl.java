package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IDiagnosticoBL;
import pe.edu.pucp.kirusmile.dao.impl.DiagnosticoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.DiagnosticoDAO;
import pe.edu.pucp.kirusmile.models.Diagnostico;

import java.time.LocalDateTime;
import java.util.List;

public class DiagnosticoBLImpl implements IDiagnosticoBL {

    private DiagnosticoDAO diagnosticoDAO;

    public DiagnosticoBLImpl() {
        this.diagnosticoDAO = new DiagnosticoDAOImpl();
    }


    @Override
    public int registrar(Diagnostico diagnostico) {
        // 1. Validaciones estructurales (Llaves foráneas en el objeto)
        if (diagnostico.getDetalleHistorial() == null || diagnostico.getDetalleHistorial().getIdDetalle() == 0) {
            System.err.println("Error BL: El diagnóstico debe pertenecer a un DetalleHistorial.");
            return 0;
        }
        if (diagnostico.getEnfermedadBase() == null || diagnostico.getEnfermedadBase().getIdEnfermedadCIE10() == 0) {
            System.err.println("Error BL: El diagnóstico debe tener asignada una Enfermedad CIE-10 válida.");
            return 0;
        }

        // 2. Validaciones clínicas
        if (!validarTipoDiagnostico(diagnostico)) {
            return 0;
        }

        // 3. Estampado de tiempo: Si el front-end no envió la hora, el servidor la pone automáticamente
        if (diagnostico.getFechaHoraRegistro() == null) {
            diagnostico.setFechaHoraRegistro(LocalDateTime.now());
        }

        return diagnosticoDAO.save(diagnostico);
    }

    @Override
    public int actualizar(Diagnostico diagnostico) {
        if (diagnostico.getIdDiagnostico() == 0) {
            System.err.println("Error BL: No se puede actualizar un diagnóstico sin ID.");
            return 0;
        }

        if (!validarTipoDiagnostico(diagnostico)) {
            return 0;
        }

        return diagnosticoDAO.update(diagnostico);
    }

    @Override
    public Diagnostico obtenerPorId(int idDiagnostico) {
        return diagnosticoDAO.load(idDiagnostico);
    }

    @Override
    public List<Diagnostico> listarPorFidDetalle(int fidDetalle) {
        // Este método es llamado por DetalleHistorialBL para ensamblar la lista completa de la consulta
        return diagnosticoDAO.listarPorFidDetalle(fidDetalle);
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---

    /**
     * Valida que el tipo de diagnóstico ingresado sea uno aceptado médicamente.
     */
    private boolean validarTipoDiagnostico(Diagnostico diagnostico) {
        if (diagnostico.getTipo() == null || diagnostico.getTipo().trim().isEmpty()) {
            System.err.println("Error BL: El tipo de diagnóstico es obligatorio.");
            return false;
        }

        // Normalizamos a mayúsculas para evitar problemas de tipeo
        String tipo = diagnostico.getTipo().trim().toUpperCase();

        // Solo aceptamos PRESUNTIVO o DEFINITIVO (puedes adaptar esto a tu catálogo si tienes más)
        if (!tipo.equals("PRESUNTIVO") && !tipo.equals("DEFINITIVO")) {
            System.err.println("Error BL: El tipo de diagnóstico debe ser 'PRESUNTIVO' o 'DEFINITIVO'.");
            return false;
        }

        // Reasignamos el valor normalizado al objeto
        diagnostico.setTipo(tipo);
        return true;
    }

}
