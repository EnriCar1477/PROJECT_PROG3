package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.AnamnesisDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Anamnesis;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnamnesisDAOImpl implements AnamnesisDAO {

    public AnamnesisDAOImpl() {
        // Constructor vacío, la conexión se obtiene localmente
    }

    @Override
    public int save(Anamnesis objeto) {
        int idGenerado = 0;
        // CORRECCIÓN: Se agrega el campo activo
        String sql = "INSERT INTO Anamnesis (fid_detalle, motivo_principal, tiempo_enfermedad, " +
                "forma_inicio, relato_clinico, antecedentes_importantes, activo) VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());
            pst.setString(2, objeto.getMotivoPrincipal());
            pst.setString(3, objeto.getTiempoEnfermedad());
            pst.setString(4, objeto.getFormaInicio());
            pst.setString(5, objeto.getRelatoClinico());
            pst.setString(6, objeto.getAntecedentesImportantes());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdAnamnesis(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Anamnesis: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Anamnesis objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE Anamnesis SET motivo_principal = ?, tiempo_enfermedad = ?, " +
                "forma_inicio = ?, relato_clinico = ?, antecedentes_importantes = ? " +
                "WHERE id_anamnesis = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, objeto.getMotivoPrincipal());
            pst.setString(2, objeto.getTiempoEnfermedad());
            pst.setString(3, objeto.getFormaInicio());
            pst.setString(4, objeto.getRelatoClinico());
            pst.setString(5, objeto.getAntecedentesImportantes());
            pst.setInt(6, objeto.getIdAnamnesis());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Anamnesis: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico implementado
        String sql = "UPDATE Anamnesis SET activo = 0 WHERE id_anamnesis = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar Anamnesis: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public Anamnesis load(int id) {
        Anamnesis anamnesis = null;
        // CORRECCIÓN: Filtro por activo = 1
        String sql = "SELECT * FROM Anamnesis WHERE id_anamnesis = ? AND activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    anamnesis = new Anamnesis();
                    anamnesis.setIdAnamnesis(rs.getInt("id_anamnesis"));
                    anamnesis.setMotivoPrincipal(rs.getString("motivo_principal"));
                    anamnesis.setTiempoEnfermedad(rs.getString("tiempo_enfermedad"));
                    anamnesis.setFormaInicio(rs.getString("forma_inicio"));
                    anamnesis.setRelatoClinico(rs.getString("relato_clinico"));
                    anamnesis.setAntecedentesImportantes(rs.getString("antecedentes_importantes"));
                    anamnesis.setActivo(rs.getBoolean("activo")); // Seteamos el estado

                    // CORRECCIÓN: Ensamblamos la llave foránea para no perderla
                    DetalleHistorial detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt("fid_detalle"));
                    anamnesis.setDetalleHistorial(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Anamnesis: " + e.getMessage());
        }
        return anamnesis;
    }

    @Override
    public List<Anamnesis> listALL() {
        return new ArrayList<>(); // Vacío por requerimiento
    }

    @Override
    public Anamnesis obtenerPorFidDetalle(int fid_detalle) {
        // CORRECCIÓN: Filtro por activo = 1
        String sql = "SELECT id_anamnesis FROM Anamnesis WHERE fid_detalle = ? AND activo = 1";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return load(rs.getInt(1)); // Reutilizamos el load
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorFidDetalle: " + e.getMessage());
        }
        return null;
    }
}
