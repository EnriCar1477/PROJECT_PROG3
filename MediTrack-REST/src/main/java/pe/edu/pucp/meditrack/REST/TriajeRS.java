package pe.edu.pucp.meditrack.REST;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.TriajeBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.ITriajeBL;
import pe.edu.pucp.kirusmile.models.Triaje;

@Path("/triaje")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TriajeRS {

    private ITriajeBL triajeBL;

    public TriajeRS() {
        this.triajeBL = new TriajeBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Triaje triaje) {
        return triajeBL.registrar(triaje);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Triaje triaje) {
        return triajeBL.actualizar(triaje);
    }

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public Triaje obtenerPorId(@PathParam("id") int idTriaje) {
        return triajeBL.obtenerPorId(idTriaje);
    }

    /**
     * Este es el endpoint más importante para tu FrontEnd.
     * Permite recuperar el triaje de una consulta específica (DetalleHistorial)
     * para mostrarlo o editarlo.
     */
    @GET
    @Path("/obtenerPorDetalle/{fidDetalle}")
    public Triaje obtenerPorFidDetalle(@PathParam("fidDetalle") int fidDetalle) {
        return triajeBL.obtenerPorFidDetalle(fidDetalle);
    }

}
