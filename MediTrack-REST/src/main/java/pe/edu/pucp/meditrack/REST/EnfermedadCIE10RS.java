package pe.edu.pucp.meditrack.REST;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.EnfermedadCIE10BLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IEnfermedadCIE10BL;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.util.List;

@Path("/cie10")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnfermedadCIE10RS {

    // Instanciamos la capa de lógica de negocio (BL)
    private IEnfermedadCIE10BL enfermedadBL;

    public EnfermedadCIE10RS() {
        this.enfermedadBL = new EnfermedadCIE10BLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (POST / PUT)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(EnfermedadCIE10 enfermedad) {
        return enfermedadBL.registrar(enfermedad);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(EnfermedadCIE10 enfermedad) {
        return enfermedadBL.actualizar(enfermedad);
    }

    // Nota: ¡No hay @DELETE! Protegemos la integridad del catálogo de la OMS.

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    /**
     * Endpoint PRINCIPAL para tu FrontEnd.
     * Carga todas las enfermedades ordenadas alfabéticamente para llenar tu Combobox en Blazor.
     */
    @GET
    @Path("/listarTodos")
    public List<EnfermedadCIE10> listarTodos() {
        return enfermedadBL.listarTodos();
    }

    @GET
    @Path("/obtenerPorId/{id}")
    public EnfermedadCIE10 obtenerPorId(@PathParam("id") int idEnfermedad) {
        return enfermedadBL.obtenerPorId(idEnfermedad);
    }

    /**
     * Endpoint por si necesitas buscar la enfermedad directamente por su código "K02.1".
     */
    @GET
    @Path("/obtenerPorCodigo/{codigoCIE}")
    public EnfermedadCIE10 obtenerPorCodigoCIE(@PathParam("codigoCIE") String codigoCIE) {
        return enfermedadBL.obtenerPorCodigoCIE(codigoCIE);
    }




}
