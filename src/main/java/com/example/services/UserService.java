package com.example.services;

import com.example.dao.UserDAO;
import com.example.models.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    // Метод аутентификации пользователя
    public User authenticateUser(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    // Метод регистрации пользователя
    public boolean registerUser(User user) {
        return userDAO.saveUser(user);
    }

    // Метод для обновления пароля пользователя
    public boolean updateUserPassword(User user) {
        return userDAO.updateUserPassword(user);
    }

    // Метод для обновления пользователя (например, обновление био и аватара)
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
