package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.DiagnosticoBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IDiagnosticoBL;
import pe.edu.pucp.kirusmile.models.Diagnostico;

import java.util.List;

@Path("/diagnostico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagnosticoRS {

    // Instanciamos la capa de lógica de negocio (BL)
    private IDiagnosticoBL diagnosticoBL;

    public DiagnosticoRS() {
        this.diagnosticoBL = new DiagnosticoBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (POST / PUT / DELETE)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Diagnostico diagnostico) {
        return diagnosticoBL.registrar(diagnostico);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Diagnostico diagnostico) {
        return diagnosticoBL.actualizar(diagnostico);
    }

    /**
     * Endpoint para el botón de "Basurero" en la tabla de tu FrontEnd.
     * Ejecuta el borrado lógico (activo = 0).
     */
    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idDiagnostico) {
        return diagnosticoBL.eliminar(idDiagnostico);
    }

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public Diagnostico obtenerPorId(@PathParam("id") int idDiagnostico) {
        return diagnosticoBL.obtenerPorId(idDiagnostico);
    }

    /**
     * Endpoint crucial: Trae todos los diagnósticos de una consulta específica.
     * Incluye el INNER JOIN de la enfermedad CIE-10 para llenar tu tabla en Blazor.
     */
    @GET
    @Path("/listarPorDetalle/{fidDetalle}")
    public List<Diagnostico> listarPorFidDetalle(@PathParam("fidDetalle") int fidDetalle) {
        return diagnosticoBL.listarPorFidDetalle(fidDetalle);
    }


}
