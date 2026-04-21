import pe.edu.pucp.kirusmile.dao.*;
import pe.edu.pucp.kirusmile.dao.impl.*;
import pe.edu.pucp.kirusmile.models.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Program {
    public static void main() {
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
                "999888777", "juan@gmail.com", "O+", "Positivo", "Superior",
                "Ingeniero", "Mestizo");
        pacienteDAO.save(p1);
        System.out.println("  -> Paciente guardado con DNI: " + p1.getDni());

        System.out.println("\n2. Insertando Medico...");
        Medico m1 = new Medico("87654321", "Dr. Luis", "Sanchez", "Vera", new java.util.Date(),
                "999111222", "luis@med.com", "CMP123", "RNE456", null, new java.util.Date(),
                "firma_digital_xyz", null);
        medicoDAO.save(m1);
        System.out.println("  -> Medico guardado con DNI: " + m1.getDni());

        System.out.println("\n3. Insertando Cita Medica...");
        CitaMedica c1 = new CitaMedica();
        c1.setFecha(LocalDate.now());
        c1.setHoraInicio(LocalTime.of(10, 0));
        c1.setHoraFin(LocalTime.of(10, 30));
        c1.setEstado(EstadoCita.PROGRAMADA);
        c1.setPaciente(p1);
        c1.setMedicoAsignado(m1);
        citaMedicaDAO.save(c1);
        System.out.println("  -> Cita guardada con ID BD: " + c1.getIdCita());

        System.out.println("\n4. Insertando Tratamiento...");
        Tratamiento t1 = new Tratamiento(0, TipoTratamiento.PREVENTIVO, "Tomar paracetamol 500mg cada 8 horas",
                new java.util.Date(), new java.util.Date());
        tratamientoDAO.save(t1);
        System.out.println("  -> Tratamiento guardado (indicaciones): " + t1.getIndicaciones());

        // ---------------------------------------------------------
        // 2. LECTURA DE DATOS (LOAD)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE LECTURA (LOAD) ---");
        Paciente pLoaded = pacienteDAO.load("12345678");
        if (pLoaded != null)
            System.out.println("Paciente leido BD -> DNI: " + pLoaded.getDni() + " | Nombres: " + pLoaded.getNombres());

        Medico mLoaded = medicoDAO.load("87654321");
        if (mLoaded != null)
            System.out.println("Medico leido BD -> DNI: " + mLoaded.getDni() + " | CMP: " + mLoaded.getCmp());

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
        // 4. ELIMINACION DE DATOS (REMOVE - DENEGADO)
        // ---------------------------------------------------------
        System.out.println("\n--- PRUEBA DE ELIMINACION (Debe saltar error por regla de negocio) ---");
        try {
            pacienteDAO.remove(p1);
        } catch (UnsupportedOperationException ex) {
            System.out.println("  -> Correcto: " + ex.getMessage());
        }

        System.out.println("\n=== Pruebas CRUD terminadas exitosamente ===");
    }

}