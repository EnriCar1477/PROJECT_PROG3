package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IDetalleHistorialBL;
import pe.edu.pucp.kirusmile.dao.impl.*;
import pe.edu.pucp.kirusmile.dao.inter.*;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;

import java.time.LocalDateTime;
import java.util.List;

public class DetalleHistorialBLImpl implements IDetalleHistorialBL {

    private DetalleHistorialDAO detalleDAO;
    private TriajeDAO triajeDAO;
    private AnamnesisDAO anamnesisDAO;
    private DiagnosticoDAO diagnosticoDAO;
    private TratamientoDAO tratamientoDAO;

    public DetalleHistorialBLImpl() {
        this.detalleDAO = new DetalleHistorialDAOImpl();
        this.triajeDAO = new TriajeDAOImpl();
        this.anamnesisDAO = new AnamnesisDAOImpl();
        this.diagnosticoDAO = new DiagnosticoDAOImpl();
        this.tratamientoDAO = new TratamientoDAOImpl();
    }

    @Override
    public int registrar(DetalleHistorial detalle) {
        // Regla de Negocio: La consulta debe originarse de una cita y pertenecer a un historial
        if (detalle.getHistorialMedico() == null || detalle.getHistorialMedico().getIdHistorial() <= 0 ||
                detalle.getCitaOrigen() == null || detalle.getCitaOrigen().getIdCitaMedica() <= 0) {
            System.err.println("Error BL: El detalle debe estar vinculado a un Historial y a una Cita válida.");
            return 0;
        }

        // Por defecto, al crearse, la consulta está abierta
        detalle.setEstaCerrada(false);
        detalle.setFechaCierre(null);

        return detalleDAO.save(detalle);
    }

    @Override
    public int actualizar(DetalleHistorial detalle) {
        // Validamos el estado actual en la base de datos antes de permitir la actualización
        DetalleHistorial detalleActual = detalleDAO.load(detalle.getIdDetalle());

        if (detalleActual != null && detalleActual.isEstaCerrada()) {
            System.err.println("Error BL: La consulta está CERRADA. Solo se puede usar cerrarConsulta() para agregar notas aclaratorias.");
            return 0; // Bloqueamos la actualización si ya se cerró
        }

        return detalleDAO.update(detalle);
    }

    @Override
    public int cerrarConsulta(int idDetalle, String notaAclaratoria) {
        DetalleHistorial detalle = detalleDAO.load(idDetalle);
        if (detalle == null) return 0;

        // Regla de Negocio: Marcar como cerrada y estampar la fecha/hora exacta
        detalle.setEstaCerrada(true);
        detalle.setFechaCierre(LocalDateTime.now());

        if (notaAclaratoria != null && !notaAclaratoria.trim().isEmpty()) {
            detalle.setNotaAclaratoria(notaAclaratoria);
        }

        return detalleDAO.update(detalle);
    }

    @Override
    public DetalleHistorial obtenerPorId(int idDetalle) {
        // 1. Obtenemos el cascarón (datos administrativos de la consulta)
        DetalleHistorial detalle = detalleDAO.load(idDetalle);

        if (detalle != null) {
            // 2. ENSAMBLAJE MAESTRO
            // Inyectamos el Triaje 1:1
            detalle.setTriaje(triajeDAO.obtenerPorIdDetalle(idDetalle));

            // Inyectamos la Anamnesis 1:1
            detalle.setAnamnesis(anamnesisDAO.obtenerPorFidDetalle(idDetalle));

            // Inyectamos la lista de Diagnósticos 1:N
            detalle.setListaDiagnosticos(diagnosticoDAO.listarPorFidDetalle(idDetalle));

            // Inyectamos la lista de Tratamientos 1:N
            detalle.setListaTratamientos(tratamientoDAO.listarPorFidDetalle(idDetalle));
        }

        return detalle;
    }

    @Override
    public List<DetalleHistorial> listarPorFidHistorial(int fidHistorial) {
        // Para listar en la tabla de historial, normalmente no ensamblamos todo el peso de los
        // tratamientos y diagnósticos para no saturar la memoria. Solo traemos los datos básicos.
        return detalleDAO.listarPorHistorial(fidHistorial);
    }
}
