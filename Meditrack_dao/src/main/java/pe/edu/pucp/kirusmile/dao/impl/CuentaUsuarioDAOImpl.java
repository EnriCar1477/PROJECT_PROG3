package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.CuentaUsuarioDAO;
import pe.edu.pucp.kirusmile.models.CuentaUsuario;
import pe.edu.pucp.kirusmile.models.RolUsuario;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaUsuarioDAOImpl implements CuentaUsuarioDAO {

    @Override
    public CuentaUsuario load(String username) {
        String sql = "SELECT username, passwordHash, rol, ultimoAcceso FROM CuentaUsuario WHERE username = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CuentaUsuario cuenta = new CuentaUsuario(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3) != null ? RolUsuario.valueOf(rs.getString(3)) : null
                    );
                    if (rs.getTimestamp(4) != null) {
                        cuenta.setUltimoAcceso(rs.getTimestamp(4).toLocalDateTime());
                    }
                    return cuenta;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CuentaUsuario save(CuentaUsuario t) {
        String sql = "INSERT INTO CuentaUsuario (username, passwordHash, rol, ultimoAcceso) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getPasswordHash());
            ps.setString(3, t.getRol() != null ? t.getRol().name() : null);
            if (t.getUltimoAcceso() != null) ps.setTimestamp(4, java.sql.Timestamp.valueOf(t.getUltimoAcceso()));
            else ps.setNull(4, java.sql.Types.TIMESTAMP);
            
            ps.executeUpdate();
            return t; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CuentaUsuario update(CuentaUsuario t) {
        String sql = "UPDATE CuentaUsuario SET passwordHash = ?, rol = ?, ultimoAcceso = ? WHERE username = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getPasswordHash());
            ps.setString(2, t.getRol() != null ? t.getRol().name() : null);
            if (t.getUltimoAcceso() != null) ps.setTimestamp(3, java.sql.Timestamp.valueOf(t.getUltimoAcceso()));
            else ps.setNull(3, java.sql.Types.TIMESTAMP);
            ps.setString(4, t.getUsername());
            
            ps.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(CuentaUsuario t) {
        // CuentaUsuario no tiene campo 'desactivado' en el modelo.
        // Si no se puede hacer soft-delete, se bloquea la operacion para evitar hard-delete.
        throw new UnsupportedOperationException("Error: La entidad CuentaUsuario no permite eliminacion (No posee campo de desactivacion logica).");
    }
}
