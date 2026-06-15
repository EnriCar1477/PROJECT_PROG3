package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.TratamientoBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.ITratamientoBL;
import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.util.List;

@Path("/tratamiento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TratamientoRS {
    // Instanciamos la capa de lógica de negocio (BL)
    private ITratamientoBL tratamientoBL;

    public TratamientoRS() {
        this.tratamientoBL = new TratamientoBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (POST / PUT / DELETE)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Tratamiento tratamiento) {
        return tratamientoBL.registrar(tratamiento);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Tratamiento tratamiento) {
        return tratamientoBL.actualizar(tratamiento);
    }

    /**
     * Endpoint para el botón de "Basurero" en el FrontEnd.
     * Ejecuta el borrado lógico (activo = 0) para que no aparezca más en la lista.
     */
    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idTratamiento) {
        return tratamientoBL.eliminar(idTratamiento);
    }

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public Tratamiento obtenerPorId(@PathParam("id") int idTratamiento) {
        return tratamientoBL.obtenerPorId(idTratamiento);
    }

    /**
     * Endpoint crucial: Trae la receta completa (todos los tratamientos)
     * recetados en una consulta médica en particular.
     */
    @GET
    @Path("/listarPorDetalle/{fidDetalle}")
    public List<Tratamiento> listarPorFidDetalle(@PathParam("fidDetalle") int fidDetalle) {
        return tratamientoBL.listarPorFidDetalle(fidDetalle);
    }
}
