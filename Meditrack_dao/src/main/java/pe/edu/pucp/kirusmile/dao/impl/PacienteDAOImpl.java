package pe.edu.pucp.kirusmile.dao.impl;

public class CiaDAOImpl {
   @Override
    public Cita load(Integer id) {
        String sql = "select id_area, nombre, activa from area where id = ?";
        try(Connection connection = Meditrack_DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Area area = new Area();
                    area.setId(rs.getInt(1));
                    area.setName(rs.getString(2));
                    area.setActive(rs.getBoolean(3));
                    return area;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Cita save(Area area) {
        area.setActive(true);
        String sql = "insert into area (nombre, activa) values (?, ?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, area.getName());
            pstmt.setBoolean(2, area.getActive());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        area.setId(newId);
                    }
                }
            }
            return area;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cita update(Area area) {
        String sql = "update area set nombre = ?, activa = ? where id = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, area.getName());
            pstmt.setBoolean(2, area.getActive());
            pstmt.setInt(3, area.getId());
            pstmt.executeUpdate();
            return area;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Cita cita) {
        // TODO: please implement logical removal
        area.setActive(false);
        String sql = "update area set activa = ? where id_area = ?";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, area.getActive());
            pstmt.setInt(2, area.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
}
