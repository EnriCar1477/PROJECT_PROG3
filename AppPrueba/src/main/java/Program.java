import pe.edu.pucp.kirusmile.BL.impl.*;
import pe.edu.pucp.kirusmile.BL.inter.*;
import pe.edu.pucp.kirusmile.dao.impl.*;

import pe.edu.pucp.kirusmile.models.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Program {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA KIRUSMILE ===");

        // Instanciamos los controladores de la Lógica de Negocio
        IEmpleadoBL empleadoBL = new EmpleadoBLImpl();
        IPacienteBL pacienteBL = new PacienteBLImpl();
        ICitaMedicaBL citaBL = new CitaMedicaBLImpl();
        IHistorialMedicoBL historialBL = new HistorialMedicoBLImpl();
        ILogAuditoriaBL auditoriaBL = new LogAuditoriaBLImpl();

        // ---------------------------------------------------------
        // EJEMPLO 1: EL ESCUDO DE SEGURIDAD (Login)
        // ---------------------------------------------------------
        System.out.println("\n1. Probando Autenticación de Usuario...");
        Empleado usuarioLogueado = empleadoBL.autenticar("admin123", "secreto123");

        if (usuarioLogueado != null) {
            System.out.println("¡Login exitoso! Bienvenido: " + usuarioLogueado.getNombres());
            System.out.println("Rol detectado: " + usuarioLogueado.getRol());
        } else {
            System.out.println("Acceso denegado. Revisa credenciales.");
            return; // Si falla, detenemos la prueba
        }

        // ---------------------------------------------------------
        // EJEMPLO 2: REGISTRO CON HERENCIA (Crear un Paciente)
        // ---------------------------------------------------------
        System.out.println("\n2. Probando Registro de Paciente...");
        Paciente nuevoPaciente = new Paciente("O", "+", "Universitario", "Ingeniero");
        // Datos heredados de Persona
        nuevoPaciente.setDni("87654321");
        nuevoPaciente.setNombres("Carlos");
        nuevoPaciente.setApellidoPaterno("Pérez");
        nuevoPaciente.setApellidoMaterno("Gómez");
        nuevoPaciente.setFechaNacimiento(LocalDate.of(1995, 5, 20));
        nuevoPaciente.setTelefono("987654321");
        nuevoPaciente.setCorreo("carlos@email.com");

        int idPacienteGenerado = pacienteBL.registrar(nuevoPaciente);
        if (idPacienteGenerado > 0) {
            System.out.println("Paciente registrado con éxito. ID Interno: " + idPacienteGenerado);
            // Le actualizamos el ID al objeto para usarlo en el siguiente ejemplo
            nuevoPaciente.setIdPaciente(idPacienteGenerado);
        }

        // ---------------------------------------------------------
        // EJEMPLO 3: LA MÁQUINA DE ESTADOS Y TRANSACCIONES (Cita Médica)
        // ---------------------------------------------------------
        System.out.println("\n3. Probando Agendamiento de Cita...");
        // Asumimos que ya existe un médico con ID 2 en la BD para esta prueba
        Medico medicoPrueba = new Medico();
        medicoPrueba.setIdMedico(2);

        CitaMedica nuevaCita = new CitaMedica();
        nuevaCita.setPaciente(nuevoPaciente);
        nuevaCita.setMedicoAsignado(medicoPrueba);
        nuevaCita.setEmpleado(usuarioLogueado); // Quien la registró
        nuevaCita.setFecha(LocalDate.now().plusDays(2)); // Cita para pasado mañana
        nuevaCita.setHoraInicio(LocalTime.of(10, 0)); // De 10:00 AM
        nuevaCita.setHoraFin(LocalTime.of(11, 0)); // a 11:00 AM
        nuevaCita.setMotivoAgendamiento("Dolor de muela del juicio");

        int idCitaGenerada = citaBL.registrar(nuevaCita);
        if (idCitaGenerada > 0) {
            System.out.println("Cita agendada con éxito. ID: " + idCitaGenerada);
            System.out.println("Estado inicial asignado por el BL: " + nuevaCita.getEstado());
        }

        // ---------------------------------------------------------
        // EJEMPLO 4: EL DIRECTOR DE ORQUESTA (Ensamblaje del Historial)
        // ---------------------------------------------------------
        System.out.println("\n4. Probando Ensamblaje de Historial Clínico...");
        // Asumiendo que el paciente que creamos generó el ID 1 o buscamos uno que ya
        // exista
        HistorialMedico historial = historialBL.obtenerPorIdPaciente(idPacienteGenerado);

        if (historial != null) {
            System.out.println("Historial recuperado exitosamente.");
            System.out.println("Cantidad de consultas pasadas (Detalles): " + historial.getListaDetalles().size());
        } else {
            System.out.println("El paciente aún no tiene un historial clínico aperturado.");
        }

        // ---------------------------------------------------------
        // EJEMPLO 5: LA CAJA NEGRA INMUTABLE (Auditoría)
        // ---------------------------------------------------------
        System.out.println("\n5. Probando Registro de Seguridad (Auditoría)...");
        LogAuditoria log = new LogAuditoria();
        log.setAccionRealizada("Registró al paciente " + nuevoPaciente.getDni() + " y agendó su primera cita.");
        log.setIpTerminal("192.168.1.45"); // Simulamos la IP de la recepcionista
        log.setEmpleado(usuarioLogueado);

        int idLog = auditoriaBL.registrar(log);
        if (idLog > 0) {
            System.out.println("Acción auditada de forma segura e inmutable. ID Log: " + idLog);
        }

        System.out.println("\n=== PRUEBAS FINALIZADAS ===");
    }

}
