package com.shop.servlet;

import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        // Проверка, что поля не пустые
        if (login == null || password == null || login.trim().isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Заполните все поля");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Проверка, существует ли логин
        if (userDAO.isLoginExists(login)) {
            req.setAttribute("error", "Пользователь с таким логином уже существует");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        // Создание пользователя
        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setRole(User.ROLE_USER);

        userDAO.save(user);

        // Перенаправление на страницу входа
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}