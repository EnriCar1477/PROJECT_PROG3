package pe.edu.pucp.kirusmile.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = "";
    private static final String USER = "admin";
    private static final String PASS = "Meditrack123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
}
