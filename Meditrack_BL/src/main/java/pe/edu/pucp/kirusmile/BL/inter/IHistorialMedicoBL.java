package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.HistorialMedico;

import java.util.List;

public interface IHistorialMedicoBL  {
    // Registra un nuevo historial validando que el paciente exista
    int registrar(HistorialMedico historial);

    // Actualiza datos administrativos (como el estado físico del folder)
    int actualizar(HistorialMedico historial);

    // Método vital: Trae el historial COMPLETO (con toda su lista de detalles)
    HistorialMedico obtenerPorIdPaciente(int idPaciente);

    // Trae el historial por su propio ID
    HistorialMedico obtenerPorIdHistorial(int idHistorial);

    public List<HistorialMedico> listarPorDniOApellido(String filtro);


}
