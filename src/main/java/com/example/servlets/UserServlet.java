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

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(); // Инициализация UserService для работы с пользователями
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("profile".equals(action)) {
            handleProfile(req, resp);
        } else if ("logout".equals(action)) {
            handleLogout(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp"); // Перенаправление на страницу авторизации
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(req, resp);
        } else if ("register".equals(action)) {
            handleRegister(req, resp);
        } else if ("logout".equals(action)) {
            handleLogout(req, resp); // Добавляем обработку logout в doPost
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Аутентификация пользователя
        User user = userService.authenticateUser(email, password);

        if (user != null) {
            // Сохраняем пользователя в сессии
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/user?action=profile"); // Перенаправляем на профиль
        } else {
            // Ошибка аутентификации
            req.setAttribute("errorMessage", "Invalid email or password");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Handling registration...");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Если пользователь выбрал роль "Преподаватель", проверяем введенный код
        if ("Преподаватель".equals(role)) {
            String teacherCode = req.getParameter("teacherCode");
            if (!"prepod".equals(teacherCode)) {
                // Если код неверный, показываем сообщение об ошибке
                req.setAttribute("errorMessage", "Неверный код преподавателя.");
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
                return;
            }
        }

        // Создаем нового пользователя
        User newUser = new User(0, username, password, email, null, null, role);

        if (userService.registerUser(newUser)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp"); // Перенаправление на логин
        } else {
            req.setAttribute("errorMessage", "Registration failed. Email already in use.");
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
        }
    }


    private void handleProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Пользователь найден в сессии, показываем профиль
            User user = (User) session.getAttribute("user");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
        } else {
            // Если пользователь не авторизован, перенаправляем на страницу логина
            resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Завершаем сессию
        }
        resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
    }
}