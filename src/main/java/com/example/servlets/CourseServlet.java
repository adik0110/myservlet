package com.example.servlets;


import com.example.models.User;
import com.example.models.Course;
import com.example.services.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() {
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCourses(request, response); // Показать список курсов
                break;
            case "view":
                viewCourse(request, response); // Просмотр конкретного курса
                break;
            default:
                listCourses(request, response); // По умолчанию — показать список курсов
                break;
        }
    }

    // Метод для показа списка курсов
    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> courses = courseService.getAllCourses(); // Получаем список курсов
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user"); // Получаем роль пользователя
        String userRole = user.getRole();

        request.setAttribute("userRole", userRole);
        request.setAttribute("courses", courses); // Устанавливаем атрибут списка курсов

        // Проверяем наличие ошибки при доступе
        String error = request.getParameter("error");
        if (error != null && error.equals("access_denied")) {
            request.setAttribute("errorMessage", "You do not have permission to perform this action.");
        }

        // Переход на страницу списка курсов
        request.getRequestDispatcher("jsp/courseList.jsp").forward(request, response); // Используем отдельную JSP для списка
    }

    // Метод для просмотра конкретного курса
    private void viewCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);

        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("role"); // Получаем роль пользователя

        request.setAttribute("course", course);
        request.setAttribute("userRole", userRole); // Передаем роль на JSP для проверки прав на редактирование
        request.getRequestDispatcher("jsp/course.jsp").forward(request, response); // Переходим на страницу курса
    }
}
