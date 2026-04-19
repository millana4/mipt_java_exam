<%-- src/main/webapp/WEB-INF/views/products.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Каталог товаров</title>
    <style>
        .category-filter {
            margin: 20px 0;
            padding: 10px;
            background: #f5f5f5;
            border-radius: 5px;
        }
        .category-filter a {
            margin: 0 10px;
            text-decoration: none;
            color: #333;
        }
        .category-filter a.active {
            font-weight: bold;
            color: #007bff;
        }
        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .product-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
        }
        .product-price {
            font-size: 1.2em;
            color: #b12704;
            margin: 10px 0;
        }
        .btn {
            display: inline-block;
            margin: 5px;
            padding: 8px 12px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-cart {
            background: #28a745;
        }
        .btn-cart:hover {
            background: #218838;
        }
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<%-- Подключить header.jsp --%>
<jsp:include page="header.jsp"/>

<h1>Каталог товаров</h1>

<%-- Отобразить список категорий в виде ссылок --%>
<div class="category-filter">
    <strong>Категории:</strong>
    <a href="${pageContext.request.contextPath}/products"
       class="${empty selectedCategoryId ? 'active' : ''}">Все</a>

    <c:forEach var="category" items="${categories}">
        <a href="${pageContext.request.contextPath}/products?categoryId=${category.id}"
           class="${selectedCategoryId == category.id ? 'active' : ''}">
            ${category.name}
        </a>
    </c:forEach>
</div>

<%-- Отобразить список товаров --%>
<c:choose>
    <c:when test="${empty products}">
        <p>Товары не найдены.</p>
    </c:when>
    <c:otherwise>
        <div class="products-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <h3>${product.name}</h3>
                    <p>${product.description}</p>
                    <div class="product-price">${product.price} ₽</div>

                    <%-- Кнопка "Подробнее" (ссылка на ProductDetailServlet с id товара) --%>
                    <a href="${pageContext.request.contextPath}/product?id=${product.id}"
                       class="btn">Подробнее</a>

                    <%-- Кнопка "В корзину" (форма на AddToCartServlet) --%>
                    <form action="${pageContext.request.contextPath}/cart/add"
                          method="post" style="display: inline;">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit" class="btn btn-cart">В корзину</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<%-- Подключить footer.jsp --%>
<jsp:include page="footer.jsp"/>

</body>
</html>