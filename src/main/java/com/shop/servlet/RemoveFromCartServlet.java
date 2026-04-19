package com.shop.servlet;

import com.shop.dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/remove")
public class RemoveFromCartServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Получить параметр cartItemId
        String cartItemIdParam = req.getParameter("cartItemId");

        if (cartItemIdParam != null && !cartItemIdParam.isEmpty()) {
            try {
                int cartItemId = Integer.parseInt(cartItemIdParam);
                // Вызвать CartDAO.delete()
                cartDAO.delete(cartItemId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Редирект на CartServlet
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}