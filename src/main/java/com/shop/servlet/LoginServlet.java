package com.shop.servlet;

import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        // Проверка, что поля не пустые
        if (login == null || password == null || login.trim().isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Заполните все поля");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Поиск пользователя
        User user = userDAO.findByLogin(login);

        if (user == null) {
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Проверка пароля
        String hashedInputPassword = PasswordUtil.hashPassword(password);
        if (!hashedInputPassword.equals(user.getPasswordHash())) {
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Успешный вход — создаём сессию
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        // Перенаправление на список товаров
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}