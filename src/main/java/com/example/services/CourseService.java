package com.example.services;

import com.example.dao.CourseDAO;
import com.example.models.Course;

import java.util.List;

public class CourseService {

    private CourseDAO courseDAO;

    // Конструктор, инициализирующий DAO
    public CourseService() {
        courseDAO = new CourseDAO();
    }

    // Метод для добавления нового курса
    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }

    // Метод для обновления курса
    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    // Метод для удаления курса
    public void deleteCourse(int courseId) {
        courseDAO.deleteCourse(courseId);
    }

    // Метод для получения курса по ID
    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    // Метод для получения списка всех курсов
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}
