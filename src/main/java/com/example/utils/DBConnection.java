package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // URL базы данных
    private static final String USER = "postgres"; // Имя пользователя базы данных
    private static final String PASSWORD = "qwerty123456"; // Пароль базы данных

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Загрузка драйвера PostgreSQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD); // Возвращаем соединение
    }
}

//package com.example.utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//
//    private static Connection connection;
//
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "qwerty123456";
//
//    private DBConnection() {
//        initDb();
//    }
//
//    private void initDb() {
//        try {
//            Class.forName("org.postgresql.Driver");
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException("Failed to initialize database connection", e);
//        }
//    }
//
//    public static synchronized Connection getConnection() {
//        if (connection == null) {
//            new DBConnection().initDb();
//        }
//        return connection;
//    }
//
//    public static void releaseConnection(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException("Failed to close database connection", e);
//            }
//        }
//    }
//}
