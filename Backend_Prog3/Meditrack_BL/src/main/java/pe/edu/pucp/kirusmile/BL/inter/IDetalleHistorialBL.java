package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.DetalleHistorial;

import java.util.List;

public interface IDetalleHistorialBL {
    // Registra la apertura de una nueva consulta médica
    int registrar(DetalleHistorial detalle);

    // Actualiza los datos de la consulta (validando si está cerrada)
    int actualizar(DetalleHistorial detalle);

    // Cierra la consulta médica oficialmente (firma del acto médico)
    int cerrarConsulta(int idDetalle, String notaAclaratoria);

    // Ensamblaje maestro: Trae la consulta con TODOS sus datos anidados
    DetalleHistorial obtenerPorId(int idDetalle);

    // Lista el resumen de las consultas de un historial
    List<DetalleHistorial> listarPorFidHistorial(int fidHistorial);
}
