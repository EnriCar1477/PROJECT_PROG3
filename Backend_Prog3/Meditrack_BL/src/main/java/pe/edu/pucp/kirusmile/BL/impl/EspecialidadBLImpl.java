package pe.edu.pucp.kirusmile.BL.impl;

import pe.edu.pucp.kirusmile.BL.inter.IEspecialidadBL;
import pe.edu.pucp.kirusmile.dao.impl.EspecialidadDAOImpl;
import pe.edu.pucp.kirusmile.dao.inter.EspecialidadDAO;
import pe.edu.pucp.kirusmile.models.Especialidad;

import java.util.List;

public class EspecialidadBLImpl implements IEspecialidadBL {

    private EspecialidadDAO especialidadDAO;

    public EspecialidadBLImpl() {
        this.especialidadDAO = new EspecialidadDAOImpl();
    }

    @Override
    public int registrar(Especialidad especialidad) {
        if (!validarCampos(especialidad)) {
            return 0;
        }

        // Regla de Unicidad: Verificar que el nombre no exista ya en la base de datos
        Especialidad existente = especialidadDAO.obtenerPorNombre(especialidad.getNombreEspecialidad());
        if (existente != null) {
            System.err.println("Error BL: La especialidad '" + especialidad.getNombreEspecialidad() + "' ya está registrada.");
            return 0; // Bloqueamos la creación de duplicados
        }

        // Por defecto, al crearla debe estar activa
        especialidad.setActivo(true);

        return especialidadDAO.save(especialidad);
    }

    @Override
    public int actualizar(Especialidad especialidad) {
        if (especialidad.getIdEspecialidad() == 0) {
            System.err.println("Error BL: No se puede actualizar una especialidad sin su ID.");
            return 0;
        }

        if (!validarCampos(especialidad)) {
            return 0;
        }

        // Regla de Unicidad (Avanzada): Verificar si le están cambiando el nombre a uno que ya tiene otra especialidad
        Especialidad existente = especialidadDAO.obtenerPorNombre(especialidad.getNombreEspecialidad());
        if (existente != null && existente.getIdEspecialidad() != especialidad.getIdEspecialidad()) {
            System.err.println("Error BL: El nombre '" + especialidad.getNombreEspecialidad() + "' ya pertenece a otra especialidad.");
            return 0;
        }

        return especialidadDAO.update(especialidad);
    }

    @Override
    public int eliminar(int idEspecialidad) {
        if (idEspecialidad == 0) {
            System.err.println("Error BL: ID de especialidad inválido para eliminar.");
            return 0;
        }
        // El DAO se encargará de hacer el UPDATE activo = 0 (Borrado lógico)
        return especialidadDAO.delete(idEspecialidad);
    }

    @Override
    public Especialidad obtenerPorId(int idEspecialidad) {
        return especialidadDAO.load(idEspecialidad);
    }

    @Override
    public List<Especialidad> listarTodas() {
        return especialidadDAO.listALL();
    }

    @Override
    public List<Especialidad> listarActivas() {
        // Ideal para llenar el ComboBox al momento de registrar un nuevo Médico
        return especialidadDAO.listarActivas();
    }

    // --- MÉTODOS PRIVADOS DE REGLAS DE NEGOCIO ---
    /**
     * Valida que los datos de la especialidad tengan sentido comercial.
     */
    private boolean validarCampos(Especialidad especialidad) {
        if (especialidad.getNombreEspecialidad() == null || especialidad.getNombreEspecialidad().trim().isEmpty()) {
            System.err.println("Error BL: El nombre de la especialidad es obligatorio.");
            return false;
        }

        // Regla Financiera: No puede haber costos negativos
        if (especialidad.getCostoEspecialidad() < 0) {
            System.err.println("Error BL: El costo de la especialidad no puede ser un valor negativo.");
            return false;
        }

        // Estandarizamos el texto (quitamos espacios en blanco accidentales al inicio y al final)
        // Opcional: podrías usar .toUpperCase() si quieres que todas las especialidades se guarden en mayúsculas.
        especialidad.setNombreEspecialidad(especialidad.getNombreEspecialidad().trim());

        return true;
    }


}
