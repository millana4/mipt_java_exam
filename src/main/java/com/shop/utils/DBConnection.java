package com.shop.utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = String.format("jdbc:postgresql://%s:%s/%s",
            dotenv.get("DB_HOST"),
            dotenv.get("DB_PORT"),
            dotenv.get("DB_NAME"));
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

//    public static void main(String[] args) {
//        try (Connection conn = DBConnection.getConnection()) {
//            System.out.println("Подключение к базе данных успешно!");
//        } catch (SQLException e) {
//            System.out.println("Ошибка: " + e.getMessage());
//        }
//    }
}
