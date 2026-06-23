package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.MedicoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Especialidad;
import pe.edu.pucp.kirusmile.models.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAOImpl implements MedicoDAO {

    public MedicoDAOImpl() {
        // CORRECCIÓN: Constructor vacío, eliminamos la conexión global
    }

    @Override
    public int save(pe.edu.pucp.kirusmile.models.Medico objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO Medico (fid_empleado, cmp, rne, fid_especialidad, fecha_ingreso, firma_digital) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // CORRECCIÓN: Conexión local auto-cerrable
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getIdEmpleado());
            pst.setString(2, objeto.getCmp());

            if (objeto.getRne() != null) {
                pst.setString(3, objeto.getRne());
            } else {
                pst.setNull(3, Types.VARCHAR);
            }

            pst.setInt(4, objeto.getEspecialidad().getIdEspecialidad());

            if (objeto.getFechaIngreso() != null) {
                pst.setDate(5, java.sql.Date.valueOf(objeto.getFechaIngreso()));
            } else {
                pst.setNull(5, java.sql.Types.DATE);
            }

            // --- CORRECCIÓN DE LA FIRMA DIGITAL (byte[]) ---
            if (objeto.getFirmaDigital() != null) {
                pst.setBytes(6, objeto.getFirmaDigital());
            } else {
                pst.setNull(6, Types.BLOB);
            }

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdMedico(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Medico: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(pe.edu.pucp.kirusmile.models.Medico objeto) {
        int resultado = 0;
        String sql = "UPDATE Medico SET cmp = ?, rne = ?, fid_especialidad = ?, " +
                "fecha_ingreso = ?, firma_digital = ? WHERE id_medico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, objeto.getCmp());

            if (objeto.getRne() != null) {
                pst.setString(2, objeto.getRne());
            } else {
                pst.setNull(2, Types.VARCHAR);
            }

            pst.setInt(3, objeto.getEspecialidad().getIdEspecialidad());

            if (objeto.getFechaIngreso() != null) {
                pst.setDate(4, java.sql.Date.valueOf(objeto.getFechaIngreso()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            // --- CORRECCIÓN DE LA FIRMA DIGITAL (setBytes en plural) ---
            if (objeto.getFirmaDigital() != null) {
                pst.setBytes(5, objeto.getFirmaDigital());
            } else {
                pst.setNull(5, Types.BLOB);
            }

            pst.setInt(6, objeto.getIdMedico());

            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Medico: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        return 0; // Usualmente el borrado lógico se hace en la tabla Padre (Empleado)
    }

    @Override
    public pe.edu.pucp.kirusmile.models.Medico load(int id) {
        pe.edu.pucp.kirusmile.models.Medico medico = null;

        String sql = "SELECT m.id_medico, m.fid_empleado, m.cmp, m.rne, m.fecha_ingreso, m.firma_digital, " +
                "e.id_especialidad, e.nombre_especialidad, e.costo_especialidad, " +
                "emp.codigo_empleado, emp.username, emp.password_hash, emp.estado_laboral, " +
                "emp.fecha_vinculacion, emp.fid_rol_usuario, emp.activo as emp_activo, " +
                "p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, p.telefono, p.correo " +
                "FROM Medico m " +
                "INNER JOIN Especialidad e ON m.fid_especialidad = e.id_especialidad " +
                "INNER JOIN Empleado emp ON m.fid_empleado = emp.id_empleado " +
                "INNER JOIN Persona p ON emp.fid_persona = p.id_persona " +
                "WHERE m.id_medico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    medico = new pe.edu.pucp.kirusmile.models.Medico();
                    medico.setIdMedico(rs.getInt("id_medico"));
                    medico.setIdEmpleado(rs.getInt("fid_empleado"));
                    medico.setCmp(rs.getString("cmp"));
                    medico.setRne(rs.getString("rne"));

                    if (rs.getDate("fecha_ingreso") != null) {
                        medico.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
                    }

                    // --- CORRECCIÓN DE LA FIRMA DIGITAL (getBytes) ---
                    medico.setFirmaDigital(rs.getBytes("firma_digital"));

                    Especialidad esp = new Especialidad();
                    esp.setIdEspecialidad(rs.getInt("id_especialidad"));
                    esp.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                    esp.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                    medico.setEspecialidad(esp);

                    // --- Datos del Empleado ---
                    medico.setCodigoEmpleado(rs.getString("codigo_empleado"));
                    medico.setUsername(rs.getString("username"));
                    medico.setPasswordHash(rs.getString("password_hash"));
                    medico.setEstadoLaboral(rs.getBoolean("estado_laboral"));
                    medico.setActivo(rs.getBoolean("emp_activo"));
                    if (rs.getDate("fecha_vinculacion") != null) {
                        medico.setFechaVinculacion(rs.getDate("fecha_vinculacion").toLocalDate());
                    }
                    int idRol = rs.getInt("fid_rol_usuario");
                    if (idRol >= 1 && idRol <= pe.edu.pucp.kirusmile.models.RolUsuario.values().length) {
                        medico.setRol(pe.edu.pucp.kirusmile.models.RolUsuario.values()[idRol - 1]);
                    }

                    // --- Datos de Persona ---
                    pe.edu.pucp.kirusmile.models.Persona persona = new pe.edu.pucp.kirusmile.models.Persona();
                    persona.setIdPersona(rs.getInt("id_persona"));
                    persona.setDni(rs.getString("dni"));
                    persona.setNombres(rs.getString("nombres"));
                    persona.setApellidoPaterno(rs.getString("apellido_paterno"));
                    persona.setApellidoMaterno(rs.getString("apellido_materno"));
                    persona.setTelefono(rs.getString("telefono"));
                    persona.setCorreo(rs.getString("correo"));

                    medico.setPersona(persona);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Medico Completo: " + e.getMessage());
        }
        return medico;
    }

    @Override
    public List<pe.edu.pucp.kirusmile.models.Medico> listALL() {
        List<pe.edu.pucp.kirusmile.models.Medico> lista = new ArrayList<>();
        String sql = "SELECT id_medico FROM Medico";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
              ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Medicos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<pe.edu.pucp.kirusmile.models.Medico> listarMedicosDatosBasicos() {
        List<pe.edu.pucp.kirusmile.models.Medico> lista = new ArrayList<>();
        String sql = "SELECT m.id_medico, m.fid_empleado, m.cmp, m.rne, m.activo as medico_activo, " +
                "e.id_especialidad, e.nombre_especialidad, e.costo_especialidad, " +
                "p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, p.telefono, p.correo, " +
                "emp.username, emp.activo as emp_activo " +
                "FROM Medico m " +
                "INNER JOIN Especialidad e ON m.fid_especialidad = e.id_especialidad " +
                "INNER JOIN Empleado emp ON m.fid_empleado = emp.id_empleado " +
                "INNER JOIN Persona p ON emp.fid_persona = p.id_persona";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                pe.edu.pucp.kirusmile.models.Medico medico = new pe.edu.pucp.kirusmile.models.Medico();
                medico.setIdMedico(rs.getInt("id_medico"));
                medico.setIdEmpleado(rs.getInt("fid_empleado"));
                medico.setCmp(rs.getString("cmp"));
                medico.setRne(rs.getString("rne"));
                medico.setActivo(rs.getBoolean("medico_activo"));

                // Dejamos la firma digital, fechas y campos pesados nulos para eficiencia
                medico.setFirmaDigital(null);
                medico.setFechaIngreso(null);

                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(rs.getInt("id_especialidad"));
                esp.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                esp.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                medico.setEspecialidad(esp);

                pe.edu.pucp.kirusmile.models.Persona persona = new pe.edu.pucp.kirusmile.models.Persona();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setDni(rs.getString("dni"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidoPaterno(rs.getString("apellido_paterno"));
                persona.setApellidoMaterno(rs.getString("apellido_materno"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));
                medico.setPersona(persona);

                medico.setUsername(rs.getString("username"));
                medico.setActivo(rs.getBoolean("emp_activo"));

                lista.add(medico);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Medicos Datos Basicos: " + e.getMessage());
        }
        return lista;
    }


    @Override
    public pe.edu.pucp.kirusmile.models.Medico obtenerPorCMP(String cmp) {
        String sql = "SELECT id_medico FROM Medico WHERE cmp = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, cmp);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médico por CMP: " + e.getMessage());
        }
        return null;
    }

    @Override
    public pe.edu.pucp.kirusmile.models.Medico obtenerPorFidEmpleado(int fid_empleado) {
        String sql = "SELECT id_medico FROM Medico WHERE fid_empleado = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_empleado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médico por empleado: " + e.getMessage());
        }
        return null;
    }
}
