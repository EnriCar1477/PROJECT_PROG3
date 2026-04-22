package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.EmpleadoDAO;
import pe.edu.pucp.kirusmile.models.Empleado;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public Empleado load(String id) {
        String sql = "SELECT dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, codigoEmpleado, fechaVinculacion, estadoLaboral FROM Empleado WHERE codigoEmpleado = ? AND estadoLaboral = 1";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado empleado = new Empleado(
                            rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getDate(5), rs.getString(6), rs.getString(7),
                            rs.getString(8), rs.getDate(9)
                    );
                    empleado.setEstadoLaboral(rs.getBoolean(10));
                    return empleado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empleado save(Empleado t) {
        t.setEstadoLaboral(true);
        String sql = "INSERT INTO Empleado (dni, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, telefono, correo, codigoEmpleado, fechaVinculacion, estadoLaboral) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo());
            ps.setString(8, t.getCodigoEmpleado());
            if (t.getFechaVinculacion() != null) ps.setDate(9, new java.sql.Date(t.getFechaVinculacion().getTime()));
            else ps.setNull(9, java.sql.Types.DATE);
            ps.setBoolean(10, t.isEstadoLaboral());
            
            ps.executeUpdate();
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empleado update(Empleado t) {
        String sql = "UPDATE Empleado SET dni=?, nombres=?, apellidoPaterno=?, apellidoMaterno=?, fechaNacimiento=?, telefono=?, correo=?, fechaVinculacion=?, estadoLaboral=? WHERE codigoEmpleado=?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDni()); ps.setString(2, t.getNombres()); ps.setString(3, t.getApellidoPaterno()); ps.setString(4, t.getApellidoMaterno());
            if (t.getFechaNacimiento() != null) ps.setDate(5, new java.sql.Date(t.getFechaNacimiento().getTime()));
            else ps.setNull(5, java.sql.Types.DATE);
            ps.setString(6, t.getTelefono()); ps.setString(7, t.getCorreo());
            if (t.getFechaVinculacion() != null) ps.setDate(8, new java.sql.Date(t.getFechaVinculacion().getTime()));
            else ps.setNull(8, java.sql.Types.DATE);
            ps.setBoolean(9, t.isEstadoLaboral());
            ps.setString(10, t.getCodigoEmpleado());
            
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(Empleado t) {
        t.setEstadoLaboral(false); // SOFT DELETE
        String sql = "UPDATE Empleado SET estadoLaboral = ? WHERE codigoEmpleado = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, t.isEstadoLaboral());
            ps.setString(2, t.getCodigoEmpleado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
