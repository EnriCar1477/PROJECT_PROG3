package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.EspecialidadDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Especialidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAOImpl implements EspecialidadDAO {

    private Connection con;

    public EspecialidadDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Especialidad objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO Especialidad (nombre_especialidad, costo_especialidad, activo) VALUES (?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, objeto.getNombreEspecialidad());
            pst.setDouble(2, objeto.getCostoEspecialidad());
            pst.setBoolean(3, objeto.isActivo());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdEspecialidad(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Especialidad: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Especialidad objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE Especialidad SET nombre_especialidad = ?, costo_especialidad = ?, activo = ? " +
                "WHERE id_especialidad = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, objeto.getNombreEspecialidad());
            pst.setDouble(2, objeto.getCostoEspecialidad());
            pst.setBoolean(3, objeto.isActivo());
            pst.setInt(4, objeto.getIdEspecialidad());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Especialidad: " + e.getMessage());
        }
        return filasAfectadas;
    }

    //usamos el borrado lógico! En lugar de un DELETE FROM, hacemos un UPDATE
    @Override
    public int delete(int id) {
        int filasAfectadas = 0;
        String sql = "UPDATE Especialidad SET activo = 0 WHERE id_especialidad = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al realizar borrado lógico de Especialidad: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public Especialidad load(int id) {
        Especialidad especialidad = null;
        String sql = "SELECT * FROM Especialidad WHERE id_especialidad = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    especialidad = new Especialidad();
                    especialidad.setIdEspecialidad(rs.getInt("id_especialidad"));
                    especialidad.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                    especialidad.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                    especialidad.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Especialidad: " + e.getMessage());
        }
        return especialidad;
    }

    // Este método trae absolutamente todas (activas e inactivas) para el módulo de administración
    @Override
    public List<Especialidad> listALL() {
        List<Especialidad> lista = new ArrayList<>();

        String sql = "SELECT * FROM Especialidad ORDER BY nombre_especialidad ASC";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(rs.getInt("id_especialidad"));
                esp.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                esp.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                esp.setActivo(rs.getBoolean("activo"));
                lista.add(esp);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todas las Especialidades: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Especialidad obtenerPorNombre(String nombreEspecialidad) {
        Especialidad especialidad = null;
        String sql = "SELECT * FROM Especialidad WHERE nombre_especialidad = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nombreEspecialidad);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    especialidad = new Especialidad();
                    especialidad.setIdEspecialidad(rs.getInt("id_especialidad"));
                    especialidad.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                    especialidad.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                    especialidad.setActivo(rs.getBoolean("activo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener Especialidad por nombre: " + e.getMessage());
        }
        return especialidad;
    }

    @Override
    public List<Especialidad> listarActivas() {
        List<Especialidad> lista = new ArrayList<>();
        // Este método trae solo las activas, ideal para llenar el ComboBox al registrar un nuevo Médico
        String sql = "SELECT * FROM Especialidad WHERE activo = 1 ORDER BY nombre_especialidad ASC";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(rs.getInt("id_especialidad"));
                esp.setNombreEspecialidad(rs.getString("nombre_especialidad"));
                esp.setCostoEspecialidad(rs.getDouble("costo_especialidad"));
                esp.setActivo(rs.getBoolean("activo"));
                lista.add(esp);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Especialidades activas: " + e.getMessage());
        }
        return lista;
    }
}
