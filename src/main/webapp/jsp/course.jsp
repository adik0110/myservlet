<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${course.title}</title>
</head>
<body>
<h1>${course.title}</h1>
<p>${course.description}</p>

<!-- Отображение кнопок редактирования только для преподавателя -->
<c:if test="${userRole == 'Преподаватель'}">
    <a href="course?action=edit&id=${course.id}">Edit Course</a>
    <a href="course?action=delete&id=${course.id}" onclick="return confirm('Are you sure?')">Delete Course</a>
</c:if>

<!-- Прочая информация о курсе, доступная всем пользователям -->
</body>
</html>
