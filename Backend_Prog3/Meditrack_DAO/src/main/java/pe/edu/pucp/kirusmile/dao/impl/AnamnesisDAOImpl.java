package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.AnamnesisDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Anamnesis;

import java.sql.*;
import java.util.List;

public class AnamnesisDAOImpl implements AnamnesisDAO {

    private Connection con;

    public AnamnesisDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Anamnesis objeto) {

        int idGenerado = 0;
        String sql = "INSERT INTO Anamnesis (fid_detalle, motivo_principal, tiempo_enfermedad, " +
                "forma_inicio, relato_clinico, antecedentes_importantes) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // El idAnamnesis (PK de Java) actúa como el fid_detalle (FK de SQL) en esta relación 1:1
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

        try (PreparedStatement pst = con.prepareStatement(sql)) {
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

    // Bloqueado: La anamnesis es un documento legal histórico
    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Anamnesis load(int id) {
        Anamnesis anamnesis = null;
        String sql = "SELECT * FROM Anamnesis WHERE id_anamnesis = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    anamnesis = new Anamnesis();
                    anamnesis.setIdAnamnesis(rs.getInt("id_anamnesis"));
                    // fid_detalle no se mapea al objeto Java porque no existe ese campo en la clase
                    anamnesis.setMotivoPrincipal(rs.getString("motivo_principal"));
                    anamnesis.setTiempoEnfermedad(rs.getString("tiempo_enfermedad"));
                    anamnesis.setFormaInicio(rs.getString("forma_inicio"));
                    anamnesis.setRelatoClinico(rs.getString("relato_clinico"));
                    anamnesis.setAntecedentesImportantes(rs.getString("antecedentes_importantes"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Anamnesis: " + e.getMessage());
        }
        return anamnesis;
    }

    // No tiene sentido listar anamnesis de forma masiva por ahora
    @Override
    public List<Anamnesis> listALL() {
        return List.of();
    }

    @Override
    public Anamnesis obtenerPorFidDetalle(int fid_detalle) {
        String sql = "SELECT id_anamnesis FROM Anamnesis WHERE fid_detalle = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
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
