package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IHistorialMedicoBL;
import pe.edu.pucp.kirusmile.dao.impl.DetalleHistorialDAOImpl;
import pe.edu.pucp.kirusmile.dao.impl.HistorialMedicoDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.DetalleHistorialDAO;
import pe.edu.pucp.kirusmile.dao.inter.HistorialMedicoDAO;
import pe.edu.pucp.kirusmile.models.HistorialMedico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoBLImpl implements IHistorialMedicoBL {

    private HistorialMedicoDAO historialDAO;

    public HistorialMedicoBLImpl() {
        this.historialDAO = new HistorialMedicoDAOImpl();
        // CORRECCIÓN 1: Eliminamos el DetalleHistorialDAO.
        // Ya no lo necesitamos aquí porque el HistorialMedicoDAOImpl ahora
        // se encarga de llenar la lista de detalles automáticamente en su método load().
    }

    @Override
    public int registrar(HistorialMedico historial) {
        // Regla de Negocio 1: Validar paciente
        if (historial.getPaciente() == null || historial.getPaciente().getIdPaciente() <= 0) {
            System.err.println("Error BL: El historial debe pertenecer a un paciente válido.");
            return 0;
        }

        // CORRECCIÓN 2: Regla de Negocio CRÍTICA (Evitar duplicidad)
        // La BD tiene un UNIQUE KEY en fid_paciente. Debemos evitar que se caiga el sistema.
        HistorialMedico existente = historialDAO.obtenerPorIdPaciente(historial.getPaciente().getIdPaciente());
        if (existente != null) {
            System.err.println("Error BL: El paciente ya tiene un historial médico registrado (Historial #" + existente.getIdHistorial() + ").");
            return 0; // Bloqueamos la creación
        }

        // Regla de Negocio 3: Autocompletado seguro
        if (historial.getFechaCreacion() == null) {
            historial.setFechaCreacion(LocalDateTime.now()); // La fecha de hoy si viene vacía
        }
        historial.setActivo(true);

        return historialDAO.save(historial);
    }

    @Override
    public int actualizar(HistorialMedico historial) {
        if (historial.getIdHistorial() <= 0) {
            System.err.println("Error BL: No se puede actualizar un historial sin ID.");
            return 0;
        }
        return historialDAO.update(historial);
    }

    @Override
    public HistorialMedico obtenerPorIdPaciente(int idPaciente) {
        if (idPaciente <= 0) return null;

        // CORRECCIÓN 3: Delegamos el trabajo pesado al DAO.
        // El HistorialMedicoDAOImpl que creamos ya hace el INNER JOIN del Paciente
        // y ejecuta la subconsulta para llenar la ListaDetalles.
        return historialDAO.obtenerPorIdPaciente(idPaciente);
    }

    @Override
    public HistorialMedico obtenerPorIdHistorial(int idHistorial) {
        if (idHistorial <= 0) return null;

        // Exactamente lo mismo, el DAO devuelve el objeto listo para Blazor
        return historialDAO.load(idHistorial);
    }

    @Override
    public List<HistorialMedico> listarPorDniOApellido(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return new ArrayList<>(); // Si envían vacío, devolvemos lista vacía
        }
        return historialDAO.listarPorDniOApellido(filtro);
    }
}
