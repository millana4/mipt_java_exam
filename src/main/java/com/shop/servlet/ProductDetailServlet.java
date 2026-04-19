package com.shop.servlet;

import com.shop.dao.ProductDAO;
import com.shop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {  // ← ДОБАВИТЬ extends HttpServlet

    private ProductDAO productDAO = new ProductDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Получаю ID из запроса
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            // Если id нет, то редирект на список товаров
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);
            Product product = productDAO.findById(productId);

            if (product != null) {
                // Товар найден - установить атрибут запроса
                req.setAttribute("product", product);
                // Передать на JSP
                req.getRequestDispatcher("/WEB-INF/views/productDetail.jsp").forward(req, resp);
            } else {
                // Товар не найден
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Товар не найден");
            }
        } catch (NumberFormatException e) {
            // Неверный формат id — редирект на список товаров
            resp.sendRedirect(req.getContextPath() + "/products");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка загрузки товара");
        }
    }
}
