package pe.edu.pucp.meditrack.REST;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.PacienteBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IPacienteBL;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.util.List;

@Path("/paciente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteRS {
    // Instanciamos la interfaz de la capa de negocio
    private IPacienteBL pacienteBL;

    public PacienteRS() {
        this.pacienteBL = new PacienteBLImpl();
    }

    // --- MÉTODOS DE ESCRITURA (POST / PUT / DELETE) ---

    @POST
    @Path("/registrar")
    public int registrar(Paciente paciente) {
        return pacienteBL.registrar(paciente);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Paciente paciente) {
        return pacienteBL.actualizar(paciente);
    }

    // Usamos DELETE porque es el verbo HTTP semánticamente correcto para borrar,
    // aunque por debajo en Java estemos haciendo un borrado lógico (UPDATE).
    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idPaciente) {
        return pacienteBL.eliminar(idPaciente);
    }

    // --- MÉTODOS DE LECTURA (GET) ---

    @GET
    @Path("/obtenerPorId/{id}")
    public Paciente obtenerPorId(@PathParam("id") int idPaciente) {
        return pacienteBL.obtenerPorId(idPaciente);
    }

    @GET
    @Path("/obtenerPorDni/{dni}")
    public Paciente obtenerPorDni(@PathParam("dni") String dni) {
        return pacienteBL.obtenerPorDni(dni);
    }

    @GET
    @Path("/listarTodos")
    public List<Paciente> listarTodos() {
        return pacienteBL.listarTodos();
    }

    @GET
    @Path("/listarPorMedico/{idMedico}")
    public List<Paciente> listarPorFidMedico(@PathParam("idMedico") int idMedico) {
        return pacienteBL.listarPorFidMedico(idMedico);
    }
}
