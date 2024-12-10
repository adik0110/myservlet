package com.example.servlets;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request to /profile");

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            logger.info("User accessed profile: {}", user.getEmail());

            req.setAttribute("user", user);
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
        } else {
            logger.warn("Unauthorized access attempt to /profile");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
