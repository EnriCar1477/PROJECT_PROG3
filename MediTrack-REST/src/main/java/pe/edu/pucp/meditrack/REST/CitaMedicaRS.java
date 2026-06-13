package pe.edu.pucp.meditrack.REST;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.CitaMedicaBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.ICitaMedicaBL;
import pe.edu.pucp.kirusmile.models.CitaMedica;

import java.util.List;

@Path("/cita")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitaMedicaRS {

    private ICitaMedicaBL citaBL;

    public CitaMedicaRS() {
        // Instanciamos la lógica de negocio
        this.citaBL = new CitaMedicaBLImpl();
    }

    @POST
    @Path("/registrar")
    public int registrar(CitaMedica cita) {
        return citaBL.registrar(cita);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(CitaMedica cita) {
        return citaBL.actualizar(cita);
    }

    @PUT
    @Path("/cancelar/{id}")
    public int cancelarCita(@PathParam("id") int idCita) {
        return citaBL.cancelarCita(idCita);
    }

    @PUT
    @Path("/registrarPago/{id}/{monto}/{metodo}")
    public int registrarPago(@PathParam("id") int idCita,
                             @PathParam("monto") double monto,
                             @PathParam("metodo") String metodoPago) {
        return citaBL.registrarPago(idCita, monto, metodoPago);
    }

    @GET
    @Path("/obtenerPorId/{id}")
    public CitaMedica obtenerPorId(@PathParam("id") int idCita) {
        return citaBL.obtenerPorId(idCita);
    }

    @GET
    @Path("/listarPorMedico/{idMedico}")
    public List<CitaMedica> listarPorFidMedico(@PathParam("idMedico") int idMedico) {
        return citaBL.listarPorFidMedico(idMedico);
    }

    @GET
    @Path("/listarPorPaciente/{idPaciente}")
    public List<CitaMedica> listarPorFidPaciente(@PathParam("idPaciente") int idPaciente) {
        return citaBL.listarPorFidPaciente(idPaciente);
    }


}
