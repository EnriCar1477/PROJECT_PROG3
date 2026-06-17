package pe.edu.pucp.kirusmile.dbmanager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionContext {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private static final ThreadLocal<Connection> proxyHolder = new ThreadLocal<>();

    public static Connection getActiveConnection() {
        return proxyHolder.get();
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null) {
            conn = DBManager.getInstance().newConnection();
            conn.setAutoCommit(false);
            connectionHolder.set(conn);

            // Proxy connection so that try-with-resources does not close the connection
            final Connection realConn = conn;
            Connection proxyConn = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("close".equals(method.getName())) {
                            // Ignore close() to keep transaction connection open
                            return null;
                        }
                        try {
                            return method.invoke(realConn, args);
                        } catch (java.lang.reflect.InvocationTargetException e) {
                            throw e.getCause();
                        }
                    }
                }
            );
            proxyHolder.set(proxyConn);
        }
        return proxyHolder.get();
    }

    public static void commit() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            conn.commit();
        }
    }

    public static void rollback() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error al hacer rollback: " + e.getMessage());
            }
        }
    }

    public static void close() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión de transacción: " + e.getMessage());
            }
            connectionHolder.remove();
            proxyHolder.remove();
        }
    }
}
