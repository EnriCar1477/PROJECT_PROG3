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
        PacienteDAO pacienteDAO = new PacienteDAOImpl();
        MedicoDAO medicoDAO = new MedicoDAOImpl();
        CitaMedicaDAO citaMedicaDAO = new CitaMedicaDAOImpl();
        TratamientoDAO tratamientoDAO = new TratamientoDAOImpl();
        
        // ---------------------------------------------------------
        // 1. CREACIÓN DE DATOS (SAVE)
        // ---------------------------------------------------------
        System.out.println("\n1. Insertando Paciente...");
        Paciente p1 = new Paciente("12345678", "Juan", "Perez", "Gomez", new java.util.Date(), 
                                   "999888777", "juan@gmail.com", 1, "ACTIVO", true, false);
        pacienteDAO.save(p1);
        System.out.println("  -> Paciente guardado con ID autogenerado: " + p1.getId());

        System.out.println("\n2. Insertando Medico...");
        Medico m1 = new Medico("87654321", "Dr. Luis", "Sanchez", "Vera", new java.util.Date(), 
                               "999111222", "luis@med.com", 2, "CMP123", "RNE456", null, new java.util.Date(), 
                               "firma_digital_xyz", null, false);
        medicoDAO.save(m1);
        System.out.println("  -> Medico guardado con ID autogenerado: " + m1.getId());

        System.out.println("\n3. Insertando Cita Medica...");
        CitaMedica c1 = new CitaMedica(3, new java.util.Date(), LocalTime.of(10, 0), LocalTime.of(10, 30), p1, m1, "PROGRAMADA", false);
        citaMedicaDAO.save(c1);
        System.out.println("  -> Cita guardada con ID BD: " + c1.getIdCita());

        System.out.println("\n4. Insertando Tratamiento...");
        Tratamiento t1 = new Tratamiento(4, TipoTratamiento.PREVENTIVO, "Tomar paracetamol", new java.util.Date(), new java.util.Date(), false);
        tratamientoDAO.save(t1);
        System.out.println("  -> Tratamiento guardado (ID): " + t1.getIdTratamiento());


        // ---------------------------------------------------------
        // 2. LECTURA DE DATOS (LOAD)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE LECTURA (LOAD) usando el nuevo Integer ID ---");
        // Nota: Si la bd está vacía, p1.getId() tendría el primer ID (ej: 1)
        Integer idPacienteABuscar = p1.getId() != null ? p1.getId() : 1; 
        Paciente pLoaded = pacienteDAO.load(idPacienteABuscar);
        if(pLoaded != null) System.out.println("Paciente leido BD -> ID: " + pLoaded.getId() + " | Nombres: " + pLoaded.getNombres());
        
        Integer idMedicoABuscar = m1.getId() != null ? m1.getId() : 1;
        Medico mLoaded = medicoDAO.load(idMedicoABuscar);
        if(mLoaded != null) System.out.println("Medico leido BD -> ID: " + mLoaded.getId() + " | CMP: " + mLoaded.getCmp());
        

        // ---------------------------------------------------------
        // 3. ACTUALIZACION DE DATOS (UPDATE)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE ACTUALIZACION (UPDATE) ---");
        if (pLoaded != null) {
            System.out.println("Correo anterior: " + pLoaded.getCorreo());
            pLoaded.setCorreo("juan_nuevo_correo@hotmail.com");
            pacienteDAO.update(pLoaded);
            System.out.println("  -> Correo de Paciente actualizado en BD a: " + pLoaded.getCorreo());
        }
        
        // ---------------------------------------------------------
        // 4. ELIMINACION DE DATOS (SOFT DELETE)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE ELIMINACION (Soft Delete) ---");
        if (pLoaded != null) {
            pacienteDAO.remove(pLoaded);
            System.out.println("  -> Correcto: Paciente desactivado lógicamente (desactivado = true)");
        }

        System.out.println("\n=== Pruebas CRUD terminadas exitosamente ===");
    }
}