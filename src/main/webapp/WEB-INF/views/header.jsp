<%-- src/main/webapp/WEB-INF/views/header.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        .header {
            background-color: #333;
            color: white;
            padding: 15px;
            text-align: center;
        }
        .nav {
            background-color: #f4f4f4;
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        .nav a {
            margin: 0 15px;
            text-decoration: none;
            color: #333;
            font-weight: bold;
        }
        .nav a:hover {
            color: #007bff;
        }
        .user-info {
            float: right;
            margin-right: 20px;
            color: #333;
        }
    </style>
</head>
<body>
<div class="header">
    <h1>Интернет-магазин ЦК-JAVA</h1>
</div>
<div class="nav">
    <a href="${pageContext.request.contextPath}/products">Каталог</a>
    <a href="${pageContext.request.contextPath}/cart">Корзина</a>

    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/login">Вход</a>
            <a href="${pageContext.request.contextPath}/register">Регистрация</a>
        </c:when>
        <c:otherwise>
            <span class="user-info">Привет, ${sessionScope.user.login}!</span>
            <a href="${pageContext.request.contextPath}/orders">Мои заказы</a>
            <a href="${pageContext.request.contextPath}/logout">Выход</a>
        </c:otherwise>
    </c:choose>
</div>