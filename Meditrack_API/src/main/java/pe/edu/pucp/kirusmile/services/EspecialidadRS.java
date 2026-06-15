package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.EspecialidadBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IEspecialidadBL;
import pe.edu.pucp.kirusmile.models.Especialidad;

import java.util.List;

@Path("/especialidad")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecialidadRS {
    // Instanciamos la capa de lógica de negocio (BL)
    private IEspecialidadBL especialidadBL;

    public EspecialidadRS() {
        this.especialidadBL = new EspecialidadBLImpl();
    }

    // ==========================================
    // MÉTODOS DE ESCRITURA (POST / PUT / DELETE)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Especialidad especialidad) {
        return especialidadBL.registrar(especialidad);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Especialidad especialidad) {
        return especialidadBL.actualizar(especialidad);
    }

    /**
     * Endpoint para "eliminar" una especialidad.
     * Recuerda que gracias a tu DAO, esto en realidad hará un borrado lógico (activo = 0).
     */
    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idEspecialidad) {
        return especialidadBL.eliminar(idEspecialidad);
    }

    // ==========================================
    // MÉTODOS DE LECTURA (GET)
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public Especialidad obtenerPorId(@PathParam("id") int idEspecialidad) {
        return especialidadBL.obtenerPorId(idEspecialidad);
    }

    /**
     * Endpoint para el módulo de ADMINISTRACIÓN.
     * Trae absolutamente todas las especialidades (incluyendo las dadas de baja)
     * para que el administrador pueda ver el historial o reactivarlas.
     */
    @GET
    @Path("/listarTodas")
    public List<Especialidad> listarTodas() {
        return especialidadBL.listarTodas();
    }

    /**
     * Endpoint para el módulo de RECEPCIÓN / SECRETARÍA.
     * Trae SOLO las especialidades activas. Ideal para llenar el <select> o ComboBox
     * al momento de agendar citas o registrar médicos nuevos.
     */
    @GET
    @Path("/listarActivas")
    public List<Especialidad> listarActivas() {
        return especialidadBL.listarActivas();
    }
}
