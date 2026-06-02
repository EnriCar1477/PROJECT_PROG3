package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IHistorialMedicoBL;
import pe.edu.pucp.kirusmile.dao.impl.DetalleHistorialDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.HistorialMedicoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.DetalleHistorialDAO;
import pe.edu.pucp.kirusmile.dao.inter.HistorialMedicoDAO;
import pe.edu.pucp.kirusmile.models.HistorialMedico;

public class HistorialMedicoBLImpl implements IHistorialMedicoBL {

    // El BL se comunica con los DAOs, NUNCA con la base de datos directamente
    private HistorialMedicoDAO historialDAO;
    private DetalleHistorialDAO detalleDAO;

    public HistorialMedicoBLImpl() {
        this.historialDAO = new HistorialMedicoDAOImpl();
        this.detalleDAO = new DetalleHistorialDAOImpl();
    }


    @Override
    public int registrar(HistorialMedico historial) {
        // Regla de Negocio: Validar que el paciente no sea nulo antes de ir al DAO
        if (historial.getPaciente() == null || historial.getPaciente().getIdPaciente() == 0) {
            System.err.println("Error BL: El historial debe pertenecer a un paciente válido.");
            return 0;
        }

        // Opcional: Podrías verificar si el paciente ya tiene un historial activo
        // para no duplicarlo, llamando a obtenerPorIdPaciente(historial.getPaciente().getIdPaciente())

        return historialDAO.save(historial);
    }

    @Override
    public int actualizar(HistorialMedico historial) {
        if (historial.getIdHistorial() == 0) {
            System.err.println("Error BL: No se puede actualizar un historial sin ID.");
            return 0;
        }
        return historialDAO.update(historial);
    }

    @Override
    public HistorialMedico obtenerPorIdPaciente(int idPaciente) {
        // 1. Obtenemos el "cascarón" del historial desde la base de datos
        HistorialMedico historial = historialDAO.obtenerPorIdPaciente(idPaciente);

        // 2. ENSAMBLAJE: Si el historial existe, le cargamos todas sus citas/detalles
        if (historial != null) {
            // El BL usa el DetalleDAO para llenar la lista del Historial
            historial.setListaDetalles(detalleDAO.listarPorHistorial(historial.getIdHistorial()));
        }

        return historial;
    }

    @Override
    public HistorialMedico obtenerPorId(int idHistorial) {
        HistorialMedico historial = historialDAO.load(idHistorial);

        if (historial != null) {
            // Llenamos la lista de detalles históricos
            historial.setListaDetalles(detalleDAO.listarPorHistorial(historial.getIdHistorial()));
        }

        return historial;
    }
}
