package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.MedicoDAO;
import pe.edu.pucp.kirusmile.models.Medico;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoDAOImpl implements MedicoDAO {

    @Override
    public Medico load(String cmp) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, cmp, rne, fechaIngreso, firmaDigital, desactivado FROM Medico WHERE cmp = ? AND desactivado = 0";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cmp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico(
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getDate(5), rs.getString(6), rs.getString(7), null, null, rs.getString(8),
                            rs.getString(9), null, rs.getDate(10), rs.getString(11), null, rs.getBoolean(12)
                    );
                    return medico;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medico save(Medico t) {
        t.setDesactivado(false);
        String sql = "INSERT INTO Medico (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, cmp, rne, fechaIngreso, firmaDigital, desactivado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombres());
            ps.setString(3, t.getApellidoPaterno());
            ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null)
                ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else
                ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono());
            ps.setString(7, t.getCorreo());
            ps.setString(8, t.getCmp());
            ps.setString(9, t.getRne());
            if (t.getFechaIngreso() != null)
                ps.setDate(10, new java.sql.Date(t.getFechaIngreso().getTime()));
            else
                ps.setNull(10, java.sql.Types.DATE);
            ps.setString(11, t.getFirmaDigital());
            ps.setBoolean(12, t.getDesactivado());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medico update(Medico t) {
        String sql = "UPDATE Medico SET dni=?, nombres=?, apellidoPaterno=?, apellidoMaterno=?, fechaNacimiento=?, telefono=?, correo=?, rne=?, fechaIngreso=?, firmaDigital=?, desactivado=? WHERE cmp=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni());
            ps.setString(2, t.getNombres());
            ps.setString(3, t.getApellidoPaterno());
            ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null)
                ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else
                ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono());
            ps.setString(7, t.getCorreo());
            ps.setString(8, t.getRne());
            if (t.getFechaIngreso() != null)
                ps.setDate(9, new java.sql.Date(t.getFechaIngreso().getTime()));
            else
                ps.setNull(9, java.sql.Types.DATE);
            ps.setString(10, t.getFirmaDigital());
            ps.setBoolean(11, t.getDesactivado());
            ps.setString(12, t.getCmp());
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Medico t) {
        t.setDesactivado(true);
        String sql = "UPDATE Medico SET desactivado = ? WHERE cmp = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.getDesactivado());
            ps.setString(2, t.getCmp());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
