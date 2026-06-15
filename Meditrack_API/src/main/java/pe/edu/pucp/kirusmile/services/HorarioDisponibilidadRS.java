package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.HorarioDisponibilidadBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IHorarioDisponibilidadBL;
import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;

import java.util.List;

@Path("/horario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

//Cuando el administrador quiere agregar un nuevo día de atención o
//borrar un horario específico solo vas a mandar todo el objeto Médico
//gigante de regreso. Vas a mandar solo el objeto HorarioDisponibilidad

public class HorarioDisponibilidadRS {
    private IHorarioDisponibilidadBL horarioBL;

    public HorarioDisponibilidadRS() {
        this.horarioBL = new HorarioDisponibilidadBLImpl();
    }

    @POST
    @Path("/registrar")
    public int registrar(HorarioDisponibilidad horario) {
        return horarioBL.registrar(horario);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(HorarioDisponibilidad horario) {
        return horarioBL.actualizar(horario);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idHorario) {
        return horarioBL.eliminar(idHorario);
    }

    @GET
    @Path("/listarPorMedico/{fidMedico}")
    public List<HorarioDisponibilidad> listarPorFidMedico(@PathParam("fidMedico") int fidMedico) {
        return horarioBL.listarPorFidMedico(fidMedico);
    }
}


