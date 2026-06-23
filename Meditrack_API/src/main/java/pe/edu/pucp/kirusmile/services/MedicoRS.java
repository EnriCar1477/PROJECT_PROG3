package pe.edu.pucp.kirusmile.services;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.MedicoBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IMedicoBL;
import pe.edu.pucp.kirusmile.models.Medico;

import java.util.List;

// 1. Definimos la ruta principal del servicio
@Path("/medico")
// 2. Indicamos que todos los métodos enviarán y recibirán datos en formato JSON
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoRS {

    // Instanciamos la capa de negocio
    private IMedicoBL medicoBL;

    public MedicoRS() {
        this.medicoBL = new MedicoBLImpl();
    }
    // --- MÉTODOS DEL SERVICIO ---

    @POST
    @Path("/registrar")
    public int registrar(Medico medico) {
        return medicoBL.registrar(medico);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Medico medico) {
        return medicoBL.actualizar(medico);
    }

    //el método obtenerPorId de MedicoRS ya te devuelve al Médico con su lista de
    // horarios adentro (gracias al ensamblaje que hicimos en el MedicoBLImpl),
    @GET
    @Path("/obtenerPorId/{id}")
    public Medico obtenerPorId(@PathParam("id") int id) {
        return medicoBL.obtenerPorId(id);
    }

    @GET
    @Path("/obtenerPorFidEmpleado/{fidEmpleado}")
    public Medico obtenerPorFidEmpleado(@PathParam("fidEmpleado") int fidEmpleado) {
        // Útil para cuando el médico inicie sesión
        return medicoBL.obtenerPorFidEmpleado(fidEmpleado);
    }

    @GET
    @Path("/listarTodos")
    public List<Medico> listarTodos() {
        return medicoBL.listarTodos();
    }

    @GET
    @Path("/listarMedicosDatosBasicos")
    public List<Medico> listarMedicosDatosBasicos() {
        return medicoBL.listarMedicosDatosBasicos();
    }
}

