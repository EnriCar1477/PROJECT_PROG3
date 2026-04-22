import pe.edu.pucp.kirusmile.dao.*;
import pe.edu.pucp.kirusmile.dao.impl.*;
import pe.edu.pucp.kirusmile.models.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO PRUEBAS CRUD ---");

        // Inicializar DAOs
        EspecialidadDAO especialidadDAO = new EspecialidadDAOImpl();
        PacienteDAO pacienteDAO = new PacienteDAOImpl();
        MedicoDAO medicoDAO = new MedicoDAOImpl();
        EnfermedadCIE10DAO enfermedadDAO = new EnfermedadCIE10DAOImpl();
        
        // ---------------------------------------------------------
        // 1. CREACIÓN DE DATOS (SAVE)
        // ---------------------------------------------------------
        System.out.println("\n1. Insertando Especialidad...");
        Especialidad esp = new Especialidad(2, "Odontologo", 70.0,true);
        especialidadDAO.save(esp);
        System.out.println("  -> Especialidad guardado con ID autogenerado: " + esp.getIdEspecialidad());

        System.out.println("\n2. Insertando Paciente...");
        Paciente p1 = new Paciente("87654321", "Dr. Luis", "Sanchez", "Vera", new java.util.Date(),
                               "999111222", "luis@med.com", "O", "+", "Tecnico", "Estudiante",
                               "peruano");
        pacienteDAO.save(p1);
        System.out.println("  -> Paciente guardado con ID autogenerado: " + p1.getDni());


        System.out.println("\n2. Insertando Medico...");
        Medico m1 = new Medico("87654321", "Dr. Luis", "Sanchez", "Vera", new java.util.Date(),
                "999111222", "luis@med.com", "EMP001", new java.util.Date(), "3", "56", null, null,
                "peruano", null, false);
        medicoDAO.save(m1);
        System.out.println("  -> Medico guardado con ID autogenerado: " + m1.getDni());

        System.out.println("\n3. Insertando Enfermedad (Prueba Local)...");
        EnfermedadCIE10 enfermedad = new EnfermedadCIE10("K02.9", "Caries dental, no especificada");
        enfermedadDAO.save(enfermedad);
        System.out.println("  -> Enfermedad creada en memoria: " + enfermedad.getCodigocCIE() + " - " + enfermedad.getDescripcionOficial());


        // ---------------------------------------------------------
        // 2. LECTURA DE DATOS (LOAD)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE LECTURA (LOAD) usando el nuevo Integer ID ---");
        // Nota: Si la bd está vacía, p1.getId() tendría el primer ID (ej: 1)
        Integer idEspecialidadABuscar = esp.getIdEspecialidad() != -1 ? esp.getIdEspecialidad() : 1;
        Especialidad pLoaded = especialidadDAO.load(idEspecialidadABuscar);
        if(pLoaded != null) System.out.println("Especialidad leida BD -> ID: " + pLoaded.getIdEspecialidad() + " | Nombre: " + pLoaded.getNombreEspecialidad());
/*
        Integer idMedicoABuscar = m1.getId() != null ? m1.getId() : 1;
        Medico mLoaded = medicoDAO.load(idMedicoABuscar);
        if(mLoaded != null) System.out.println("Medico leido BD -> ID: " + mLoaded.getId() + " | CMP: " + mLoaded.getCmp());
 */

        // ---------------------------------------------------------
        // 3. ACTUALIZACION DE DATOS (UPDATE)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE ACTUALIZACION (UPDATE) ---");
        if (pLoaded != null) {
            System.out.println("Nombre anterior: " + pLoaded.getNombreEspecialidad());
            pLoaded.setNombreEspecialidad("Pediatra");
            especialidadDAO.update(pLoaded);
            System.out.println("  -> Nombre de especialidad actualizado en BD a: " + pLoaded.getNombreEspecialidad());
        }
        
        // ---------------------------------------------------------
        // 4. ELIMINACION DE DATOS (SOFT DELETE)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE ELIMINACION (Soft Delete) ---");
        if (pLoaded != null) {
            especialidadDAO.remove(pLoaded);
            System.out.println("  -> Correcto: Especialidad desactivado lógicamente (desactivado = true)");
        }

        System.out.println("\n=== Pruebas CRUD terminadas exitosamente ===");
    }
}
