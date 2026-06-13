package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.PacienteDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOImpl implements PacienteDAO {

    // Se eliminó el Connection global y el constructor porque ya manejas
    // las conexiones de forma local y segura en cada método (try-with-resources).

    @Override
    public int save(Paciente objeto) {
        int resultado = 0;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false); // Iniciamos transacción

            // PASO 1: Insertar en Persona
            String sqlPersona = "INSERT INTO Persona (dni, nombres, apellido_paterno, apellido_materno, " +
                    "fecha_nacimiento, telefono, correo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

            try (PreparedStatement pstP = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());
                pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.executeUpdate();

                try (ResultSet rs = pstP.getGeneratedKeys()) {
                    if (rs.next()) {
                        objeto.setIdPersona(rs.getInt(1)); // Recuperamos el ID de la Persona
                    }
                }
            }

            // PASO 2: Insertar en Paciente usando la llave recuperada
            String sqlPaciente = "INSERT INTO Paciente (fid_persona, grupo_sanguineo, factor_rh, " +
                    "grado_instruccion, ocupacion, etnia, activo) VALUES (?, ?, ?, ?, ?, ?, 1)";

            try (PreparedStatement pstH = con.prepareStatement(sqlPaciente, Statement.RETURN_GENERATED_KEYS)) {
                pstH.setInt(1, objeto.getIdPersona());
                pstH.setString(2, objeto.getGrupoSanguineo());
                pstH.setString(3, objeto.getFactorRh());
                pstH.setString(4, objeto.getGradoInstruccion());
                pstH.setString(5, objeto.getOcupacion());
                pstH.setString(6, objeto.getEtnia());

                int filasAfec = pstH.executeUpdate();
                if (filasAfec > 0) {
                    try (ResultSet rs = pstH.getGeneratedKeys()) {
                        if (rs.next()) {
                            resultado = rs.getInt(1);
                            objeto.setIdPaciente(resultado); // Seteamos el ID del Paciente también
                        }
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { }
            }
            System.err.println("Error en save cascada Paciente: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int update(Paciente objeto) {
        int resultado = 0;
        String sqlPersona = "UPDATE Persona SET dni=?, nombres=?, apellido_paterno=?, " +
                "apellido_materno=?, fecha_nacimiento=?, telefono=?, correo=? WHERE id_persona=?";
        String sqlPaciente = "UPDATE Paciente SET grupo_sanguineo=?, factor_rh=?, " +
                "grado_instruccion=?, ocupacion=?, etnia=? WHERE id_paciente=?";

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);

            try (PreparedStatement pstP = con.prepareStatement(sqlPersona)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());
                pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.setInt(8, objeto.getIdPersona());
                pstP.executeUpdate();
            }

            try (PreparedStatement pstH = con.prepareStatement(sqlPaciente)) {
                pstH.setString(1, objeto.getGrupoSanguineo());
                pstH.setString(2, objeto.getFactorRh());
                pstH.setString(3, objeto.getGradoInstruccion());
                pstH.setString(4, objeto.getOcupacion());
                pstH.setString(5, objeto.getEtnia()); // Descomentado
                pstH.setInt(6, objeto.getIdPaciente());
                resultado = pstH.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { }
            }
            System.err.println("Error en update cascada de Paciente: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        int filasAfectadas = 0;
        String sql = "UPDATE Paciente SET activo = 0 WHERE id_paciente = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error en borrado lógico de Paciente: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public Paciente load(int id) {
        Paciente paciente = null;
        // CORRECCIÓN: ON p.fid_persona = per.id_persona
        String sql = "SELECT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE p.id_paciente = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    paciente = mapearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en load Paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public List<Paciente> listALL() {
        List<Paciente> lista = new ArrayList<>();
        // CORRECCIÓN: ON p.fid_persona = per.id_persona
        String sql = "SELECT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE p.activo = 1 AND per.activo = 1 ORDER BY per.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todos los Pacientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Paciente obtenerPorDni(String dni) {
        Paciente paciente = null;
        // CORRECCIÓN: ON p.fid_persona = per.id_persona
        String sql = "SELECT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE per.dni = ? AND p.activo = 1 AND per.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, dni);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    paciente = mapearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorDni de Paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public List<Paciente> listarPorNombre(String filtro) {
        List<Paciente> lista = new ArrayList<>();
        // CORRECCIÓN: ON p.fid_persona = per.id_persona
        String sql = "SELECT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE p.activo = 1 AND per.activo = 1 " +
                "AND (per.nombres LIKE ? OR per.apellido_paterno LIKE ? OR per.apellido_materno LIKE ?) " +
                "ORDER BY per.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            String parametroBusqueda = "%" + filtro + "%";
            pst.setString(1, parametroBusqueda);
            pst.setString(2, parametroBusqueda);
            pst.setString(3, parametroBusqueda);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPaciente(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pacientes por nombre: " + e.getMessage());
        }
        return lista;
    }

    // --- NUEVO: REQUERIDO PARA LA VISTA BLAZOR ---
    public List<Paciente> listarPorFidMedico(int fidMedico) {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "INNER JOIN CitaMedica c ON c.fid_paciente = p.id_paciente " +
                "WHERE c.fid_medico = ? AND p.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fidMedico);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPaciente(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pacientes por médico: " + e.getMessage());
        }
        return lista;
    }

    // --- MÉTODO AUXILIAR PARA NO REPETIR CÓDIGO ---
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        // Datos Padre
        paciente.setIdPersona(rs.getInt("id_persona"));
        paciente.setDni(rs.getString("dni"));
        paciente.setNombres(rs.getString("nombres"));
        paciente.setApellidoPaterno(rs.getString("apellido_paterno"));
        paciente.setApellidoMaterno(rs.getString("apellido_materno"));
        if (rs.getDate("fecha_nacimiento") != null) {
            paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        }
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setCorreo(rs.getString("correo"));

        // Datos Hijo
        paciente.setIdPaciente(rs.getInt("id_paciente"));
        paciente.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
        paciente.setFactorRh(rs.getString("factor_rh"));
        paciente.setGradoInstruccion(rs.getString("grado_instruccion"));
        paciente.setOcupacion(rs.getString("ocupacion"));
        paciente.setEtnia(rs.getString("etnia"));
        paciente.setActivo(true);

        return paciente;
    }
}