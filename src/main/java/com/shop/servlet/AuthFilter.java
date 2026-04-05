package com.shop.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")  // перехватывает все запросы
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();

        // Страницы, доступные без авторизации
        boolean isLoginPage = uri.endsWith("/login");
        boolean isRegisterPage = uri.endsWith("/register");
        boolean isStaticResource = uri.startsWith(req.getContextPath() + "/css/");

        if (isLoginPage || isRegisterPage || isStaticResource) {
            // Доступно всем
            chain.doFilter(request, response);
            return;
        }

        // Проверка авторизации
        if (session != null && session.getAttribute("user") != null) {
            // Пользователь авторизован — пропускаем
            chain.doFilter(request, response);
        } else {
            // Не авторизован — редирект на страницу входа
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}