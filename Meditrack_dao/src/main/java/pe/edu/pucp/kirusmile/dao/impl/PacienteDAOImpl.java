package pe.edu.pucp.kirusmile.dao.impl;

import pe.edu.pucp.kirusmile.dao.inter.PacienteDAO;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;
import pe.edu.pucp.kirusmile.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOImpl implements PacienteDAO {

    private Connection con;

    public PacienteDAOImpl() {
        this.con = DBManager.getInstance().getConnection();
    }


    @Override
    public int save(Paciente objeto) {
        int resultado = 0;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false); // Iniciamos transacción para que ambos se inserten o ninguno

            // Sentencia para el PADRE (Persona)
            String sqlPersona = "INSERT INTO Persona (dni, nombres, apellido_paterno, apellido_materno, " +
                    "fecha_nacimiento, telefono, correo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, 1)";


            // PASO 1: Insertar en Persona
            try (PreparedStatement pstP = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());
                pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());

                pstP.executeUpdate();

                // Recuperamos la llave del padre
                try (ResultSet rs = pstP.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idPersonaGenerada = rs.getInt(1);
                        objeto.setIdPersona(idPersonaGenerada);
//                        objeto.setIdPaciente(idPersonaGenerada); // Comparten el mismo ID
                    }
                }
            }


            // Sentencia para el HIJO (Paciente) - Usamos id_paciente como la FK que recibimos del padre
            String sqlPaciente = "INSERT INTO Paciente (fid_persona, grupo_sanguineo, factor_rh, " +
                    "grado_instruccion, ocupacion, etnia, activo) VALUES ( ?, ?, ?, ?, ?, ?, 1)";


            // PASO 2: Insertar en Paciente usando la llave recuperada
            try (PreparedStatement pstH = con.prepareStatement(sqlPaciente, Statement.RETURN_GENERATED_KEYS)) {
                pstH.setInt(1, objeto.getIdPersona()); // Usamos el ID devuelto por Persona
                pstH.setString(2, objeto.getGrupoSanguineo());
                pstH.setString(3, objeto.getFactorRh());
                pstH.setString(4, objeto.getGradoInstruccion());
                pstH.setString(5, objeto.getOcupacion());
                pstH.setString(6, objeto.getEtnia());

                int filasAfec = pstH.executeUpdate();

                if (filasAfec > 0) {
                    // 2. Recuperar el ID autoincremental
                    try (ResultSet rs = pstH.getGeneratedKeys()) {
                        if (rs.next()) {
                            resultado = rs.getInt(1);
                        }
                    }
                }

            }


            con.commit(); // Si todo salió bien, guardamos cambios en ambas tablas
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { } // Si algo falla, deshacemos todo
            }
            System.err.println("Error en save cascada Paciente: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int update(Paciente objeto) {
        int resultado = 0;
        // Sentencias separadas para actualizar Padre e Hijo
        String sqlPersona = "UPDATE Persona SET dni=?, nombres=?, apellido_paterno=?, " +
                "apellido_materno=?, fecha_nacimiento=?, telefono=?, correo=? " +
                "WHERE id_persona=?";

        String sqlPaciente = "UPDATE Paciente SET grupo_sanguineo=?, factor_rh=?, " +
                "grado_instruccion=?, ocupacion=?, etnia=? WHERE id_paciente=?";

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false); // Transacción: Se actualizan ambas tablas o ninguna

            // 1. Actualizamos los datos heredados en la tabla Persona
            try (PreparedStatement pstP = con.prepareStatement(sqlPersona)) {
                pstP.setString(1, objeto.getDni());
                pstP.setString(2, objeto.getNombres());
                pstP.setString(3, objeto.getApellidoPaterno());
                pstP.setString(4, objeto.getApellidoMaterno());
                pstP.setDate(5, java.sql.Date.valueOf(objeto.getFechaNacimiento()));
                pstP.setString(6, objeto.getTelefono());
                pstP.setString(7, objeto.getCorreo());
                pstP.setInt(8, objeto.getIdPersona());
                pstP.executeUpdate();
            }

            // 2. Actualizamos los datos clínicos/sociales en la tabla Paciente
            try (PreparedStatement pstH = con.prepareStatement(sqlPaciente)) {
                pstH.setString(1, objeto.getGrupoSanguineo());
                pstH.setString(2, objeto.getFactorRh());
                pstH.setString(3, objeto.getGradoInstruccion());
                pstH.setString(4, objeto.getOcupacion());
//                pstH.setString(5, objeto.getEtnia());
                pstH.setInt(6, objeto.getIdPaciente());

                resultado = pstH.executeUpdate(); // Retornamos las filas afectadas del hijo
            }

            con.commit(); // Confirmamos los cambios
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { } // Deshacemos si hay error
            }
            System.err.println("Error en update cascada de Paciente: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { }
        }
        return resultado;
    }

    @Override
    public int delete(int id) {
        int filasAfectadas = 0;
        // Detalle de diseño: Solo desactivamos el rol de "Paciente".
        // No desactivamos a la "Persona" porque ese mismo ser humano podría ser también un Empleado.
        String sql = "UPDATE Paciente SET activo = 0 WHERE id_paciente = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            filasAfectadas = pst.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error en borrado lógico de Paciente: " + e.getMessage());
        }
        return filasAfectadas;
    }

    @Override
    public Paciente load(int id) {
        Paciente paciente = null;
        // Estilo del profesor: SELECT con INNER JOIN para traer todo de una vez
        String sql = "SELECT p.id_paciente, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.id_paciente = per.id_persona " +
                "WHERE p.id_paciente = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    // Datos del Padre (Persona)
                    paciente.setIdPersona(rs.getInt("id_paciente"));
                    paciente.setDni(rs.getString("dni"));
                    paciente.setNombres(rs.getString("nombres"));
                    paciente.setApellidoPaterno(rs.getString("apellido_paterno"));
                    paciente.setApellidoMaterno(rs.getString("apellido_materno"));
                    paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setCorreo(rs.getString("correo"));

                    // Datos del Hijo (Paciente)
                    paciente.setIdPaciente(rs.getInt("id_paciente"));
                    paciente.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
                    paciente.setFactorRh(rs.getString("factor_rh"));
                    paciente.setGradoInstruccion(rs.getString("grado_instruccion"));
                    paciente.setOcupacion(rs.getString("ocupacion"));
                   //paciente.setEtnia(rs.getString("etnia"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en load Paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public List<Paciente> listALL() {
        List<Paciente> lista = new ArrayList<>();
        // Usamos INNER JOIN para traer la lista completa ensamblada
        String sql = "SELECT p.id_paciente, per.id_persona, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion, p.etnia " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.id_paciente = per.id_persona " +
                "WHERE p.activo = 1 AND per.activo = 1 ORDER BY per.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();

                // Ensamblamos el Padre
                paciente.setIdPersona(rs.getInt("id_persona"));
                paciente.setDni(rs.getString("dni"));
                paciente.setNombres(rs.getString("nombres"));
                paciente.setApellidoPaterno(rs.getString("apellido_paterno"));
                paciente.setApellidoMaterno(rs.getString("apellido_materno"));

                if (rs.getDate("fecha_nacimiento") != null) {
                    paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                }
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setCorreo(rs.getString("correo"));

                // Ensamblamos el Hijo
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
                paciente.setFactorRh(rs.getString("factor_rh"));
                paciente.setGradoInstruccion(rs.getString("grado_instruccion"));
                paciente.setOcupacion(rs.getString("ocupacion"));
                //paciente.setEtnia(rs.getString("etnia"));

                lista.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todos los Pacientes: " + e.getMessage());
        }
        return lista;
    }


    @Override
    public Paciente obtenerPorDni(String dni) {
        Paciente paciente = null;
        // Hacemos el INNER JOIN buscando específicamente por la columna DNI de la tabla Persona
        String sql = "SELECT p.id_paciente, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.id_paciente = per.id_persona " +
                "WHERE per.dni = ? AND p.activo = 1 AND per.activo = 1";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, dni);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    // Datos del Padre (Persona)
                    paciente.setIdPersona(rs.getInt("id_paciente"));
                    paciente.setDni(rs.getString("dni"));
                    paciente.setNombres(rs.getString("nombres"));
                    paciente.setApellidoPaterno(rs.getString("apellido_paterno"));
                    paciente.setApellidoMaterno(rs.getString("apellido_materno"));
                    if (rs.getDate("fecha_nacimiento") != null) {
                        paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    }
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setCorreo(rs.getString("correo"));

                    // Datos del Hijo (Paciente)
                    paciente.setIdPaciente(rs.getInt("id_paciente"));
                    paciente.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
                    paciente.setFactorRh(rs.getString("factor_rh"));
                    paciente.setGradoInstruccion(rs.getString("grado_instruccion"));
                    paciente.setOcupacion(rs.getString("ocupacion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerPorDni de Paciente: " + e.getMessage());
        }
        return paciente;
    }

    @Override
    public List<Paciente> listarPorNombre(String filtro) {
        List<Paciente> lista = new ArrayList<>();
        // El LIKE nos permite buscar coincidencias tanto en el nombre como en los apellidos
        String sql = "SELECT p.id_paciente, per.dni, per.nombres, per.apellido_paterno, " +
                "per.apellido_materno, per.fecha_nacimiento, per.telefono, per.correo, " +
                "p.grupo_sanguineo, p.factor_rh, p.grado_instruccion, p.ocupacion " +
                "FROM Paciente p " +
                "INNER JOIN Persona per ON p.id_paciente = per.id_persona " +
                "WHERE p.activo = 1 AND per.activo = 1 " +
                "AND (per.nombres LIKE ? OR per.apellido_paterno LIKE ? OR per.apellido_materno LIKE ?) " +
                "ORDER BY per.apellido_paterno ASC";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Los comodines '%' le dicen a MySQL que busque el texto en cualquier parte de la palabra
            String parametroBusqueda = "%" + filtro + "%";
            pst.setString(1, parametroBusqueda);
            pst.setString(2, parametroBusqueda);
            pst.setString(3, parametroBusqueda);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();
                    // Ensamblamos el Padre
                    paciente.setIdPersona(rs.getInt("id_paciente"));
                    paciente.setDni(rs.getString("dni"));
                    paciente.setNombres(rs.getString("nombres"));
                    paciente.setApellidoPaterno(rs.getString("apellido_paterno"));
                    paciente.setApellidoMaterno(rs.getString("apellido_materno"));
                    if (rs.getDate("fecha_nacimiento") != null) {
                        paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    }
                    paciente.setTelefono(rs.getString("telefono"));
                    paciente.setCorreo(rs.getString("correo"));

                    // Ensamblamos el Hijo
                    paciente.setIdPaciente(rs.getInt("id_paciente"));
                    paciente.setGrupoSanguineo(rs.getString("grupo_sanguineo"));
                    paciente.setFactorRh(rs.getString("factor_rh"));
                    paciente.setGradoInstruccion(rs.getString("grado_instruccion"));
                    paciente.setOcupacion(rs.getString("ocupacion"));

                    lista.add(paciente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pacientes por nombre: " + e.getMessage());
        }
        return lista;
    }
}
