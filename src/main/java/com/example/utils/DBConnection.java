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