<%-- src/main/webapp/WEB-INF/views/orderDetail.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Детали заказа №${order.id}</title>
    <style>
        .order-info {
            background: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border: 1px solid #ddd;
        }
        .order-info p {
            margin: 8px 0;
        }
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .items-table th, .items-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .items-table th {
            background-color: #f5f5f5;
        }
        .total-row {
            font-weight: bold;
            background-color: #f9f9f9;
        }
        .status-CREATED { color: #ffc107; font-weight: bold; }
        .status-CONFIRMED { color: #17a2b8; font-weight: bold; }
        .status-SHIPPED { color: #007bff; font-weight: bold; }
        .status-DELIVERED { color: #28a745; font-weight: bold; }
        .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 8px 16px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .btn-back:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Детали заказа №${order.id}</h1>

<div class="order-info">
    <p><strong>Номер заказа:</strong> ${order.id}</p>
    <p><strong>Дата:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd.MM.yyyy HH:mm:ss"/></p>
    <p><strong>Статус:</strong> <span class="status-${order.status}">${order.status}</span></p>
    <p><strong>Общая сумма:</strong> <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="false"/> ₽</p>
</div>

<h2>Товары в заказе</h2>

<table class="items-table">
    <thead>
        <tr>
            <th>Товар</th>
            <th>Количество</th>
            <th>Цена на момент заказа</th>
            <th>Сумма</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${orderItems}">
            <tr>
                <td>${item.product.name}</td>
                <td>${item.quantity}</td>
                <td><fmt:formatNumber value="${item.priceAtTime}" type="number" groupingUsed="false"/> ₽</td>
                <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="false"/> ₽</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<a href="${pageContext.request.contextPath}/orders" class="btn-back">← Назад к истории заказов</a>

<jsp:include page="footer.jsp"/>

</body>
</html>