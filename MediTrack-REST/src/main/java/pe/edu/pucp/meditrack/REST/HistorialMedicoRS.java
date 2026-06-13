package pe.edu.pucp.meditrack.REST;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.HistorialMedicoBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IHistorialMedicoBL;
import pe.edu.pucp.kirusmile.models.HistorialMedico;

import java.util.List;

@Path("/historial")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistorialMedicoRS {
    // Instanciamos la capa de lógica de negocio (BL)
    private IHistorialMedicoBL historialBL;

    public HistorialMedicoRS() {
        this.historialBL = new HistorialMedicoBLImpl();
    }

    // --- MÉTODOS DE ESCRITURA (POST / PUT) ---

    @POST
    @Path("/registrar")
    public int registrar(HistorialMedico historial) {
        return historialBL.registrar(historial);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(HistorialMedico historial) {
        return historialBL.actualizar(historial);
    }

    // --- MÉTODOS DE LECTURA (GET) ---

    /**
     * Este es el endpoint estrella para tu Blazor.
     * Recibe el ID del paciente y devuelve el historial completo con todas sus citas.
     */
    @GET
    @Path("/paciente/{idPaciente}")
    public HistorialMedico obtenerPorIdPaciente(@PathParam("idPaciente") int idPaciente) {
        return historialBL.obtenerPorIdPaciente(idPaciente);
    }

    /**
     * Endpoint por si necesitas buscar el historial directamente por su propio ID
     */
    @GET
    @Path("/obtenerPorId/{idHistorial}")
    public HistorialMedico obtenerPorIdHistorial(@PathParam("idHistorial") int idHistorial) {
        return historialBL.obtenerPorIdHistorial(idHistorial);
    }

    /**
     * Endpoint para la barra de búsqueda en Blazor (por DNI o Apellido)
     */
    @GET
    @Path("/buscar/{filtro}")
    public List<HistorialMedico> buscarPorFiltro(@PathParam("filtro") String filtro) {
        return historialBL.listarPorDniOApellido(filtro);
    }
}
