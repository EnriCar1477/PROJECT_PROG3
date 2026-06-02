package pe.edu.pucp.kirusmile.BL.inter;

import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.util.List;

public interface IEnfermedadCIE10BL {
    // Registra una nueva enfermedad validando que el código no exista previamente
    int registrar(EnfermedadCIE10 enfermedad);

    // Actualiza la descripción de una enfermedad
    int actualizar(EnfermedadCIE10 enfermedad);

    // Obtiene una enfermedad por su ID interno
    EnfermedadCIE10 obtenerPorId(int idEnfermedad);

    // Método especializado: Busca por el código exacto de la OMS (Ej. "K02.1")
    EnfermedadCIE10 obtenerPorCodigoCIE(String codigoCIE);

    // Lista todas las enfermedades para llenar los ComboBox del Front-end
    List<EnfermedadCIE10> listarTodos();

}
