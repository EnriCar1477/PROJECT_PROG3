package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.Empleado;
import java.util.List;

public interface IEmpleadoBL {
    // Registra un empleado validando datos personales, laborales y de usuario
    int registrar(Empleado empleado);

    // Actualiza datos (ej. si le suben el sueldo o cambia de rol)
    int actualizar(Empleado empleado);

    // Realiza el borrado lógico / Desactiva su acceso al sistema
    int eliminar(int idEmpleado);

    // Método vital de SEGURIDAD: Verifica el login de un usuario
    Empleado autenticar(String username, String passwordPlana);

    // Obtiene al empleado por su ID y ensambla su lista de auditorías
    Empleado obtenerPorId(int idEmpleado);

    // Busca un empleado por su nombre de usuario
    Empleado obtenerPorUsername(String username);

    // Lista a todos los empleados activos
    List<Empleado> listarTodos();

}
