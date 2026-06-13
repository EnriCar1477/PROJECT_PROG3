<<<<<<< HEAD
package pe.edu.pucp.kirusmile.dbmanager;

import pe.edu.pucp.kirusmile.dbmanager.util.Cifrado;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager instance;
    private Properties properties;
    private final String url;
    private final String user;
    private final String password;
    private final String DB_CREDENTIALS_FILE = "db.properties";

    private DBManager() {

        // CORRECCIÓN 1: Registrar el Driver explícitamente
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error: No se encontró el Driver MySQL en el classpath.", e);
        }

        properties = new Properties();
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_CREDENTIALS_FILE);
            if (inputStream == null) {
                throw new IOException("No se pudo encontrar el archivo: " + DB_CREDENTIALS_FILE);
            }
            properties.load(inputStream);
        }catch(IOException ex){
            System.out.println("Error when loading properties file: " + ex.getMessage());
        }
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");
        //CAMBIO EN URL
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC&useSSL=false";
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

    public static DBManager getInstance(){
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, Cifrado.descifrar(password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
=======
package pe.edu.pucp.kirusmile.dbmanager;

import pe.edu.pucp.kirusmile.dbmanager.util.Cifrado;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager instance;
    private Properties properties;
    private final String url;
    private final String user;
    private final String password;
    private final String DB_CREDENTIALS_FILE = "db.properties";

    private DBManager() {
        properties = new Properties();
        try{
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_CREDENTIALS_FILE);
            properties.load(inputStream);
        }catch(IOException ex){
            System.out.println("Error when loading properties file: " + ex.getMessage());
        }
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

    public static DBManager getInstance(){
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, Cifrado.descifrar(password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
>>>>>>> 17c12cc8a68643e5f7a0f04e500cd95058c63fb7
