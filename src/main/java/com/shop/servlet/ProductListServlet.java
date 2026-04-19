package com.shop.servlet;

import com.shop.dao.CategoryDAO;
import com.shop.dao.ProductDAO;
import com.shop.model.Category;
import com.shop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String categoryIdParam = req.getParameter("categoryId");
            List<Category> categories = categoryDAO.findAll();

            List<Product> products;
            Integer selectedCategoryId = null;

            if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                selectedCategoryId = Integer.parseInt(categoryIdParam);
                products = productDAO.findByCategory(selectedCategoryId);
            } else {
                products = productDAO.findAll();
            }

            req.setAttribute("categories", categories);
            req.setAttribute("products", products);
            req.setAttribute("selectedCategoryId", selectedCategoryId);
            req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка загрузки товаров");
        }
    }
}