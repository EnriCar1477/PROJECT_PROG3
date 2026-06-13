package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.HistorialMedicoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.models.HistorialMedico;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAOImpl implements HistorialMedicoDAO {

    public HistorialMedicoDAOImpl() {
        // Ya no guardamos la conexión global para evitar fugas de memoria
    }

    @Override
    public int save(HistorialMedico objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO HistorialMedico (fid_paciente, fecha_creacion, estado_fisico, activo) VALUES (?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, objeto.getPaciente().getIdPaciente());

            if (objeto.getFechaCreacion() != null) {
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(objeto.getFechaCreacion()));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }

            ps.setString(3, objeto.getEstadoFisico());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdHistorial(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar HistorialMedico: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(HistorialMedico objeto) {
        int resultado = 0;
        String sql = "UPDATE HistorialMedico SET estado_fisico = ? WHERE id_historial_medico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, objeto.getEstadoFisico());
            ps.setInt(2, objeto.getIdHistorial());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar HistorialMedico: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico
        String sql = "UPDATE HistorialMedico SET activo = 0 WHERE id_historial_medico = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar HistorialMedico: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public HistorialMedico load(int id) {
        HistorialMedico h = null;

        // 1. OBTENER EL HISTORIAL Y SU PACIENTE (INNER JOIN)
        String sqlHistorial = "SELECT hm.*, p.*, per.* " +
                "FROM HistorialMedico hm " +
                "INNER JOIN Paciente p ON hm.fid_paciente = p.id_paciente " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE hm.id_historial_medico = ? AND hm.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlHistorial)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial_medico"));
                    h.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("fecha_creacion") != null) {
                        h.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                    }
                    h.setEstadoFisico(rs.getString("estado_fisico"));

                    // Ensamblaje completo del Paciente requerido por Blazor
                    Paciente pac = new Paciente();
                    pac.setIdPaciente(rs.getInt("id_paciente"));
                    pac.setDni(rs.getString("dni"));
                    pac.setNombres(rs.getString("nombres"));
                    pac.setApellidoPaterno(rs.getString("apellido_paterno"));
                    pac.setApellidoMaterno(rs.getString("apellido_materno"));
                    if(rs.getDate("fecha_nacimiento") != null) {
                        pac.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    }
                    pac.setTelefono(rs.getString("telefono"));
                    pac.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
                    pac.setFactorRh(rs.getString("factor_rh"));
                    h.setPaciente(pac);
                }
            }

            // 2. OBTENER LA LISTA DE DETALLES DEL HISTORIAL (Atenciones Previas)
            if (h != null) {
                String sqlDetalles = "SELECT * FROM DetalleHistorial WHERE fid_historial_medico = ? AND activo = 1 ORDER BY fecha_cierre DESC";
                try (PreparedStatement psDetalles = con.prepareStatement(sqlDetalles)) {
                    psDetalles.setInt(1, h.getIdHistorial());
                    try (ResultSet rsDetalles = psDetalles.executeQuery()) {
                        while (rsDetalles.next()) {
                            DetalleHistorial detalle = new DetalleHistorial();
                            detalle.setIdDetalle(rsDetalles.getInt("id_detalle"));
                            detalle.setEstaCerrada(rsDetalles.getBoolean("esta_cerrada"));

                            if (rsDetalles.getTimestamp("fecha_cierre") != null) {
                                detalle.setFechaCierre(rsDetalles.getTimestamp("fecha_cierre").toLocalDateTime());
                            }
                            detalle.setNotaAclaratoria(rsDetalles.getString("nota_aclaratoria"));
                            detalle.setActivo(rsDetalles.getBoolean("activo"));

                            // Agregamos el detalle a la lista del historial
                            h.getListaDetalles().add(detalle);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar HistorialMedico: " + e.getMessage());
        }
        return h;
    }

    @Override
    public List<HistorialMedico> listALL() {
        List<HistorialMedico> lista = new ArrayList<>();
        String sql = "SELECT id_historial_medico FROM HistorialMedico WHERE activo = 1 ORDER BY fecha_creacion DESC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(this.load(rs.getInt(1))); // Reutiliza el load para traer todo ensamblado
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Historiales: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public HistorialMedico obtenerPorIdPaciente(int idPaciente) {
        String sql = "SELECT id_historial_medico FROM HistorialMedico WHERE fid_paciente = ? AND activo = 1";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPaciente);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return load(rs.getInt(1)); // Trae el historial ensamblado con sus detalles
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorIdPaciente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<HistorialMedico> listarPorDniOApellido(String filtro) {
        List<HistorialMedico> lista = new ArrayList<>();

        // Hacemos INNER JOIN para poder filtrar usando las columnas de la tabla Persona
        String sql = "SELECT hm.id_historial_medico " +
                "FROM HistorialMedico hm " +
                "INNER JOIN Paciente p ON hm.fid_paciente = p.id_paciente " +
                "INNER JOIN Persona per ON p.fid_persona = per.id_persona " +
                "WHERE hm.activo = 1 AND p.activo = 1 " +
                "AND (per.dni LIKE ? OR per.apellido_paterno LIKE ? OR per.apellido_materno LIKE ?) " +
                "ORDER BY per.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Preparamos el comodín '%' para que busque coincidencias parciales
            String busqueda = "%" + filtro.trim() + "%";
            pst.setString(1, busqueda);
            pst.setString(2, busqueda);
            pst.setString(3, busqueda);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Magia pura: Le pasamos el ID encontrado al load() para que lo ensamble completo
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en listarPorDniOApellido: " + e.getMessage());
        }
        return lista;
    }


}
