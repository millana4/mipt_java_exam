package com.shop.servlet;

import com.shop.dao.OrderDAO;
import com.shop.model.Order;
import com.shop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderHistoryServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

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

        // Вызвать OrderDAO.findByUserId()
        List<Order> orders = orderDAO.findByUserId(user.getId());

        // Передать список заказов в JSP
        req.setAttribute("orders", orders);

        // Forward на orderHistory.jsp
        req.getRequestDispatcher("/WEB-INF/views/orderHistory.jsp").forward(req, resp);
    }
}
