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

    private Connection con;

    public DetalleHistorialDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(DetalleHistorial objeto) {
        int idGenerado = 0;
        // Según tu SQL: id_historial_medico, id_cita_medica, esta_cerrada, fecha_cierre, nota_aclaratoria
        String sql = "INSERT INTO DetalleHistorial (fid_historial_medico, fid_cita_medica, esta_cerrada, " +
                "fecha_cierre, nota_aclaratoria) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, objeto.getHistorialMedico().getIdHistorial());
            pst.setInt(2, objeto.getCitaOrigen().getIdCitaMedica());
            pst.setBoolean(3, objeto.isEstaCerrada());
            // --- PROTECCIÓN CONTRA NULLPOINTEREXCEPTION ---
            if (objeto.getFechaCierre() != null) {
                pst.setTimestamp(4, Timestamp.valueOf(objeto.getFechaCierre()));
            } else {
                pst.setNull(4, Types.TIMESTAMP); // Caso: Atención recién abierta
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
        // El update es vital para cuando el médico "Cierra" la atención
        String sql = "UPDATE DetalleHistorial SET esta_cerrada = ?, fecha_cierre = ?, " +
                "nota_aclaratoria = ? WHERE id_detalle = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setBoolean(1, objeto.isEstaCerrada());
            pst.setObject(2, objeto.getFechaCierre());
            pst.setString(3, objeto.getNotaAclaratoria());
            pst.setInt(4, objeto.getIdDetalle());

            filas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar DetalleHistorial: " + e.getMessage());
        }
        return filas;
    }

    //BLOQUEADO POR LEY MEDICA
    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public DetalleHistorial load(int id) {
        DetalleHistorial detalle = null;
        String sql = "SELECT * FROM DetalleHistorial WHERE id_detalle = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
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

                    // Solo cargamos IDs de las relaciones, la carga completa de objetos (Triaje, Anamnesis, etc.)
                    // se hace mediante sus propios DAOs en la capa de negocio (Service).
                    HistorialMedico h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial_medico"));
                    detalle.setHistorialMedico(h);

                    CitaMedica c = new CitaMedica();
                    c.setIdCitaMedica(rs.getInt("id_cita_medica"));
                    detalle.setCitaOrigen(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar DetalleHistorial: " + e.getMessage());
        }
        return detalle;
    }

    //POR AHORA SOLO LISTAREMOS UNO POR UNO POR SU ID
    @Override
    public List<DetalleHistorial> listALL() {
        return List.of();
    }


    @Override
    public List<DetalleHistorial> listarPorHistorial(int idHistorialMedico) {
        List<DetalleHistorial> lista = new ArrayList<>();
        String sql = "SELECT id_detalle FROM DetalleHistorial WHERE id_historial_medico = ? ORDER BY id_detalle DESC";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idHistorialMedico);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) { }
        return lista;
    }
}
