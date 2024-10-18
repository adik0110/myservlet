package com.example.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String avatar; // URL или путь к файлу аватара
    private String bio; // Описание пользователя
    private String role; // Роль: студент или преподаватель

    // Конструктор без параметров (по умолчанию)
    public User() {}

    // Конструктор со всеми параметрами
    public User(int id, String username, String password, String email, String avatar, String bio, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.bio = bio;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
