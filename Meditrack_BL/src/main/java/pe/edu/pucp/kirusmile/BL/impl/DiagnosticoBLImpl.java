package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IDiagnosticoBL;
import pe.edu.pucp.kirusmile.dao.impl.DiagnosticoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.DiagnosticoDAO;
import pe.edu.pucp.kirusmile.models.Diagnostico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoBLImpl implements IDiagnosticoBL {

    private DiagnosticoDAO diagnosticoDAO;

    public DiagnosticoBLImpl() {
        this.diagnosticoDAO = new DiagnosticoDAOImpl();
    }

    @Override
    public int registrar(Diagnostico diagnostico) {
        // 1. Validaciones estructurales (Llaves foráneas)
        if (diagnostico.getDetalleHistorial() == null || diagnostico.getDetalleHistorial().getIdDetalle() <= 0) {
            System.err.println("Error BL: El diagnóstico debe pertenecer a una consulta válida (DetalleHistorial).");
            return 0;
        }
        if (diagnostico.getEnfermedadBase() == null || diagnostico.getEnfermedadBase().getIdEnfermedadCIE10() <= 0) {
            System.err.println("Error BL: El diagnóstico debe tener asignada una Enfermedad CIE-10 válida.");
            return 0;
        }

        // 2. Validación Clínica del Enum
        if (diagnostico.getTipo() == null) {
            System.err.println("Error BL: El tipo de diagnóstico (PRESUNTIVO, DEFINITIVO o REPETITIVO) es obligatorio.");
            return 0;
        }

        // 3. Estampado de tiempo y estado
        if (diagnostico.getFechaHoraRegistro() == null) {
            diagnostico.setFechaHoraRegistro(LocalDateTime.now());
        }
        diagnostico.setActivo(true); // Aseguramos que nazca activo

        return diagnosticoDAO.save(diagnostico);
    }

    @Override
    public int actualizar(Diagnostico diagnostico) {
        if (diagnostico.getIdDiagnostico() <= 0) {
            System.err.println("Error BL: No se puede actualizar un diagnóstico sin ID.");
            return 0;
        }
        if (diagnostico.getTipo() == null) {
            System.err.println("Error BL: El tipo de diagnóstico es obligatorio.");
            return 0;
        }

        return diagnosticoDAO.update(diagnostico);
    }

    // --- ¡NUEVO! Método indispensable para el FrontEnd ---
    @Override
    public int eliminar(int idDiagnostico) {
        if (idDiagnostico <= 0) {
            System.err.println("Error BL: ID de diagnóstico inválido para eliminar.");
            return 0;
        }
        // Aplica el borrado lógico que programamos en el DAO
        return diagnosticoDAO.delete(idDiagnostico);
    }

    @Override
    public Diagnostico obtenerPorId(int idDiagnostico) {
        if (idDiagnostico <= 0) return null;
        return diagnosticoDAO.load(idDiagnostico);
    }

    @Override
    public List<Diagnostico> listarPorFidDetalle(int fidDetalle) {
        if (fidDetalle <= 0) return new ArrayList<>();
        // Este método es llamado por DetalleHistorialBL para ensamblar la lista completa de la consulta
        return diagnosticoDAO.listarPorFidDetalle(fidDetalle);
    }
}
