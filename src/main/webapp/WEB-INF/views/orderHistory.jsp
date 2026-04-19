<%-- src/main/webapp/WEB-INF/views/orderHistory.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>История заказов</title>
    <style>
        .orders-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .orders-table th, .orders-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .orders-table th {
            background-color: #f5f5f5;
        }
        .status-CREATED { color: #ffc107; font-weight: bold; }
        .status-CONFIRMED { color: #17a2b8; font-weight: bold; }
        .status-SHIPPED { color: #007bff; font-weight: bold; }
        .status-DELIVERED { color: #28a745; font-weight: bold; }
        .btn {
            display: inline-block;
            padding: 5px 10px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .empty-orders {
            text-align: center;
            padding: 40px;
            font-size: 1.2em;
            color: #666;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1>История заказов</h1>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-orders">
            <p>У вас пока нет заказов</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Перейти к товарам</a>
        </div>
    </c:when>
    <c:otherwise>
        <table class="orders-table">
            <thead>
                <tr>
                    <th>№ заказа</th>
                    <th>Дата</th>
                    <th>Статус</th>
                    <th>Сумма</th>
                    <th>Детали</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>
                        <td><fmt:formatDate value="${order.orderDate}" pattern="dd.MM.yyyy HH:mm"/></td>
                        <td class="status-${order.status}">${order.status}</td>
                        <td><fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="false"/> ₽</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/order/detail?orderId=${order.id}" class="btn">Детали</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp"/>

</body>
</html>