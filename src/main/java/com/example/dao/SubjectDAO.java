package com.example.dao;

import com.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDAO {

    // Метод для получения названия предмета по его ID
    public String getSubjectNameById(int subjectId) {
        String subjectName = null;
        String sql = "SELECT name FROM subjects WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                subjectName = rs.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectName;
    }
}
