package com.example.servlets;


import com.example.models.Enrollment;
import com.example.models.User;
import com.example.models.Course;
import com.example.services.CourseService;

import com.example.services.EnrollmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;
    private EnrollmentService enrollmentService;

    @Override
    public void init() {
        courseService = new CourseService();
        enrollmentService = new EnrollmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCourses(request, response);
                break;
            case "view":
                viewCourse(request, response);
                break;
            case "work":
//                workCourse(request, response);
                break;
            case "view-my-courses":
                viewMyCourses(request, response);
                break;
        }
    }

    // Метод для показа списка курсов
    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Получаем пользователя из сессии
        User user = (User) session.getAttribute("user");
        String userRole = null;
        if (user != null) {
            userRole = user.getRole();
        }

        // Получаем все курсы
        List<Course> allCourses = courseService.getAllCourses();

        // Если пользователь авторизован, получаем список курсов, на которые он уже подписан
        List<Course> filteredCourses = new ArrayList<>(allCourses);
        if (user != null) {
            try {
                // Получаем список подписок пользователя
                List<Enrollment> userEnrollments = enrollmentService.getEnrollmentsByStudentId(user.getId());

                // Создаем множество ID курсов, на которые пользователь уже подписан
                Set<Integer> enrolledCourseIds = userEnrollments.stream()
                        .map(Enrollment::getCourseId)
                        .collect(Collectors.toSet());

                // Фильтруем курсы, исключая те, на которые пользователь уже подписан
                filteredCourses = allCourses.stream()
                        .filter(course -> !enrolledCourseIds.contains(course.getId()))
                        .collect(Collectors.toList());

            } catch (SQLException e) {
                throw new ServletException("Failed to retrieve user enrollments", e);
            }
        }

        // Передаем роль пользователя и отфильтрованный список курсов в JSP
        request.setAttribute("userRole", userRole);
        request.setAttribute("courses", filteredCourses);

        // Перенаправляем запрос на JSP
        request.getRequestDispatcher("jsp/courseList.jsp").forward(request, response);
    }

    private void viewCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userRole = user.getRole();

        request.setAttribute("course", course);
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("userRole", userRole);
        request.getRequestDispatcher("jsp/course.jsp").forward(request, response);
    }

    private void viewMyCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Проверяем, авторизован ли пользователь
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Получаем пользователя из сессии
        User user = (User) session.getAttribute("user");

        try {
            // Получаем все подписки пользователя
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(user.getId());

            // Получаем информацию о курсах, на которые подписан пользователь
            List<Course> myCourses = new ArrayList<>();
            for (Enrollment enrollment : enrollments) {
                Course course = courseService.getCourseById(enrollment.getCourseId());
                if (course != null) {
                    myCourses.add(course);
                }
            }

            // Передаем список курсов в JSP
            request.setAttribute("myCourses", myCourses);
            request.getRequestDispatcher("/jsp/myCourses.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve user's courses", e);
        }
    }
}
