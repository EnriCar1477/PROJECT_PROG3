package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.util.List;

public interface PacienteDAO extends BaseDAO<Paciente> {

    /*
    * pusimos una regla de negocio que verifica si el DNI ya existe antes de registrar o actualizar a alguien para evitar duplicados.
    *Además, es la búsqueda más rápida para agendar citas.
    * */
    public Paciente obtenerPorDni(String dni);

    /*
     *"Hola, soy Juan Pérez, olvidé mi DNI, ¿a qué hora era mi cita?", la recepcionista necesita una barra de
     búsqueda en la web que filtre la tabla escribiendo letras.
     */

    public List<Paciente> listarPorNombre(String filtro);

}
