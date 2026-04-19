package com.shop.servlet;

import com.shop.dao.CartDAO;
import com.shop.dao.OrderDAO;
import com.shop.dao.OrderItemDAO;
import com.shop.model.Cart;
import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.User;
import com.shop.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        // Получить корзину через CartDAO.findByUserId()
        List<Cart> cartItems = cartDAO.findByUserId(user.getId());

        // Если корзина пуста — установить сообщение об ошибке в сессию, редирект на CartServlet
        if (cartItems == null || cartItems.isEmpty()) {
            session.setAttribute("errorMessage", "Корзина пуста. Нечего оформлять.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            // Отключить auto-commit у соединения
            conn.setAutoCommit(false);

            // Создать Order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setStatus(Order.STATUS_CREATED);

            // Рассчитать totalAmount
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (Cart item : cartItems) {
                totalAmount = totalAmount.add(item.getTotalPrice());
            }
            order.setTotalAmount(totalAmount);

            // Вызвать OrderDAO.save() для получения orderId
            int orderId = orderDAO.save(order);
            if (orderId == -1) {
                throw new SQLException("Не удалось создать заказ");
            }
            order.setId(orderId);

            // Для каждой позиции корзины: создать OrderItem, сохранить через OrderItemDAO.save()
            for (Cart cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setProductId(cartItem.getProductId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPriceAtTime(cartItem.getProduct().getPrice());
                orderItemDAO.save(orderItem);
            }

            // Вызвать CartDAO.clearByUserId() для очистки корзины
            cartDAO.clearByUserId(user.getId());

            // Выполнить commit
            conn.commit();
            conn.setAutoCommit(true);

            // Редирект на OrderHistoryServlet
            resp.sendRedirect(req.getContextPath() + "/orders");

        } catch (SQLException e) {
            e.printStackTrace();
            // При ошибке выполнить rollback
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(true);
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            session.setAttribute("errorMessage", "Ошибка при оформлении заказа: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
