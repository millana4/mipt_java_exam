<%-- src/main/webapp/WEB-INF/views/cart.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Корзина</title>
    <style>
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .cart-table th, .cart-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        .cart-table th {
            background-color: #f5f5f5;
        }
        .total-row {
            font-weight: bold;
            background-color: #f9f9f9;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-danger {
            background: #dc3545;
        }
        .btn-danger:hover {
            background: #c82333;
        }
        .btn-success {
            background: #28a745;
        }
        .btn-success:hover {
            background: #218838;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .empty-cart {
            text-align: center;
            padding: 40px;
            font-size: 1.2em;
            color: #666;
        }
        .checkout-section {
            text-align: right;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Корзина</h1>

<c:choose>
    <c:when test="${empty cartItems}">
        <div class="empty-cart">
            <p>Ваша корзина пуста</p>
            <a href="${pageContext.request.contextPath}/products" class="btn">Перейти к товарам</a>
        </div>
    </c:when>
    <c:otherwise>
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Товар</th>
                    <th>Цена</th>
                    <th>Количество</th>
                    <th>Сумма</th>
                    <th>Действие</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td>${item.product.name}</td>
                        <td><fmt:formatNumber value="${item.product.price}" type="number" groupingUsed="false"/> ₽</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="false"/> ₽</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/cart/remove" method="post" style="display: inline;">
                                <input type="hidden" name="cartItemId" value="${item.id}">
                                <button type="submit" class="btn btn-danger">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr class="total-row">
                    <td colspan="3"><strong>Итого:</strong></td>
                    <td colspan="2"><strong><fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="false"/> ₽</strong></td>
                </tr>
            </tbody>
        </table>

        <div class="checkout-section">
            <form action="${pageContext.request.contextPath}/checkout" method="post" style="display: inline;">
                <button type="submit" class="btn btn-success">Оформить заказ</button>
            </form>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="footer.jsp"/>

</body>
</html>