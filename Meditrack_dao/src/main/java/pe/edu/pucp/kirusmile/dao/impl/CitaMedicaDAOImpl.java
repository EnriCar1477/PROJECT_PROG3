package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.CitaMedicaDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.CitaMedica;
import pe.edu.pucp.kirusmile.models.EstadoCita;
import pe.edu.pucp.kirusmile.models.Medico;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaMedicaDAOImpl implements CitaMedicaDAO {

    private Connection con;

    public CitaMedicaDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(CitaMedica objeto) {
        if (objeto.getPaciente() == null || objeto.getMedicoAsignado() == null) {
            System.err.println("Error: Paciente o Médico no pueden ser nulos.");
            return 0;
        }

        int idGenerado = 0;
        String sql = "INSERT INTO CitaMedica (fid_paciente, fid_medico, fid_empleado, fecha, " +
                "hora_inicio, hora_fin, motivo_agendamiento, fid_estado_cita, monto, " +
                "fecha_hora_pago, metodo_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, objeto.getPaciente().getIdPaciente());
            pst.setInt(2, objeto.getMedicoAsignado().getIdMedico());

            if (objeto.getEmpleado() != null) pst.setInt(3, objeto.getEmpleado().getIdEmpleado());
            else pst.setNull(3, java.sql.Types.INTEGER);

            pst.setDate(4, java.sql.Date.valueOf(objeto.getFecha()));
            pst.setTime(5, java.sql.Time.valueOf(objeto.getHoraInicio()));
            pst.setTime(6, java.sql.Time.valueOf(objeto.getHoraFin()));
            pst.setString(7, objeto.getMotivoAgendamiento());
            pst.setInt(8, objeto.getEstado().ordinal() + 1);
            pst.setDouble(9, objeto.getMonto());

            if (objeto.getFechaHoraPago() != null) pst.setTimestamp(10, Timestamp.valueOf(objeto.getFechaHoraPago()));
            else pst.setNull(10, Types.TIMESTAMP);

            pst.setString(11, objeto.getMetodoPago());

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar CitaMedica: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(CitaMedica objeto) {
        int filas = 0;
        String sql = "UPDATE CitaMedica SET fecha = ?, hora_inicio = ?, hora_fin = ?, " +
                "motivo_agendamiento = ?, fid_estado_cita = ?, monto = ?, " +
                "fecha_hora_pago = ?, metodo_pago = ? WHERE id_cita_medica = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDate(1, java.sql.Date.valueOf(objeto.getFecha()));
            pst.setTime(2, java.sql.Time.valueOf(objeto.getHoraInicio()));
            pst.setTime(3, java.sql.Time.valueOf(objeto.getHoraFin()));
            pst.setString(4, objeto.getMotivoAgendamiento());
            pst.setInt(5, objeto.getEstado().ordinal() + 1);
            pst.setDouble(6, objeto.getMonto());

            if (objeto.getFechaHoraPago() != null) {
                pst.setTimestamp(7, Timestamp.valueOf(objeto.getFechaHoraPago()));
            } else {
                pst.setNull(7, java.sql.Types.TIMESTAMP);
            }

            pst.setString(8, objeto.getMetodoPago());
            pst.setInt(9, objeto.getIdCitaMedica());

            filas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar CitaMedica: " + e.getMessage());
        }
        return filas;
    }

    @Override
    public int delete(int id) {
        String sql = "UPDATE CitaMedica SET activo = 0 WHERE id_cita_medica = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate();
        } catch (SQLException e) { return 0; }
    }

    @Override
    public CitaMedica load(int id) {
        CitaMedica cita = null;
        String sql = "SELECT c.*, p.nombres AS p_nombres, p.apellido_paterno AS p_ap, " +
                "per_m.nombres AS m_nombres, per_m.apellido_paterno AS m_ap " +
                "FROM CitaMedica c " +
                "INNER JOIN Paciente pac ON c.fid_paciente = pac.id_paciente " +
                "INNER JOIN Persona p ON pac.fid_persona = p.id_persona " +
                "INNER JOIN Medico m ON c.fid_medico = m.id_medico " +
                "INNER JOIN Empleado emp ON m.fid_empleado = emp.id_empleado " +
                "INNER JOIN Persona per_m ON emp.fid_persona = per_m.id_persona " +
                "WHERE c.id_cita_medica = ? AND c.activo = 1";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cita = new CitaMedica();
                    cita.setIdCitaMedica(rs.getInt("id_cita_medica"));
                    cita.setFecha(rs.getDate("fecha").toLocalDate());
                    cita.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    cita.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    cita.setMotivoAgendamiento(rs.getString("motivo_agendamiento"));
                    cita.setActivo(rs.getBoolean("activo"));

                    // Seguridad Enum
                    int idEstado = rs.getInt("fid_estado_cita");
                    if (idEstado >= 1 && idEstado <= EstadoCita.values().length)
                        cita.setEstado(EstadoCita.values()[idEstado - 1]);

                    cita.setMonto(rs.getDouble("monto"));
                    if (rs.getTimestamp("fecha_hora_pago") != null)
                        cita.setFechaHoraPago(rs.getTimestamp("fecha_hora_pago").toLocalDateTime());

                    // Ensamblaje Paciente
                    Paciente p = new Paciente();
                    p.setIdPaciente(rs.getInt("fid_paciente"));
                    p.setNombres(rs.getString("p_nombres"));
                    p.setApellidoPaterno(rs.getString("p_ap"));
                    cita.setPaciente(p);

                    // Ensamblaje Médico
                    Medico m = new Medico();
                    m.setIdMedico(rs.getInt("fid_medico"));
                    m.setNombres(rs.getString("m_nombres"));
                    m.setApellidoPaterno(rs.getString("m_ap"));
                    cita.setMedicoAsignado(m);
                }
            }
        } catch (SQLException e) { System.err.println("Error load: " + e.getMessage()); }
        return cita;
    }

    @Override
    public List<CitaMedica> listALL() {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT id_cita_medica FROM CitaMedica WHERE activo = 1 ORDER BY fecha DESC";
        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) { }
        return lista;
    }

    @Override
    public List<CitaMedica> listarPorFidMedico(int fid_medico) {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT id_cita_medica FROM CitaMedica WHERE fid_medico = ? AND activo = 1";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_medico);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) { }
        return lista;
    }

    @Override
    public List<CitaMedica> listarPorFidPaciente(int fid_paciente) {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT id_cita_medica FROM CitaMedica WHERE fid_paciente = ? AND activo = 1";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_paciente);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) { }
        return lista;
    }

    @Override
    public List<CitaMedica> listarPorFidMedicoYFechas(int fid_medico, LocalDate fechaInicio, LocalDate fechaFin) {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT id_cita_medica FROM CitaMedica WHERE fid_medico = ? AND fecha BETWEEN ? AND ? AND activo = 1";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_medico);
            pst.setDate(2, java.sql.Date.valueOf(fechaInicio));
            pst.setDate(3, java.sql.Date.valueOf(fechaFin));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por médico y fechas: " + e.getMessage());
        }
        return lista;
    }

	
	
	/*
	
	Cuidado con los Enums al guardar en Base de Datos
	En tu CitaMedicaDAOImpl, guardas el estado de la cita (fid_estado_cita). 
	Recuerda que en Java, EstadoCita es un Enum (PROGRAMADA, CANCELADA, etc.).
	Dependiendo de cómo hiciste tu base de datos, tienes dos opciones correctas para enviarlo 
	en el PreparedStatement:
	
	Si en tu MySQL el estado es un VARCHAR (Texto):
	pst.setString(8, objeto.getEstado().name()); // Guarda "PROGRAMADA"
	
	Si en tu MySQL el estado es un INT (Foreign Key a una tabla de estados):
	// ordinal() empieza en 0, le sumamos 1 para que coincida con el ID de la base de datos (1, 2, 3...)
	pst.setInt(8, objeto.getEstado().ordinal() + 1);
	
	
	*/
	
}
