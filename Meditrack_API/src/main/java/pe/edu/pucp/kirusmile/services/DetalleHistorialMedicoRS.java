package pe.edu.pucp.kirusmile.services;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.DetalleHistorialBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IDetalleHistorialBL;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;

import java.util.List;

@Path("/detalleHistorial")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DetalleHistorialMedicoRS {

    private IDetalleHistorialBL detalleBL;

    public DetalleHistorialMedicoRS() {
        // Instanciamos la lógica de negocio
        this.detalleBL = new DetalleHistorialBLImpl();
    }

    // --- MÉTODOS DE ESCRITURA (POST / PUT) ---

    @POST
    @Path("/registrar")
    public int registrar(DetalleHistorial detalle) {
        return detalleBL.registrar(detalle);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(DetalleHistorial detalle) {
        return detalleBL.actualizar(detalle);
    }

    /**
     * Usamos @QueryParam para la nota aclaratoria, así en Blazor puedes
     * enviarla en la URL de forma segura (ej. ?nota=Todo%20salio%20bien)
     */
    @PUT
    @Path("/cerrarConsulta/{id}")
    public int cerrarConsulta(@PathParam("id") int idDetalle,
                              @QueryParam("nota") String notaAclaratoria) {
        return detalleBL.cerrarConsulta(idDetalle, notaAclaratoria);
    }

    // --- MÉTODOS DE LECTURA (GET) ---

    /**
     * ¡Este es el endpoint del ENSAMBLAJE MAESTRO!
     * Devolverá el detalle junto con su Triaje, Anamnesis, Diagnósticos y Tratamientos.
     */
    @GET
    @Path("/obtenerPorId/{id}")
    public DetalleHistorial obtenerPorId(@PathParam("id") int idDetalle) {
        return detalleBL.obtenerPorId(idDetalle);
    }

    /**
     * Endpoint ligero para llenar la lista en la pantalla principal del Historial Médico
     */
    @GET
    @Path("/listarPorHistorial/{idHistorial}")
    public List<DetalleHistorial> listarPorFidHistorial(@PathParam("idHistorial") int idHistorial) {
        return detalleBL.listarPorFidHistorial(idHistorial);
    }


}
