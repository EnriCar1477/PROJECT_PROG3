package pe.edu.pucp.meditrack.REST;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.AnamnesisBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IAnamnesisBL;
import pe.edu.pucp.kirusmile.models.Anamnesis;

@Path("/anamnesis")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnamnesisRS {

    // Instanciamos la capa de lógica de negocio (BL)
    private IAnamnesisBL anamnesisBL;

    public AnamnesisRS() {
        this.anamnesisBL = new AnamnesisBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (POST / PUT)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Anamnesis anamnesis) {
        return anamnesisBL.registrar(anamnesis);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Anamnesis anamnesis) {
        return anamnesisBL.actualizar(anamnesis);
    }

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    /**
     * Busca la anamnesis directamente por su ID principal.
     */
    @GET
    @Path("/obtenerPorId/{id}")
    public Anamnesis obtenerPorId(@PathParam("id") int idAnamnesis) {
        return anamnesisBL.obtenerPorId(idAnamnesis);
    }

    /**
     * Este es el endpoint clave para tu FrontEnd.
     * Permite saber si una consulta (DetalleHistorial) ya tiene una Anamnesis llenada.
     */
    @GET
    @Path("/obtenerPorDetalle/{fidDetalle}")
    public Anamnesis obtenerPorFidDetalle(@PathParam("fidDetalle") int fidDetalle) {
        return anamnesisBL.obtenerPorFidDetalle(fidDetalle);
    }

}
