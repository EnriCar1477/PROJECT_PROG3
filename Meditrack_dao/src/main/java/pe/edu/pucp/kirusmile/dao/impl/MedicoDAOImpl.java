package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.MedicoDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Especialidad;
import pe.edu.pucp.kirusmile.models.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAOImpl implements MedicoDAO {

    private Connection con;

    public MedicoDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(Medico objeto) {
        int idGenerado = 0;
        String sql = "INSERT INTO Medico (fid_empleado, cmp, rne, fid_especialidad, fecha_ingreso, firma_digital) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, objeto.getIdEmpleado());
            pst.setString(2, objeto.getCmp());

            // El RNE puede ser nulo en médicos generales
            if (objeto.getRne() != null) {
                pst.setString(3, objeto.getRne());
            } else {
                pst.setNull(3, Types.VARCHAR);
            }

            pst.setInt(4, objeto.getEspecialidad().getIdEspecialidad());

            // --- CORRECCIÓN: PROTECCIÓN PARA FECHA DE INGRESO ---
            if (objeto.getFechaIngreso() != null) {
                pst.setDate(5, java.sql.Date.valueOf(objeto.getFechaIngreso()));
            } else {
                pst.setNull(5, java.sql.Types.DATE);
            }

            pst.setString(6, objeto.getFirmaDigital());

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdMedico(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Medico: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(Medico objeto) {
        int resultado = 0;
        String sql = "UPDATE Medico SET cmp = ?, rne = ?, fid_especialidad = ?, " +
                "fecha_ingreso = ?, firma_digital = ? WHERE id_medico = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, objeto.getCmp());

            if (objeto.getRne() != null) {
                pst.setString(2, objeto.getRne());
            } else {
                pst.setNull(2, Types.VARCHAR);
            }

            pst.setInt(3, objeto.getEspecialidad().getIdEspecialidad());

            // --- CORRECCIONES EN UPDATE ---
            if (objeto.getFechaIngreso() != null) {
                pst.setDate(4, java.sql.Date.valueOf(objeto.getFechaIngreso()));
            } else {
                pst.setNull(4, java.sql.Types.DATE);
            }

            pst.setString(5, objeto.getFirmaDigital());
            pst.setInt(6, objeto.getIdMedico());

            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Medico: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        return 0;// Usualmente el borrado lógico se hace en la tabla Padre (Empleado)
    }

    @Override
    public Medico load(int id) {
        Medico medico = null;
        String sql = "SELECT * FROM Medico WHERE id_medico = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    medico = new Medico();
                    medico.setIdMedico(rs.getInt("id_medico"));
                    medico.setIdEmpleado(rs.getInt("fid_empleado")); // Heredado
                    medico.setCmp(rs.getString("cmp"));
                    medico.setRne(rs.getString("rne"));

                    // --- CORRECCIONES EN LOAD ---
                    if (rs.getDate("fecha_ingreso") != null) {
                        medico.setFechaIngreso(rs.getDate("fecha_ingreso").toLocalDate());
                    }

                    medico.setFirmaDigital(rs.getString("firma_digital"));

                    // Ensamblaje básico de la especialidad
                    Especialidad esp = new Especialidad();
                    esp.setIdEspecialidad(rs.getInt("fid_especialidad"));
                    medico.setEspecialidad(esp);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Medico: " + e.getMessage());
        }
        return medico;
    }

    @Override
    public List<Medico> listALL() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT id_medico FROM Medico";
        try (PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                lista.add(this.load(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar Medicos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Medico obtenerPorCMP(String cmp) {
        String sql = "SELECT id_medico FROM Medico WHERE cmp = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, cmp);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médico por CMP: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Medico obtenerPorFidEmpleado(int fid_empleado) {
        String sql = "SELECT id_medico FROM Medico WHERE fid_empleado = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_empleado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return load(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener médico por empleado: " + e.getMessage());
        }
        return null;
    }
}
