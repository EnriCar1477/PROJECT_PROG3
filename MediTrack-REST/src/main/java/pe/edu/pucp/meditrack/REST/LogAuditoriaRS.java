package pe.edu.pucp.meditrack.REST;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.LogAuditoriaBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.ILogAuditoriaBL;
import pe.edu.pucp.kirusmile.models.LogAuditoria;

import java.util.List;

@Path("/log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAuditoriaRS {
    // Instanciamos la capa de lógica de negocio (BL)
    private ILogAuditoriaBL logAuditoriaBL;

    public LogAuditoriaRS() {
        this.logAuditoriaBL = new LogAuditoriaBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (Solo POST)
    // ==========================================

    /**
     * Endpoint para registrar un nuevo evento en el sistema.
     * Único método de escritura permitido para esta clase.
     */
    @POST
    @Path("/registrar")
    public int registrar(LogAuditoria log) {
        return logAuditoriaBL.registrar(log);
    }

    // ⚠️ REGLA DE SEGURIDAD ESTRICTA:
    // No se exponen endpoints @PUT ni @DELETE. El log de auditoría es inmutable.

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public LogAuditoria obtenerPorId(@PathParam("id") int idLogAuditoria) {
        return logAuditoriaBL.obtenerPorId(idLogAuditoria);
    }

    /**
     * Endpoint para el módulo de Administración/Seguridad.
     * Trae todos los logs registrados ordenados desde el más reciente al más antiguo.
     */
    @GET
    @Path("/listarTodos")
    public List<LogAuditoria> listarTodos() {
        return logAuditoriaBL.listarTodos();
    }

    /**
     * Endpoint para ver las acciones realizadas por un empleado en específico.
     */
    @GET
    @Path("/listarPorEmpleado/{fidEmpleado}")
    public List<LogAuditoria> listarPorFidEmpleado(@PathParam("fidEmpleado") int fidEmpleado) {
        return logAuditoriaBL.listarPorFidEmpleado(fidEmpleado);
    }
}
