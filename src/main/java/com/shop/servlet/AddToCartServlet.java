package com.shop.servlet;

import com.shop.dao.CartDAO;
import com.shop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart/add")
public class AddToCartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Получить параметры productId и quantity
        String productIdParam = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");

        if (productIdParam == null || quantityParam == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            if (quantity <= 0) {
                quantity = 1;
            }

            // Получить userId из сессии (из объекта User)
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            User user = (User) session.getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            // Вызвать CartDAO.addOrUpdate()
            cartDAO.addOrUpdate(user.getId(), productId, quantity);

            // Редирект обратно на страницу, откуда пришёл пользователь
            String referer = req.getHeader("Referer");
            if (referer == null || referer.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/products");
            } else {
                resp.sendRedirect(referer);
            }

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/products");
        }
    }
}