package com.shop.servlet;

import com.shop.dao.OrderDAO;
import com.shop.dao.OrderItemDAO;
import com.shop.model.Order;
import com.shop.model.OrderItem;
import com.shop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/detail")
public class OrderDetailServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Получить параметр orderId
        String orderIdParam = req.getParameter("orderId");

        if (orderIdParam == null || orderIdParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }

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

        try {
            int orderId = Integer.parseInt(orderIdParam);

            // Вызвать OrderDAO.findById()
            Order order = orderDAO.findById(orderId);

            // Проверить, что заказ принадлежит текущему пользователю
            if (order == null || order.getUserId() != user.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "У вас нет доступа к этому заказу");
                return;
            }

            // Вызвать OrderItemDAO.findByOrderId() для получения товаров в заказе
            List<OrderItem> orderItems = orderItemDAO.findByOrderId(orderId);

            // Передать order и orderItems в JSP
            req.setAttribute("order", order);
            req.setAttribute("orderItems", orderItems);

            // Forward на orderDetail.jsp
            req.getRequestDispatcher("/WEB-INF/views/orderDetail.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
        }
    }
}
