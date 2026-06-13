package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.TriajeDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.models.Triaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TriajeDAOImpl implements TriajeDAO {

    public TriajeDAOImpl() {
        // Constructor vacío: Ya no guardamos la conexión global
    }

    @Override
    public int save(Triaje objeto) {
        int idGenerado = 0;
        // CORRECCIÓN: Se agrega el campo activo = 1
        String sql = "INSERT INTO Triaje (fid_detalle, peso, talla, presion_arterial, temperatura, saturacion, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());
            pst.setDouble(2, objeto.getPeso());
            pst.setDouble(3, objeto.getTalla());
            pst.setString(4, objeto.getPresionArterial());
            pst.setDouble(5, objeto.getTemperatura());
            pst.setDouble(6, objeto.getSaturacion());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdTriaje(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Triaje: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Triaje objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE Triaje SET peso = ?, talla = ?, presion_arterial = ?, " +
                "temperatura = ?, saturacion = ? WHERE id_triaje = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, objeto.getPeso());
            pst.setDouble(2, objeto.getTalla());
            pst.setString(3, objeto.getPresionArterial());
            pst.setDouble(4, objeto.getTemperatura());
            pst.setDouble(5, objeto.getSaturacion());
            pst.setInt(6, objeto.getIdTriaje());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Triaje: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico
        String sql = "UPDATE Triaje SET activo = 0 WHERE id_triaje = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar Triaje: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public Triaje load(int id) {
        Triaje triaje = null;
        // CORRECCIÓN: Filtro por activo
        String sql = "SELECT * FROM Triaje WHERE id_triaje = ? AND activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    triaje = new Triaje();
                    triaje.setIdTriaje(rs.getInt("id_triaje"));
                    triaje.setPeso(rs.getDouble("peso"));
                    triaje.setTalla(rs.getDouble("talla"));
                    triaje.setPresionArterial(rs.getString("presion_arterial"));
                    triaje.setTemperatura(rs.getDouble("temperatura"));
                    triaje.setSaturacion(rs.getDouble("saturacion"));
                    triaje.setActivo(rs.getBoolean("activo"));

                    // CORRECCIÓN: Ensamblar llave foránea
                    DetalleHistorial detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt("fid_detalle"));
                    triaje.setDetalleHistorial(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Triaje: " + e.getMessage());
        }
        return triaje;
    }

    @Override
    public List<Triaje> listALL() { return new ArrayList<>(); }

    @Override
    public Triaje obtenerPorIdDetalle(int fid_detalle) {
        // CORRECCIÓN: Filtro por activo
        String sql = "SELECT id_triaje FROM Triaje WHERE fid_detalle = ? AND activo = 1";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorIdDetalle: " + e.getMessage());
        }
        return null;
    }
}
