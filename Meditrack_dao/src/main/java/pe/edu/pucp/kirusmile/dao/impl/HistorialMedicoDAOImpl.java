package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.HistorialMedicoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.HistorialMedico;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAOImpl implements HistorialMedicoDAO {

    private Connection con;

    public HistorialMedicoDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(HistorialMedico objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO HistorialMedico (fid_paciente, fecha_creacion, estado_fisico) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, objeto.getPaciente().getIdPaciente());

            // --- CORRECCIÓN: EVITAMOS setObject Y USAMOS Timestamp SEGURO ---
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

        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
        return 0;
    }

    @Override
    public HistorialMedico load(int id) {

        HistorialMedico h = null;
        String sql = "SELECT * FROM HistorialMedico WHERE id_historial_medico = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    h = new HistorialMedico();
                    h.setIdHistorial(rs.getInt("id_historial_medico"));

                    if (rs.getTimestamp("fecha_creacion") != null) {
                        h.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                    }

                    h.setEstadoFisico(rs.getString("estado_fisico"));

                    // Solo ensamblamos el ID del paciente, luego el BL llama a PacienteDAO si necesita el resto
                    Paciente p = new Paciente();
                    p.setIdPaciente(rs.getInt("fid_paciente"));
                    h.setPaciente(p);
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
        String sql = "SELECT id_historial_medico FROM HistorialMedico ORDER BY fecha_creacion DESC";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Historiales: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public HistorialMedico obtenerPorIdPaciente(int idPaciente) {
        String sql = "SELECT id_historial_medico FROM HistorialMedico WHERE fid_paciente = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPaciente);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return load(rs.getInt(1)); // Reutilizamos el load para traer el objeto
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorIdPaciente: " + e.getMessage());
        }
        return null;
    }
}
