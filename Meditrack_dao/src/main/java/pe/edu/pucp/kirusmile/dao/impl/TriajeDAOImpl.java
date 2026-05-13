package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.TriajeDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Triaje;

import java.sql.*;
import java.util.List;

public class TriajeDAOImpl implements TriajeDAO {

    private Connection con;

    public TriajeDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Triaje objeto) {
        int idGenerado = 0;
        // Usamos fid_detalle tal como está en tu nuevo script de SQL
        String sql = "INSERT INTO Triaje (fid_detalle, peso, talla, presion_arterial, temperatura, saturacion) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // El id_detalle (FK) viene del atributo idTriaje del objeto en este modelo 1:1
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
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar save en Triaje: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Triaje objeto) {
        int filasAfectadas = 0;
        // Aquí actualizamos por la PK id_triaje
        String sql = "UPDATE Triaje SET peso = ?, talla = ?, presion_arterial = ?, " +
                "temperatura = ?, saturacion = ? WHERE id_triaje = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDouble(1, objeto.getPeso());
            pst.setDouble(2, objeto.getTalla());
            pst.setString(3, objeto.getPresionArterial());
            pst.setDouble(4, objeto.getTemperatura());
            pst.setDouble(5, objeto.getSaturacion());
            pst.setInt(6, objeto.getIdTriaje());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar update en Triaje: " + e.getMessage());
        }
        return filasAfectadas;
    }

    //REGLA: PROHIBIDO BORRAR DATOS
    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Triaje load(int id) {
        Triaje triaje = null;
        String sql = "SELECT * FROM Triaje WHERE id_triaje = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    triaje = new Triaje();
                    triaje.setIdTriaje(rs.getInt("id_triaje"));
                    // fid_detalle no se setea al modelo Java porque no existe ese atributo en la clase
                    triaje.setPeso(rs.getDouble("peso"));
                    triaje.setTalla(rs.getDouble("talla"));
                    triaje.setPresionArterial(rs.getString("presion_arterial"));
                    triaje.setTemperatura(rs.getDouble("temperatura"));
                    triaje.setSaturacion(rs.getDouble("saturacion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Triaje: " + e.getMessage());
        }
        return triaje;
    }

    // No se requiere listar todos los triajes del sistema
    @Override
    public List<Triaje> listALL() {
        return List.of();
    }

    @Override
    public Triaje obtenerPorIdDetalle(int fid_detalle) {
        String sql = "SELECT id_triaje FROM Triaje WHERE fid_detalle = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return load(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorFidDetalle: " + e.getMessage());
        }
        return null;
    }
}
