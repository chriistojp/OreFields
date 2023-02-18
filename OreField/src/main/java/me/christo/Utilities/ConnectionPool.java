package me.christo.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private final int initialPoolSize;

    public ConnectionPool(String url, String user, String password, int initialPoolSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.initialPoolSize = initialPoolSize;
        connectionPool = new ArrayList<>(initialPoolSize);
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            for (int i = 0; i < initialPoolSize; i++) {
                connectionPool.add(createConnection());
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized boolean releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.add(connection);
            return usedConnections.remove(connection);
        }
        return false;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}



