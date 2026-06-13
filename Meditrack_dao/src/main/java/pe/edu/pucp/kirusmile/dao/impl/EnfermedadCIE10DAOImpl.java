<<<<<<< HEAD
package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.EnfermedadCIE10DAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadCIE10DAOImpl implements EnfermedadCIE10DAO {

    public EnfermedadCIE10DAOImpl() {
        // Constructor vacío: Ya no guardamos la conexión aquí para evitar fugas de memoria
    }

    @Override
    public int save(EnfermedadCIE10 objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO EnfermedadCIE10 (codigo_cie, descripcion_oficial) VALUES (?, ?)";

        // CORRECCIÓN: Abrimos la conexión localmente
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, objeto.getCodigoCIE());
            pst.setString(2, objeto.getDescripcionOficial());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdEnfermedadCIE10(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar EnfermedadCIE10: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(EnfermedadCIE10 objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE EnfermedadCIE10 SET codigo_cie = ?, descripcion_oficial = ? WHERE id_enfermedad_cie10 = ?";

        // CORRECCIÓN: Abrimos la conexión localmente
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, objeto.getCodigoCIE());
            pst.setString(2, objeto.getDescripcionOficial());
            pst.setInt(3, objeto.getIdEnfermedadCIE10());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar EnfermedadCIE10: " + e.getMessage());
        }
        return filasAfectadas;
    }

    // ¡EXCELENTE PRÁCTICA! Bloqueo estricto para catálogos inmutables
    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Ilegal: No se permite eliminar códigos de diagnóstico internacional.");
    }

    @Override
    public EnfermedadCIE10 load(int id) {
        EnfermedadCIE10 enfermedad = null;
        String sql = "SELECT * FROM EnfermedadCIE10 WHERE id_enfermedad_cie10 = ?";

        // CORRECCIÓN: Abrimos la conexión localmente
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    enfermedad = new EnfermedadCIE10();
                    enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                    enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                    enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar EnfermedadCIE10: " + e.getMessage());
        }
        return enfermedad;
    }

    @Override
    public List<EnfermedadCIE10> listALL() {
        List<EnfermedadCIE10> lista = new ArrayList<>();
        String sql = "SELECT * FROM EnfermedadCIE10 ORDER BY codigo_cie ASC";

        // CORRECCIÓN: Abrimos la conexión localmente
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                EnfermedadCIE10 enfermedad = new EnfermedadCIE10();
                enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                lista.add(enfermedad);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar EnfermedadCIE10: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public EnfermedadCIE10 obtenerPorCodigoCIE(String codigoCIE) {
        EnfermedadCIE10 enfermedad = null;
        String sql = "SELECT * FROM EnfermedadCIE10 WHERE codigo_cie = ?";

        // CORRECCIÓN: Abrimos la conexión localmente
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, codigoCIE);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    enfermedad = new EnfermedadCIE10();
                    enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                    enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                    enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorCodigoCIE: " + e.getMessage());
        }
        return enfermedad;
    }
}
=======
package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.EnfermedadCIE10DAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.EnfermedadCIE10;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadCIE10DAOImpl implements EnfermedadCIE10DAO {

    private Connection con;

    public EnfermedadCIE10DAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(EnfermedadCIE10 objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO EnfermedadCIE10 (codigo_cie, descripcion_oficial) VALUES (?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, objeto.getCodigoCIE());
            pst.setString(2, objeto.getDescripcionOficial());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdEnfermedadCIE10(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar EnfermedadCIE10: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(EnfermedadCIE10 objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE EnfermedadCIE10 SET codigo_cie = ?, descripcion_oficial = ? WHERE id_enfermedad_cie10 = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, objeto.getCodigoCIE());
            pst.setString(2, objeto.getDescripcionOficial());
            pst.setInt(3, objeto.getIdEnfermedadCIE10());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar EnfermedadCIE10: " + e.getMessage());
        }
        return filasAfectadas;
    }

    // Bloqueo estricto: Las normativas de la OMS son inmutables
    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Ilegal: No se permite eliminar códigos de diagnóstico internacional.");
    }

    @Override
    public EnfermedadCIE10 load(int id) {
        EnfermedadCIE10 enfermedad = null;
        String sql = "SELECT * FROM EnfermedadCIE10 WHERE id_enfermedad_cie10 = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    enfermedad = new EnfermedadCIE10();
                    enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                    enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                    enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar EnfermedadCIE10: " + e.getMessage());
        }
        return enfermedad;
    }

    @Override
    public List<EnfermedadCIE10> listALL() {
        List<EnfermedadCIE10> lista = new ArrayList<>();
        // Ordenamos alfabéticamente por el código (ej. K01, K02) para que se vea bien en el Front-end
        String sql = "SELECT * FROM EnfermedadCIE10 ORDER BY codigo_cie ASC";

        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                EnfermedadCIE10 enfermedad = new EnfermedadCIE10();
                enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                lista.add(enfermedad);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar EnfermedadCIE10: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public EnfermedadCIE10 obtenerPorCodigoCIE(String codigoCIE) {
        EnfermedadCIE10 enfermedad = null;
        String sql = "SELECT * FROM EnfermedadCIE10 WHERE codigo_cie = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, codigoCIE);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    enfermedad = new EnfermedadCIE10();
                    enfermedad.setIdEnfermedadCIE10(rs.getInt("id_enfermedad_cie10"));
                    enfermedad.setCodigoCIE(rs.getString("codigo_cie"));
                    enfermedad.setDescripcionOficial(rs.getString("descripcion_oficial"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorCodigoCIE: " + e.getMessage());
        }
        return enfermedad;
    }
}
>>>>>>> 17c12cc8a68643e5f7a0f04e500cd95058c63fb7
