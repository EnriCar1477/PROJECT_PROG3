package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.EmpleadoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Empleado;
import pe.edu.pucp.kirusmile.models.RolUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private Connection con;

    public EmpleadoDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Empleado objeto) {
        int resultado = 0;
        String sqlPersona = "INSERT INTO Persona (dni, nombres, apellido_paterno, apellido_materno, " +
                "fecha_nacimiento, telefono, correo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";

        String sqlEmpleado = "INSERT INTO Empleado (id_empleado, codigo_empleado, fecha_vinculacion, " +
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

                // --- CORRECCIÓN FECHA DE NACIMIENTO ---
                if (objeto.getFechaNacimiento() != null) {
                    pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                } else {
                    pstP.setNull(5, java.sql.Types.DATE);
                }

                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.executeUpdate();

                // Recuperamos ID generado
                try (ResultSet rs = pstP.getGeneratedKeys()) {
                    if (rs.next()) {
                        objeto.setIdPersona(rs.getInt(1));
                        objeto.setIdEmpleado(objeto.getIdPersona());
                    }
                }
            }

            // 2. Insertamos en el Hijo (Empleado)
            try (PreparedStatement pstE = conTransaccion.prepareStatement(sqlEmpleado)) {
                pstE.setInt(1, objeto.getIdEmpleado());
                pstE.setString(2, objeto.getCodigoEmpleado());

                // --- CORRECCIÓN FECHA DE VINCULACIÓN ---
                if (objeto.getFechaVinculacion() != null) {
                    pstE.setDate(3, java.sql.Date.valueOf(objeto.getFechaVinculacion()));
                } else {
                    pstE.setNull(3, java.sql.Types.DATE);
                }

                pstE.setBoolean(4, objeto.isEstadoLaboral());
                pstE.setString(5, objeto.getUsername());
                pstE.setString(6, objeto.getPasswordHash());
                // Enum Rol (ID 1=MEDICO, 2=SECRETARIO...)
                pstE.setInt(7, objeto.getRol().ordinal() + 1);

                resultado = pstE.executeUpdate();
            }

            conTransaccion.commit(); // Confirmamos transacción
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
                if (objeto.getFechaNacimiento() != null) {
                    pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                } else {
                    pstP.setNull(5, java.sql.Types.DATE);
                }
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.setInt(8, objeto.getIdPersona());
                pstP.executeUpdate();
            }

            try (PreparedStatement pstE = conTransaccion.prepareStatement(sqlEmpleado)) {
                pstE.setString(1, objeto.getCodigoEmpleado());
                if (objeto.getFechaVinculacion() != null) {
                    pstE.setDate(2, java.sql.Date.valueOf(objeto.getFechaVinculacion()));
                } else {
                    pstE.setNull(2, java.sql.Types.DATE);
                }
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
        try (PreparedStatement pst = con.prepareStatement(sql)) {
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
        String sql = "SELECT p.id_persona, p.dni, p.nombres, p.apellido_paterno, p.apellido_materno, " +
                "p.fecha_nacimiento, p.telefono, p.correo, e.codigo_empleado, e.fecha_vinculacion, " +
                "e.estado_laboral, e.username, e.password_hash, e.fid_rol_usuario, e.ultimo_acceso " +
                "FROM Empleado e INNER JOIN Persona p ON e.id_empleado = p.id_persona " +
                "WHERE e.id_empleado = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setIdPersona(rs.getInt("id_persona"));
                    empleado.setIdEmpleado(rs.getInt("id_persona"));
                    empleado.setDni(rs.getString("dni"));
                    empleado.setNombres(rs.getString("nombres"));
                    empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
                    empleado.setApellidoMaterno(rs.getString("apellido_materno"));

                    // --- CORRECCIONES EN LA RECUPERACIÓN (LOAD) ---
                    if (rs.getDate("fecha_nacimiento") != null) {
                        empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    }
                    if (rs.getDate("fecha_vinculacion") != null) {
                        empleado.setFechaVinculacion(rs.getDate("fecha_vinculacion").toLocalDate());
                    }

                    empleado.setTelefono(rs.getString("telefono"));
                    empleado.setCorreo(rs.getString("correo"));
                    empleado.setCodigoEmpleado(rs.getString("codigo_empleado"));
                    empleado.setEstadoLaboral(rs.getBoolean("estado_laboral"));
                    empleado.setUsername(rs.getString("username"));
                    empleado.setPasswordHash(rs.getString("password_hash"));

                    if (rs.getTimestamp("ultimo_acceso") != null) {
                        empleado.setUltimoAcceso(rs.getTimestamp("ultimo_acceso").toLocalDateTime());
                    }
                    empleado.setRol(RolUsuario.values()[rs.getInt("fid_rol_usuario") - 1]);
                }
            }
        } catch (SQLException e) { System.err.println("Error load Empleado: " + e.getMessage()); }
        return empleado;
    }

    @Override
    public List<Empleado> listALL() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT id_empleado FROM Empleado WHERE activo = 1";
        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) lista.add(this.load(rs.getInt(1)));
        } catch (SQLException e) { }
        return lista;
    }

    @Override
    public Empleado obtenerPorUsername(String username) {
        String sql = "SELECT id_empleado FROM Empleado WHERE username = ? AND activo = 1";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) { }
        return null;
    }
}
