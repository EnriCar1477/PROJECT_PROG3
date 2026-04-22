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
        MedicoDAO medicoDAO = new MedicoDAOImpl();
        CitaMedicaDAO citaMedicaDAO = new CitaMedicaDAOImpl();
        TratamientoDAO tratamientoDAO = new TratamientoDAOImpl();
        
        // ---------------------------------------------------------
        // 1. CREACIÓN DE DATOS (SAVE)
        // ---------------------------------------------------------
        System.out.println("\n1. Insertando Especialidad...");
        Especialidad esp = new Especialidad(2, "Endocrinologia", 70.0,true);
        especialidadDAO.save(esp);
        System.out.println("  -> Especialidad guardado con ID autogenerado: " + esp.getIdEspecialidad());
/*
        System.out.println("\n2. Insertando Medico...");
        Medico m1 = new Medico("87654321", "Dr. Luis", "Sanchez", "Vera", new java.util.Date(), 
                               "999111222", "luis@med.com", 2, "CMP123", "RNE456", null, new java.util.Date(), 
                               "firma_digital_xyz", null, false);
        medicoDAO.save(m1);
        System.out.println("  -> Medico guardado con ID autogenerado: " + m1.getId());
*/
/*
        System.out.println("\n4. Insertando Tratamiento...");
        Tratamiento t1 = new Tratamiento(4, TipoTratamiento.PREVENTIVO, "Tomar paracetamol", new java.util.Date(), new java.util.Date(), false);
        tratamientoDAO.save(t1);
        System.out.println("  -> Tratamiento guardado (ID): " + t1.getIdTratamiento());
*/

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
            pLoaded.setNombreEspecialidad("Dentista");
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
