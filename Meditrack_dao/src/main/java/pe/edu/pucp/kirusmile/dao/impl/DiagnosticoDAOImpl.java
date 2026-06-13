package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.DiagnosticoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.models.Diagnostico;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;
import pe.edu.pucp.kirusmile.models.TipoDiagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAOImpl implements DiagnosticoDAO {

    public DiagnosticoDAOImpl() {
        // Constructor vacío, conexión local en cada método
    }

    @Override
    public int save(Diagnostico objeto) {
        int idGenerado = 0;
        // CORRECCIÓN: Agregado el campo activo = 1
        String sql = "INSERT INTO Diagnostico (fid_detalle, fid_enfermedad_cie10, tipo, " +
                "observaciones, fecha_hora_registro, activo) VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());
            pst.setInt(2, objeto.getEnfermedadBase().getIdEnfermedadCIE10());

            // CORRECCIÓN: Mapeo seguro del Enum a String
            pst.setString(3, objeto.getTipo().name());
            pst.setString(4, objeto.getObservaciones());

            // CORRECCIÓN: Manejo seguro de LocalDateTime
            if (objeto.getFechaHoraRegistro() != null) {
                pst.setTimestamp(5, Timestamp.valueOf(objeto.getFechaHoraRegistro()));
            } else {
                pst.setNull(5, Types.TIMESTAMP);
            }

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdDiagnostico(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Diagnostico: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Diagnostico objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE Diagnostico SET fid_enfermedad_cie10 = ?, tipo = ?, " +
                "observaciones = ? WHERE id_diagnostico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, objeto.getEnfermedadBase().getIdEnfermedadCIE10());
            pst.setString(2, objeto.getTipo().name()); // Enum a String
            pst.setString(3, objeto.getObservaciones());
            pst.setInt(4, objeto.getIdDiagnostico());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Diagnostico: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico habilitado para soportar el botón "Eliminar" del FrontEnd
        String sql = "UPDATE Diagnostico SET activo = 0 WHERE id_diagnostico = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar Diagnostico: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public Diagnostico load(int id) {
        Diagnostico diagnostico = null;
        // CORRECCIÓN: INNER JOIN para traer los datos del CIE10 requeridos por la tabla de Blazor
        String sql = "SELECT d.*, c.codigo_cie, c.descripcion_oficial " +
                "FROM Diagnostico d " +
                "INNER JOIN EnfermedadCIE10 c ON d.fid_enfermedad_cie10 = c.id_enfermedad_cie10 " +
                "WHERE d.id_diagnostico = ? AND d.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    diagnostico = new Diagnostico();
                    diagnostico.setIdDiagnostico(rs.getInt("id_diagnostico"));

                    // Mapeo inverso: String a Enum (usamos toUpperCase por seguridad)
                    diagnostico.setTipo(TipoDiagnostico.valueOf(rs.getString("tipo").toUpperCase()));

                    diagnostico.setObservaciones(rs.getString("observaciones"));
                    diagnostico.setActivo(rs.getBoolean("activo"));

                    if (rs.getTimestamp("fecha_hora_registro") != null) {
                        diagnostico.setFechaHoraRegistro(rs.getTimestamp("fecha_hora_registro").toLocalDateTime());
                    }

                    // Ensamblaje de la consulta padre
                    DetalleHistorial detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt("fid_detalle"));
                    diagnostico.setDetalleHistorial(detalle);

                    // Ensamblaje de la Enfermedad CIE-10 (Crucial para Blazor)
                    EnfermedadCIE10 cie10 = new EnfermedadCIE10();
                    cie10.setIdEnfermedadCIE10(rs.getInt("fid_enfermedad_cie10"));
                    cie10.setCodigoCIE(rs.getString("codigo_cie"));
                    cie10.setDescripcionOficial(rs.getString("descripcion_oficial"));
                    diagnostico.setEnfermedadBase(cie10);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Diagnostico: " + e.getMessage());
        }
        return diagnostico;
    }

    @Override
    public List<Diagnostico> listALL() {
        return new ArrayList<>(); // Vacío por requerimiento
    }

    @Override
    public List<Diagnostico> listarPorFidDetalle(int fid_detalle) {
        List<Diagnostico> lista = new ArrayList<>();
        // CORRECCIÓN: Filtro por activos
        String sql = "SELECT id_diagnostico FROM Diagnostico WHERE fid_detalle = ? AND activo = 1 ORDER BY fecha_hora_registro ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1))); // Reutilizamos el potente load
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar diagnósticos: " + e.getMessage());
        }
        return lista;
    }
}
