<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css?">
</head>
<body>
<div class="profile-container">
    <h1>Profile</h1>
    <img src="${pageContext.request.contextPath}/avatars/${user.avatar}" alt="Avatar" class="profile-avatar">    <p>Имя: ${user.name}</p>
    <p>Почта: ${user.email}</p>
    <p>Биография: ${user.bio}</p>
    <p>Роль: ${user.role}</p>

    <form action="logout" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="logout-button">Logout</button>
    </form>
</div>
</body>
</html>
