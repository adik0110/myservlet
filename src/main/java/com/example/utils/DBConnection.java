package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DBConnection {

    private static DBConnection instance;

    private Stack<Connection> connections;

    private Set<Connection> usedConnections;

    private static final int MAX_CONNECTIONS = 5;

    private DBConnection() {
        connections = new Stack<>();
        usedConnections = new HashSet<>();

        try {
            Class.forName("org.postgresql.Driver");

            for (int i = 0; i < MAX_CONNECTIONS; ++i) {
                Connection connection =
                        DriverManager.getConnection(
                                // адрес БД , имя пользователя, пароль
                                "jdbc:postgresql://localhost:5432/postgres", "postgres", "qwerty123456");
                connections.push(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = connections.pop();
            usedConnections.add(connection);
        } catch (EmptyStackException e) {
            connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/postgres", "postgres", "qwerty123456");
        }
        return connection;
    }

    public void destroy() {
        for (Connection connection : usedConnections ) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}