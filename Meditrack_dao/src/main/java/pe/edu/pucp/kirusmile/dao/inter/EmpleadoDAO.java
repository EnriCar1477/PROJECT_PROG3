package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Empleado;

import java.time.LocalDateTime;

public interface EmpleadoDAO extends BaseDAO<Empleado> {
    // Método vital para el Login: buscar por usuario
    public Empleado obtenerPorUsername(String username);
    public void registrarUltimoAcceso(int idEmpleado, LocalDateTime fecha);

    public Empleado obtenerPorDni(String dni);
}