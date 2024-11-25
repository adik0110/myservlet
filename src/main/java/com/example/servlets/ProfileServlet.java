package com.example.servlets;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Пользователь найден в сессии, показываем профиль
            User user = (User) session.getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
        } else {
            // Если пользователь не авторизован, перенаправляем на страницу логина
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
