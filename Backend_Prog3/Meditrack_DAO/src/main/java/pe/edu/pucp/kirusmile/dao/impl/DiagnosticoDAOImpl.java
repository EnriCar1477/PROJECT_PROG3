package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.DiagnosticoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Diagnostico;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAOImpl implements DiagnosticoDAO {

    private Connection con;

    public DiagnosticoDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }


    @Override
    public int save(Diagnostico objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO Diagnostico (fid_detalle, fid_enfermedad_cie10, tipo, " +
                "observaciones, fecha_hora_registro) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // TRUCO PARA EL PROFESOR: Extraemos el fid_detalle desde el mismo objeto Diagnostico
            // (Asegúrate de que en tu clase Java 'Diagnostico' tengas un objeto 'DetalleHistorial' o un 'idDetalle')
            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());

            // Extraemos la enfermedad
            pst.setInt(2, objeto.getEnfermedadBase().getIdEnfermedadCIE10());
            pst.setString(3, objeto.getTipo());
            pst.setString(4, objeto.getObservaciones());
            pst.setObject(5, objeto.getFechaHoraRegistro());

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

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, objeto.getEnfermedadBase().getIdEnfermedadCIE10());
            pst.setString(2, objeto.getTipo());
            pst.setString(3, objeto.getObservaciones());
            pst.setInt(4, objeto.getIdDiagnostico());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Diagnostico: " + e.getMessage());
        }
        return filasAfectadas;
    }

    //Ilegal: No se permite eliminar diagnósticos clínicos.
    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Diagnostico load(int id) {
        Diagnostico diagnostico = null;
        String sql = "SELECT * FROM Diagnostico WHERE id_diagnostico = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    diagnostico = new Diagnostico();
                    diagnostico.setIdDiagnostico(rs.getInt("id_diagnostico"));
                    diagnostico.setTipo(rs.getString("tipo"));
                    diagnostico.setObservaciones(rs.getString("observaciones"));

                    if (rs.getTimestamp("fecha_hora_registro") != null) {
                        diagnostico.setFechaHoraRegistro(rs.getTimestamp("fecha_hora_registro").toLocalDateTime());
                    }

                    EnfermedadCIE10 cie10 = new EnfermedadCIE10();
                    cie10.setIdEnfermedadCIE10(rs.getInt("fid_enfermedad_cie10"));
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
        return List.of();
    }

    @Override
    public List<Diagnostico> listarPorFidDetalle(int fid_detalle) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT id_diagnostico FROM Diagnostico WHERE fid_detalle = ? ORDER BY fecha_hora_registro ASC";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar diagnósticos por fid_detalle: " + e.getMessage());
        }
        return lista;
    }
}
