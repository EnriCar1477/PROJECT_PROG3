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
    public Medico load(String dni) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, cmp, rne, especialidad_id, fechaIngreso, firmaDigital FROM Medico WHERE dni = ?";
        
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, dni);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico medico = new Medico(
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8), 
                            rs.getString(9), null, rs.getDate(11), rs.getString(12), null
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
        String sql = "INSERT INTO Medico (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, cmp, rne, fechaIngreso, firmaDigital) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                     
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno());
            ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo()); ps.setString(8, t.getCmp());
            ps.setString(9, t.getRne());
            if (t.getFechaIngreso() != null) ps.setDate(10, new java.sql.Date(t.getFechaIngreso().getTime()));
            else ps.setNull(10, java.sql.Types.DATE);
            ps.setString(11, t.getFirmaDigital());
            
            ps.executeUpdate();
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medico update(Medico t) {
        String sql = "UPDATE Medico SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, fechaNacimiento = ?, telefono = ?, correo = ?, cmp = ?, rne = ?, fechaIngreso = ?, firmaDigital = ? WHERE dni = ?";
                     
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, t.getNombres()); ps.setString(2, t.getApellidoPaterno()); ps.setString(3, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(4, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(4, java.sql.Types.DATE);
            ps.setString(5, t.getTelefono()); ps.setString(6, t.getCorreo()); ps.setString(7, t.getCmp());
            ps.setString(8, t.getRne());
            if (t.getFechaIngreso() != null) ps.setDate(9, new java.sql.Date(t.getFechaIngreso().getTime()));
            else ps.setNull(9, java.sql.Types.DATE);
            ps.setString(10, t.getFirmaDigital()); ps.setString(11, t.getDni());
            
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Medico t) {
        throw new UnsupportedOperationException("Error: Método 'remove' deshabilitado por las restricciones de negocio para proteger esta tabla.");
    }
}
