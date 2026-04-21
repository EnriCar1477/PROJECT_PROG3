package pe.edu.pucp.kirusmile.dao.impl;

public class PacienteDAOImpl {
   @Override
    public Paciente load(String dni,String nombres,String apellidoPaterno, String apellidoMaterno,Date fechaNacimiento,String telefono,String correo,
					String estado, boolean tieneSeguro) {
        String sql = "select id_cita, activa from area where id = ?";
        try(Connection connection = Meditrack_DBManager.getInstance().getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            pstmt.setString(2, nombres);
            pstmt.setString(3, apellidoPaterno);
            pstmt.setString(4, apellidoMaterno);
            pstmt.setDate(5, fechaNacimiento);
            pstmt.setString(6, telefono);
            pstmt.setString(7, correo);
            pstmt.setString(8, estado);
            pstmt.setBoolean(9, tieneSeguro);

            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(1,2,3,4,5,6,7,8,9);
                    return paciente;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Paciente save(Paciente paciente) {
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
