package com.example.servlets;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        if ("Преподаватель".equals(role)) {
            String teacherCode = req.getParameter("teacherCode");
            if (!"prepod".equals(teacherCode)) {
                req.setAttribute("errorMessage", "Неверный код преподавателя.");
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
                return;
            }
        }

        // Создаем нового пользователя
        User newUser = new User(0, username, email, null, null, password, role);
        if (userService.registerUser(newUser)) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("errorMessage", "Registration failed. Email already in use.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        }


    }
}
