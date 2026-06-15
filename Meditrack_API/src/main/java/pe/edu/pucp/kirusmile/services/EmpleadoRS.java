package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.kirusmile.BL.impl.EmpleadoBLImpl;
import pe.edu.pucp.kirusmile.BL.inter.IEmpleadoBL;
import pe.edu.pucp.kirusmile.models.Empleado;

import java.util.List;

@Path("/empleado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpleadoRS {
    private IEmpleadoBL empleadoBL;

    public EmpleadoRS() {
        this.empleadoBL = new EmpleadoBLImpl();
    }

    // ==========================================
    // MÉTODOS DE SEGURIDAD Y LOGIN
    // ==========================================

    /**
     * Endpoint para el inicio de sesión.
     * Usamos @POST para que las credenciales viajen de forma segura en el Body (JSON) y no en la URL.
     */
    @POST
    @Path("/autenticar")
    public Empleado autenticar(Empleado credenciales) {
        // Blazor enviará un objeto Empleado JSON solo con el 'username' y 'passwordHash' llenos
        return empleadoBL.autenticar(credenciales.getUsername(), credenciales.getPasswordHash());
    }

    // ==========================================
    // MÉTODOS DE GESTIÓN (CRUD)
    // ==========================================

    @POST
    @Path("/registrar")
    public int registrar(Empleado empleado) {
        return empleadoBL.registrar(empleado);
    }

    @PUT
    @Path("/actualizar")
    public int actualizar(Empleado empleado) {
        return empleadoBL.actualizar(empleado);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public int eliminar(@PathParam("id") int idEmpleado) {
        return empleadoBL.eliminar(idEmpleado);
    }

    // ==========================================
    // MÉTODOS DE BÚSQUEDA Y LECTURA
    // ==========================================

    @GET
    @Path("/obtenerPorId/{id}")
    public Empleado obtenerPorId(@PathParam("id") int idEmpleado) {
        return empleadoBL.obtenerPorId(idEmpleado);
    }

    @GET
    @Path("/obtenerPorUsername/{username}")
    public Empleado obtenerPorUsername(@PathParam("username") String username) {
        return empleadoBL.obtenerPorUsername(username);
    }

    @GET
    @Path("/listarTodos")
    public List<Empleado> listarTodos() {
        return empleadoBL.listarTodos();
    }

}
