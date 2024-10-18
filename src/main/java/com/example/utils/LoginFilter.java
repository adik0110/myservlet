package com.example.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Получаем сессию и проверяем, авторизован ли пользователь
        HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login.jsp"; // Путь к странице логина

        // Получаем URI текущего запроса
        String requestURI = httpRequest.getRequestURI();

        // Если пользователь не авторизован и запрашивает защищенную страницу
        if (!loggedIn && !requestURI.equals(loginURI) && !requestURI.endsWith("register.jsp")) {
            // Перенаправляем на страницу логина
            httpResponse.sendRedirect(loginURI);
        } else {
            // Если пользователь авторизован или запрашивает страницу логина/регистрации, продолжаем фильтрацию
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
