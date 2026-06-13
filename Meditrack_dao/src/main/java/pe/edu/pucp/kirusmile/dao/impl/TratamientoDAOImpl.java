package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.TratamientoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.DetalleHistorial;
import pe.edu.pucp.kirusmile.models.TipoTratamiento;
import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAOImpl implements TratamientoDAO {

    public TratamientoDAOImpl() {
        // Constructor vacío: Ya no guardamos la conexión global para evitar fugas de memoria
    }

    @Override
    public int save(Tratamiento objeto) {
        int idGenerado = 0;
        // CORRECCIÓN: Se agrega el campo activo
        String sql = "INSERT INTO Tratamiento (fid_detalle, fid_tipo_tratamiento, indicaciones, " +
                "fecha_inicio, fecha_fin, activo) VALUES (?, ?, ?, ?, ?, 1)";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());
            pst.setInt(2, objeto.getTipo().ordinal() + 1);
            pst.setString(3, objeto.getIndicaciones());

            if (objeto.getFechaInicio() != null) {
                pst.setDate(4, java.sql.Date.valueOf(objeto.getFechaInicio()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            if (objeto.getFechaFin() != null) {
                pst.setDate(5, java.sql.Date.valueOf(objeto.getFechaFin()));
            } else {
                pst.setNull(5, java.sql.Types.DATE);
            }

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdTratamiento(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Tratamiento: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Tratamiento objeto) {
        int resultado = 0;
        String sql = "UPDATE Tratamiento SET fid_tipo_tratamiento = ?, indicaciones = ?, " +
                "fecha_inicio = ?, fecha_fin = ? WHERE id_tratamiento = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, objeto.getTipo().ordinal() + 1);
            pst.setString(2, objeto.getIndicaciones());

            if (objeto.getFechaInicio() != null) {
                pst.setDate(3, java.sql.Date.valueOf(objeto.getFechaInicio()));
            } else {
                pst.setNull(3, java.sql.Types.DATE);
            }

            if (objeto.getFechaFin() != null) {
                pst.setDate(4, java.sql.Date.valueOf(objeto.getFechaFin()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            pst.setInt(5, objeto.getIdTratamiento());

            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Tratamiento: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        // CORRECCIÓN: Borrado Lógico habilitado para el FrontEnd
        String sql = "UPDATE Tratamiento SET activo = 0 WHERE id_tratamiento = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar Tratamiento: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public Tratamiento load(int id) {
        Tratamiento tratamiento = null;
        // CORRECCIÓN: Filtro AND activo = 1
        String sql = "SELECT * FROM Tratamiento WHERE id_tratamiento = ? AND activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    tratamiento = new Tratamiento();
                    tratamiento.setIdTratamiento(rs.getInt("id_tratamiento"));

                    int idTipo = rs.getInt("fid_tipo_tratamiento");
                    tratamiento.setTipo(TipoTratamiento.values()[idTipo - 1]);

                    tratamiento.setIndicaciones(rs.getString("indicaciones"));
                    tratamiento.setActivo(rs.getBoolean("activo")); // Seteamos el estado

                    if (rs.getDate("fecha_inicio") != null) {
                        tratamiento.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    }
                    if (rs.getDate("fecha_fin") != null) {
                        tratamiento.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                    }

                    // CORRECCIÓN: Ensamblaje de la consulta padre
                    DetalleHistorial detalle = new DetalleHistorial();
                    detalle.setIdDetalle(rs.getInt("fid_detalle"));
                    tratamiento.setDetalleHistorial(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Tratamiento: " + e.getMessage());
        }
        return tratamiento;
    }

    @Override
    public List<Tratamiento> listALL() {
        return new ArrayList<>(); // Vacío por requerimiento
    }

    @Override
    public List<Tratamiento> listarPorFidDetalle(int fid_detalle) {
        List<Tratamiento> lista = new ArrayList<>();
        // CORRECCIÓN: Filtro AND activo = 1
        String sql = "SELECT id_tratamiento FROM Tratamiento WHERE fid_detalle = ? AND activo = 1 ORDER BY fecha_inicio ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1))); // Reutilizamos el load
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tratamientos por fid_detalle: " + e.getMessage());
        }
        return lista;
    }
}