<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
    <h2>Вход в аккаунт</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p style="color: red;"><%= error %></p>
    <% } %>

    <form action="login" method="post">
        <label>Логин:</label>
        <input type="text" name="login" required><br><br>

        <label>Пароль:</label>
        <input type="password" name="password" required><br><br>

        <button type="submit">Войти</button>
    </form>

    <p>Нет аккаунта? <a href="register">Зарегистрироваться</a></p>
</body>
</html>
