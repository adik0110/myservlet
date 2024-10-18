package com.example.dao;

import com.example.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.utils.DBConnection;

public class UserDAO {

    // Метод для получения пользователя по email и паролю
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("username"), password, email, rs.getString("avatar"), rs.getString("bio"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Метод для сохранения нового пользователя
    public boolean saveUser(User user) {
        if (isEmailRegistered(user.getEmail())) {
            return false;
        }
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Проверка, зарегистрирован ли email
    public boolean isEmailRegistered(String email) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Если возвращено больше 0 записей, email уже существует
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Метод для обновления пароля пользователя
    public boolean updateUserPassword(User user) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail()); // Предполагаем, что у пользователя уникальный email

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Метод для обновления данных пользователя (например, био и аватара)
    public void updateUser(User user) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE users SET bio = ?, avatar = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getBio());
            stmt.setString(2, user.getAvatar());
            stmt.setString(3, user.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
