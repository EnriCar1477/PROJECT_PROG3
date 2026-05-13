package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.TratamientoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.TipoTratamiento;
import pe.edu.pucp.kirusmile.models.Tratamiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientoDAOImpl implements TratamientoDAO {
    private Connection con;

    public TratamientoDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Tratamiento objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO Tratamiento (fid_detalle, fid_tipo_tratamiento, indicaciones, " +
                "fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getDetalleHistorial().getIdDetalle());
            pst.setInt(2, objeto.getTipo().ordinal() + 1); // Asumiendo que el Enum mapea con ID numérico en BD
            pst.setString(3, objeto.getIndicaciones());

            // --- CORRECCIÓN: PROTECCIÓN PARA FECHA DE INICIO ---
            if (objeto.getFechaInicio() != null) {
                pst.setDate(4, java.sql.Date.valueOf(objeto.getFechaInicio()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            // --- CORRECCIÓN: PROTECCIÓN PARA FECHA DE FIN (CRÍTICO EN TRATAMIENTOS) ---
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

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, objeto.getTipo().ordinal() + 1);
            pst.setString(2, objeto.getIndicaciones());

            // --- CORRECCIONES EN UPDATE ---
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
        throw new UnsupportedOperationException("Ilegal: No se permite borrar tratamientos del historial clínico.");
    }

    @Override
    public Tratamiento load(int id) {
        Tratamiento tratamiento = null;
        String sql = "SELECT * FROM Tratamiento WHERE id_tratamiento = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    tratamiento = new Tratamiento();
                    tratamiento.setIdTratamiento(rs.getInt("id_tratamiento"));

                    int idTipo = rs.getInt("fid_tipo_tratamiento");
                    tratamiento.setTipo(TipoTratamiento.values()[idTipo - 1]);

                    tratamiento.setIndicaciones(rs.getString("indicaciones"));

                    // --- CORRECCIONES EN LOAD ---
                    if (rs.getDate("fecha_inicio") != null) {
                        tratamiento.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    }
                    if (rs.getDate("fecha_fin") != null) {
                        tratamiento.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Tratamiento: " + e.getMessage());
        }
        return tratamiento;
    }

    //no se buscara todos los tratamientos por ahora
    @Override
    public List<Tratamiento> listALL() {
        return List.of();// No se suelen listar todos masivamente
    }

    @Override
    public List<Tratamiento> listarPorFidDetalle(int fid_detalle) {
        List<Tratamiento> lista = new ArrayList<>();
        String sql = "SELECT id_tratamiento FROM Tratamiento WHERE fid_detalle = ? ORDER BY fecha_inicio ASC";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_detalle);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tratamientos por fid_detalle: " + e.getMessage());
        }
        return lista;
    }
}
