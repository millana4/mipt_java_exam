<%-- src/main/webapp/WEB-INF/views/productDetail.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${product.name} - Детали товара</title>
    <style>
        .product-detail {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background: #f9f9f9;
        }
        .product-detail h2 {
            margin-top: 0;
            color: #333;
        }
        .product-description {
            margin: 15px 0;
            line-height: 1.5;
            color: #555;
        }
        .product-price {
            font-size: 1.5em;
            font-weight: bold;
            color: #b12704;
            margin: 15px 0;
        }
        .quantity-input {
            width: 60px;
            padding: 5px;
            margin-right: 10px;
        }
        .btn-add {
            background: #28a745;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-add:hover {
            background: #218838;
        }
        .btn-back {
            display: inline-block;
            margin-top: 20px;
            color: #007bff;
            text-decoration: none;
        }
        .btn-back:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<%-- Подключить header.jsp --%>
<jsp:include page="header.jsp"/>

<div class="product-detail">
    <h2>${product.name}</h2>

    <div class="product-description">
        <strong>Описание:</strong><br>
        ${product.description}
    </div>

    <div class="product-price">
        Цена: ${product.price} ₽
    </div>

    <%-- Форма с полем quantity (количество) и кнопкой "Добавить в корзину" --%>
    <form action="${pageContext.request.contextPath}/cart/add" method="post">
        <input type="hidden" name="productId" value="${product.id}">
        <label for="quantity">Количество:</label>
        <input type="number" id="quantity" name="quantity" value="1" min="1" class="quantity-input">
        <button type="submit" class="btn-add">Добавить в корзину</button>
    </form>

    <a href="${pageContext.request.contextPath}/products" class="btn-back">← Назад к каталогу</a>
</div>

<%-- Подключить footer.jsp --%>
<jsp:include page="footer.jsp"/>

</body>
</html>