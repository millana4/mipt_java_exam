<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
    <h2>Регистрация</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
        <p style="color: red;"><%= error %></p>
    <% } %>

    <form action="register" method="post">
        <label>Логин:</label>
        <input type="text" name="login" required><br><br>

        <label>Пароль:</label>
        <input type="password" name="password" required><br><br>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <p>Уже есть аккаунт? <a href="login">Войти</a></p>
</body>
</html>