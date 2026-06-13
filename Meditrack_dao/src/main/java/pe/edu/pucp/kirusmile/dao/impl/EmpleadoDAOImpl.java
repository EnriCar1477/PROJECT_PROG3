package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.EmpleadoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Empleado;
import pe.edu.pucp.kirusmile.models.RolUsuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    // Eliminamos la Connection global y el constructor vacío

    @Override
    public int save(Empleado objeto) {
        int resultado = 0;
        String sqlPersona = "INSERT INTO Persona (dni, nombres, apellido_paterno, apellido_materno, " +
                "fecha_nacimiento, telefono, correo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        // CORRECCIÓN: Insertamos en fid_persona, no en id_empleado
        String sqlEmpleado = "INSERT INTO Empleado (fid_persona, codigo_empleado, fecha_vinculacion, " +
                "estado_laboral, username, password_hash, fid_rol_usuario, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        Connection conTransaccion = null;
        try {
            conTransaccion = DBManager.getInstance().getConnection();
            conTransaccion.setAutoCommit(false); // Iniciamos transacción

            // 1. Insertamos en el Padre (Persona)
            try (PreparedStatement pstP = conTransaccion.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());

                if (objeto.getFechaNacimiento() != null) {
                    pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                } else {
                    pstP.setNull(5, java.sql.Types.DATE);
                }

                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.executeUpdate();

                try (ResultSet rs = pstP.getGeneratedKeys()) {
                    if (rs.next()) {
                        objeto.setIdPersona(rs.getInt(1));
                    }
                }
            }

            // 2. Insertamos en el Hijo (Empleado) usando el ID de Persona recuperado
            try (PreparedStatement pstE = conTransaccion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS)) {
                pstE.setInt(1, objeto.getIdPersona()); // fid_persona
                pstE.setString(2, objeto.getCodigoEmpleado());

                if (objeto.getFechaVinculacion() != null) {
                    pstE.setDate(3, java.sql.Date.valueOf(objeto.getFechaVinculacion()));
                } else {
                    pstE.setNull(3, java.sql.Types.DATE);
                }

                pstE.setBoolean(4, objeto.isEstadoLaboral());
                pstE.setString(5, objeto.getUsername());
                pstE.setString(6, objeto.getPasswordHash());
                pstE.setInt(7, objeto.getRol().ordinal() + 1);

                int filasAfec = pstE.executeUpdate();
                if (filasAfec > 0) {
                    try (ResultSet rs = pstE.getGeneratedKeys()) {
                        if (rs.next()) {
                            resultado = rs.getInt(1);
                            objeto.setIdEmpleado(resultado); // Recuperamos el ID autoincremental de Empleado
                        }
                    }
                }
            }

            conTransaccion.commit();
        } catch (SQLException e) {
            if (conTransaccion != null) {
                try { conTransaccion.rollback(); } catch (SQLException ex) { }
            }
            System.err.println("Error en save cascada Empleado: " + e.getMessage());
        } finally {
            try { if (conTransaccion != null) conTransaccion.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int update(Empleado objeto) {
        int resultado = 0;
        String sqlPersona = "UPDATE Persona SET dni=?, nombres=?, apellido_paterno=?, " +
                "apellido_materno=?, fecha_nacimiento=?, telefono=?, correo=? WHERE id_persona=?";
        String sqlEmpleado = "UPDATE Empleado SET codigo_empleado=?, fecha_vinculacion=?, " +
                "estado_laboral=?, username=?, password_hash=?, fid_rol_usuario=? WHERE id_empleado=?";

        Connection conTransaccion = null;
        try {
            conTransaccion = DBManager.getInstance().getConnection();
            conTransaccion.setAutoCommit(false);

            try (PreparedStatement pstP = conTransaccion.prepareStatement(sqlPersona)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());
                if (objeto.getFechaNacimiento() != null) pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                else pstP.setNull(5, java.sql.Types.DATE);
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.setInt(8, objeto.getIdPersona());
                pstP.executeUpdate();
            }

            try (PreparedStatement pstE = conTransaccion.prepareStatement(sqlEmpleado)) {
                pstE.setString(1, objeto.getCodigoEmpleado());
                if (objeto.getFechaVinculacion() != null) pstE.setDate(2, java.sql.Date.valueOf(objeto.getFechaVinculacion()));
                else pstE.setNull(2, java.sql.Types.DATE);
                pstE.setBoolean(3, objeto.isEstadoLaboral());
                pstE.setString(4, objeto.getUsername());
                pstE.setString(5, objeto.getPasswordHash());
                pstE.setInt(6, objeto.getRol().ordinal() + 1);
                pstE.setInt(7, objeto.getIdEmpleado());
                resultado = pstE.executeUpdate();
            }

            conTransaccion.commit();
        } catch (SQLException e) {
            if (conTransaccion != null) try { conTransaccion.rollback(); } catch (SQLException ex) { }
            System.err.println("Error en update cascada de Empleado: " + e.getMessage());
        } finally {
            try { if (conTransaccion != null) conTransaccion.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        int filasAfectadas = 0;
        String sql = "UPDATE Empleado SET activo = 0 WHERE id_empleado = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error en borrado lógico de Empleado: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public Empleado load(int id) {
        Empleado empleado = null;
        // CORRECCIÓN: ON e.fid_persona = p.id_persona
        String sql = "SELECT p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, " +
                "p.fecha_nacimiento, p.telefono, p.correo, e.id_empleado, e.codigo_empleado, e.fecha_vinculacion, " +
                "e.estado_laboral, e.username, e.password_hash, e.fid_rol_usuario, e.ultimo_acceso " +
                "FROM Empleado e INNER JOIN Persona p ON e.fid_persona = p.id_persona " +
                "WHERE e.id_empleado = ? AND e.activo = 1 AND p.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    empleado = mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) { System.err.println("Error load Empleado: " + e.getMessage()); }
        return empleado;
    }

    @Override
    public List<Empleado> listALL() {
        List<Empleado> lista = new ArrayList<>();
        // Optimizado con INNER JOIN para evitar múltiples consultas a BD
        String sql = "SELECT p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, " +
                "p.fecha_nacimiento, p.telefono, p.correo, e.id_empleado, e.codigo_empleado, e.fecha_vinculacion, " +
                "e.estado_laboral, e.username, e.password_hash, e.fid_rol_usuario, e.ultimo_acceso " +
                "FROM Empleado e INNER JOIN Persona p ON e.fid_persona = p.id_persona " +
                "WHERE e.activo = 1 AND p.activo = 1 ORDER BY p.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listALL Empleado: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Empleado obtenerPorUsername(String username) {
        Empleado empleado = null;
        String sql = "SELECT p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, " +
                "p.fecha_nacimiento, p.telefono, p.correo, e.id_empleado, e.codigo_empleado, e.fecha_vinculacion, " +
                "e.estado_laboral, e.username, e.password_hash, e.fid_rol_usuario, e.ultimo_acceso " +
                "FROM Empleado e INNER JOIN Persona p ON e.fid_persona = p.id_persona " +
                "WHERE e.username = ? AND e.activo = 1 AND p.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) empleado = mapearEmpleado(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error obtenerPorUsername: " + e.getMessage());
        }
        return empleado;
    }

    // --- MÉTODO AUXILIAR PARA LIMPIAR EL CÓDIGO ---
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();

        // Datos de Persona (Padre)
        empleado.setIdPersona(rs.getInt("id_persona"));
        empleado.setDni(rs.getString("dni"));
        empleado.setNombres(rs.getString("nombres"));
        empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
        empleado.setApellidoMaterno(rs.getString("apellido_materno"));
        if (rs.getDate("fecha_nacimiento") != null) {
            empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        }
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setCorreo(rs.getString("correo"));

        // Datos de Empleado (Hijo)
        empleado.setIdEmpleado(rs.getInt("id_empleado"));
        empleado.setCodigoEmpleado(rs.getString("codigo_empleado"));
        if (rs.getDate("fecha_vinculacion") != null) {
            empleado.setFechaVinculacion(rs.getDate("fecha_vinculacion").toLocalDate());
        }
        empleado.setEstadoLaboral(rs.getBoolean("estado_laboral"));
        empleado.setUsername(rs.getString("username"));
        empleado.setPasswordHash(rs.getString("password_hash"));

        if (rs.getTimestamp("ultimo_acceso") != null) {
            empleado.setUltimoAcceso(rs.getTimestamp("ultimo_acceso").toLocalDateTime());
        }

        // SEGURIDAD ENUM: Evitamos IndexOutOfBoundsException
        int idRol = rs.getInt("fid_rol_usuario");
        if (idRol >= 1 && idRol <= RolUsuario.values().length) {
            empleado.setRol(RolUsuario.values()[idRol - 1]);
        }

        empleado.setActivo(true);
        return empleado;
    }
    @Override
    public void registrarUltimoAcceso(int idEmpleado, LocalDateTime fecha) {
        String sql = "UPDATE Empleado SET ultimo_acceso = ? WHERE id_empleado = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(fecha));
            pst.setInt(2, idEmpleado);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar acceso: " + e.getMessage());
        }
    }

    @Override
    public Empleado obtenerPorDni(String dni) {
        Empleado empleado = null;
        // Hacemos el INNER JOIN buscando específicamente por la columna DNI de la tabla Persona
        String sql = "SELECT p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, " +
                "p.fecha_nacimiento, p.telefono, p.correo, e.id_empleado, e.codigo_empleado, e.fecha_vinculacion, " +
                "e.estado_laboral, e.username, e.password_hash, e.fid_rol_usuario, e.ultimo_acceso " +
                "FROM Empleado e INNER JOIN Persona p ON e.fid_persona = p.id_persona " +
                "WHERE p.dni = ? AND e.activo = 1 AND p.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, dni);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Reutilizamos nuestro método auxiliar para ensamblar todo automáticamente
                    empleado = mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorDni de Empleado: " + e.getMessage());
        }
        return empleado;
    }

}