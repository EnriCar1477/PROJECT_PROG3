package pe.edu.pucp.kirusmile.dao.inter;

import pe.edu.pucp.kirusmile.dao.base.BaseDAO;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.util.List;

public interface PacienteDAO extends BaseDAO<Paciente> {

    public Paciente obtenerPorDni(String dni);
    public List<Paciente> listarPorFidMedico(int fidMedico);

    public List<Paciente> listarPorNombre(String filtro);

}
