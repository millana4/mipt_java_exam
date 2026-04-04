package com.shop.dao;

import com.shop.model.User;
import com.shop.utils.DBConnection;
import java.sql.*;

public class UserDAO {

    // Поиск пользователя по логину
    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Проверка существования логина
    public boolean isLoginExists(String login) {
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Сохранение нового пользователя
    public void save(User user) {
        String sql = "INSERT INTO users (login, password_hash, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        UserDAO dao = new UserDAO();
//
//        // Проверка сохранения пользователя
//        User newUser = new User();
//        newUser.setLogin("testuser");
//        newUser.setPasswordHash("dummyhash");
//        newUser.setRole(User.ROLE_USER);
//        dao.save(newUser);
//        System.out.println("Пользователь сохранен");
//
//        // Проверка поиска
//        User found = dao.findByLogin("testuser");
//        if (found != null) {
//            System.out.println("Найден: " + found.getLogin() + ", роль: " + found.getRole());
//        }
//
//        // Проверка существования логина
//        boolean exists = dao.isLoginExists("testuser");
//        System.out.println("Логин testuser существует: " + exists);
//    }
}