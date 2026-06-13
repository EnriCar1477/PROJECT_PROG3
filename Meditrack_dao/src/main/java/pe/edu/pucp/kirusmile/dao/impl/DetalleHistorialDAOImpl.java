package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.DetalleHistorialDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.CitaMedica;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.models.HistorialMedico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleHistorialDAOImpl implements DetalleHistorialDAO {

    public DetalleHistorialDAOImpl() {
        // Constructor vacío, la conexión se obtiene localmente en cada método
    }

    @Override
    public int save(DetalleHistorial objeto) {
        int idGenerado = 0;
        // Agregamos el campo 'activo'
        String sql = "INSERT INTO DetalleHistorial (fid_historial_medico, fid_cita_medica, esta_cerrada, " +
                "fecha_cierre, nota_aclaratoria, activo) VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getHistorialMedico().getIdHistorial());
            pst.setInt(2, objeto.getCitaOrigen().getIdCitaMedica());
            pst.setBoolean(3, objeto.isEstaCerrada());

            // Protección contra NullPointerException
            if (objeto.getFechaCierre() != null) {
                pst.setTimestamp(4, Timestamp.valueOf(objeto.getFechaCierre()));
            } else {
                pst.setNull(4, Types.TIMESTAMP);
            }

            if (objeto.getNotaAclaratoria() != null) {
                pst.setString(5, objeto.getNotaAclaratoria());
            } else {
                pst.setNull(5, Types.VARCHAR);
            }

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdDetalle(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar DetalleHistorial: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(DetalleHistorial objeto) {
        int filas = 0;
        String sql = "UPDATE DetalleHistorial SET esta_cerrada = ?, fecha_cierre = ?, " +
                "nota_aclaratoria = ? WHERE id_detalle = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setBoolean(1, objeto.isEstaCerrada());

            // CORRECCIÓN: Manejo seguro del LocalDateTime al igual que en el Save
            if (objeto.getFechaCierre() != null) {
                pst.setTimestamp(2, Timestamp.valueOf(objeto.getFechaCierre()));
            } else {
                pst.setNull(2, Types.TIMESTAMP);
            }

            pst.setString(3, objeto.getNotaAclaratoria());
            pst.setInt(4, objeto.getIdDetalle());

            filas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar DetalleHistorial: " + e.getMessage());
        }
        return filas;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico por si el médico registró una atención por accidente
        String sql = "UPDATE DetalleHistorial SET activo = 0 WHERE id_detalle = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al anular DetalleHistorial: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public DetalleHistorial load(int id) {
        DetalleHistorial detalle = null;
        // CORRECCIÓN: Añadido filtro activo = 1
        String sql = "SELECT * FROM DetalleHistorial WHERE id_detalle = ? AND activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt("id_detalle"));
                    detalle.setEstaCerrada(rs.getBoolean("esta_cerrada"));

                    if (rs.getTimestamp("fecha_cierre") != null) {
                        detalle.setFechaCierre(rs.getTimestamp("fecha_cierre").toLocalDateTime());
                    }

                    detalle.setNotaAclaratoria(rs.getString("nota_aclaratoria"));
                    detalle.setActivo(rs.getBoolean("activo")); // Seteamos el atributo nuevo

                    // Ensamblaje de foráneas
                    HistorialMedico h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("fid_historial_medico")); // Corrección de nombre de columna
                    detalle.setHistorialMedico(h);

                    CitaMedica c = new CitaMedica();
                    c.setIdCitaMedica(rs.getInt("fid_cita_medica")); // Corrección de nombre de columna
                    detalle.setCitaOrigen(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar DetalleHistorial: " + e.getMessage());
        }
        return detalle;
    }

    @Override
    public List<DetalleHistorial> listALL() {
        return new ArrayList<>(); // Vacío por requerimiento
    }

    @Override
    public List<DetalleHistorial> listarPorHistorial(int idHistorialMedico) {
        List<DetalleHistorial> lista = new ArrayList<>();
        // CORRECCIÓN: Filtramos solo los activos
        String sql = "SELECT id_detalle FROM DetalleHistorial WHERE fid_historial_medico = ? AND activo = 1 ORDER BY id_detalle DESC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idHistorialMedico); // fid_historial_medico en lugar de id_historial_medico
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Detalles: " + e.getMessage());
        }
        return lista;
    }
}