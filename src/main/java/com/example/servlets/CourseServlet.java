package com.example.servlets;

import com.example.models.Course;
import com.example.services.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        super.init();
        courseService = new CourseService(); // Инициализация сервиса для работы с курсами
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
            case "add":
                checkTeacherRole(request, response); // Проверить роль преподавателя перед добавлением курса
                showAddCourseForm(request, response); // Показать форму добавления курса
                break;
            case "edit":
                checkTeacherRole(request, response); // Проверить роль преподавателя перед редактированием
                showEditCourseForm(request, response); // Показать форму редактирования курса
                break;
            case "view":
                viewCourse(request, response); // Просмотр конкретного курса
                break;
            case "delete":
                checkTeacherRole(request, response); // Проверить роль преподавателя перед удалением
                deleteCourse(request, response); // Удаление курса
                break;
            default:
                listCourses(request, response); // По умолчанию — показать список курсов
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                checkTeacherRole(request, response); // Проверить роль преподавателя перед добавлением курса
                addCourse(request, response); // Добавление нового курса
                break;
            case "update":
                checkTeacherRole(request, response); // Проверить роль преподавателя перед обновлением курса
                updateCourse(request, response); // Обновление существующего курса
                break;
            default:
                listCourses(request, response); // Если действие неизвестно, показать список курсов
                break;
        }
    }

    // Метод для проверки роли преподавателя
    private void checkTeacherRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("role");

        // Если пользователь не преподаватель, перенаправляем на список курсов с ошибкой
        if (userRole == null || !userRole.equals("teacher")) {
            response.sendRedirect("course?action=list&error=access_denied");
            return;
        }
    }

    // Метод для показа списка курсов
    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> courses = courseService.getAllCourses(); // Получаем список курсов
        request.setAttribute("courses", courses); // Устанавливаем атрибут списка курсов

        // Проверяем наличие ошибки при доступе
        String error = request.getParameter("error");
        if (error != null && error.equals("access_denied")) {
            request.setAttribute("errorMessage", "You do not have permission to perform this action.");
        }

        // Переход на страницу списка курсов
        request.getRequestDispatcher("jsp/courseList.jsp").forward(request, response); // Используем отдельную JSP для списка
    }


    // Метод для показа формы добавления курса (доступно только преподавателю)
    private void showAddCourseForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/addCourse.jsp").forward(request, response);
    }

    // Метод для добавления нового курса (доступно только преподавателю)
    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);

        courseService.addCourse(course); // Добавляем курс через CourseService
        response.sendRedirect("course?action=list"); // Перенаправляем на список курсов
    }

    // Метод для показа формы редактирования курса (доступно только преподавателю)
    private void showEditCourseForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        Course existingCourse = courseService.getCourseById(courseId);
        request.setAttribute("course", existingCourse);
        request.getRequestDispatcher("jsp/addCourse.jsp").forward(request, response); // Используем ту же форму, что и для добавления
    }

    // Метод для обновления курса (доступно только преподавателю)
    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Course course = new Course();
        course.setId(courseId);
        course.setTitle(title);
        course.setDescription(description);

        courseService.updateCourse(course); // Обновляем курс через CourseService
        response.sendRedirect("course?action=list");
    }

    // Метод для удаления курса (доступно только преподавателю)
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        courseService.deleteCourse(courseId);
        response.sendRedirect("course?action=list");
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
