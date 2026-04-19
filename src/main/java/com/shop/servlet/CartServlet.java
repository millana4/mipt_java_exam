package com.shop.servlet;

import com.shop.dao.CartDAO;
import com.shop.model.Cart;
import com.shop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Получить userId из сессии
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

        // Вызвать CartDAO.findByUserId()
        List<Cart> cartItems = cartDAO.findByUserId(user.getId());

        // Рассчитать итоговую сумму (сумма quantity * price для каждой позиции)
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            totalAmount = totalAmount.add(item.getTotalPrice());
        }

        // Передать список корзины и итоговую сумму в JSP
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalAmount", totalAmount);

        // Forward на cart.jsp
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
