<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
    <link rel="stylesheet" href="/static/css/profile.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="profile-container">
    <h1>Profile</h1>
    <c:choose>
        <c:when test="${not empty user.avatar}">
            <img src="/static/avatars/${user.avatar}" alt="Avatar" class="profile-avatar">
        </c:when>
        <c:otherwise>
            <img src="/static/avatars/default_avatar.jpg" alt="Default Avatar" class="profile-avatar">
        </c:otherwise>
    </c:choose>
    <p>Имя: ${user.name}</p>
    <p>Почта: ${user.email}</p>
    <p>Биография: ${user.bio}</p>
    <p>Роль: ${user.role}</p>

    <form action="logout" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="logout-button">Logout</button>
    </form>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>