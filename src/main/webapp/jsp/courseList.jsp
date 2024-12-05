<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Available Courses</title>
    <!-- Подключаем файл стилей -->
    <link rel="stylesheet" href="/static/css/courseList.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />

<h2>Available Courses</h2>

<!-- Отображение ошибки, если она есть -->
<c:if test="${not empty errorMessage}">
    <p class="error-message">${errorMessage}</p>
</c:if>

<!-- Таблица курсов -->
<div class="course-table">
    <c:forEach var="course" items="${courses}">
        <div class="course-card">
            <h2>${course.title}</h2>
            <p>${course.description}</p>
            <p><strong>Предмет:</strong> ${course.subjectName}</p> <!-- Название предмета -->
            <p><strong>Преподаватель:</strong> ${course.teacherName}</p> <!-- Имя преподавателя -->
            <a href="course?action=view&id=${course.id}" class="btn-view">Подробнее</a>
        </div>
    </c:forEach>
</div>

<!-- Кнопка добавления нового курса для преподавателя -->
<c:if test="${userRole == 'Преподаватель'}">
    <a href="course?action=add" class="add-course-btn">Add New Course</a>
</c:if>

<jsp:include page="footer.jsp" />

</body>
</html>
