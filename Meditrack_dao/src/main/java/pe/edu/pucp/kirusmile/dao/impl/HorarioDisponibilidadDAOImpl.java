package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.HorarioDisponibilidadDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.HorarioDisponibilidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDisponibilidadDAOImpl implements HorarioDisponibilidadDAO {
    private Connection con;

    public HorarioDisponibilidadDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }

    @Override
    public int save(HorarioDisponibilidad objeto) {
        int idGenerado = 0;
        // Usamos fid_medico tal como definimos en el script SQL
        String sql = "INSERT INTO HorarioDisponibilidad (fid_medico, dia_semana, " +
                "hora_inicio, hora_fin, activo) VALUES (?, ?, ?, ?, 1)";

        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // 1. Vinculamos con el médico (Asumiendo que el objeto tiene la referencia)
            pst.setInt(1, objeto.getMedico().getIdMedico());

            // 2. dia de semana
            pst.setString(2, objeto.getDiaSemana());

            // 3. Mapeo de LocalTime a java.sql.Time (Manejo de horas en JDBC)
            pst.setTime(3, java.sql.Time.valueOf(objeto.getHoraInicio()));
            pst.setTime(4, java.sql.Time.valueOf(objeto.getHoraFin()));

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    objeto.setIdHorario(idGenerado);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar Horario: " + e.getMessage());
        }
        return idGenerado;
    }

    @Override
    public int update(HorarioDisponibilidad objeto) {
        int filasAfectadas = 0;
        String sql = "UPDATE HorarioDisponibilidad SET dia_semana = ?, hora_inicio = ?, " +
                "hora_fin = ? WHERE id_horario = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, objeto.getDiaSemana());
            pst.setTime(2, java.sql.Time.valueOf(objeto.getHoraInicio()));
            pst.setTime(3, java.sql.Time.valueOf(objeto.getHoraFin()));
            pst.setInt(4, objeto.getIdHorario());

            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar Horario: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public int delete(int id) {
        int filasAfectadas = 0;
        // Aplicamos borrado lógico (activo = 0) para no romper citas pasadas
        String sql = "UPDATE HorarioDisponibilidad SET activo = 0 WHERE id_horario = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            filasAfectadas = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error en borrado lógico de Horario: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public HorarioDisponibilidad load(int id) {
        HorarioDisponibilidad horario = null;
        String sql = "SELECT * FROM HorarioDisponibilidad WHERE id_horario = ? AND activo = 1";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    horario = new HorarioDisponibilidad();
                    horario.setIdHorario(rs.getInt("id_horario"));
                    horario.setDiaSemana(rs.getString("dia_semana"));
                    horario.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    horario.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar Horario: " + e.getMessage());
        }
        return horario;
    }

    // No se usa listar todos los horarios de todos los médicos a la vez
    @Override
    public List<HorarioDisponibilidad> listALL() {
        return List.of();
    }

    @Override
    public List<HorarioDisponibilidad> listarPorFidMedico(int fid_medico) {
        List<HorarioDisponibilidad> lista = new ArrayList<>();
        String sql = "SELECT id_horario FROM HorarioDisponibilidad WHERE fid_medico = ? AND activo = 1 " +
                "ORDER BY FIELD(dia_semana, 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'), hora_inicio ASC";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, fid_medico);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(this.load(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar horarios por médico: " + e.getMessage());
        }
        return lista;
    }

}
