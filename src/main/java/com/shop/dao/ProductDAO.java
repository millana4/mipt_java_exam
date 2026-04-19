package com.shop.dao;

import com.shop.model.Product;
import com.shop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Получить все товары
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
            return products;
        }
    }

    //Найти товар
    public Product findById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }
        }
        return null;
    }

    //Найти товары по категории
    public List<Product> findByCategory(int categoryId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    //Вспомогательный метод: преобразует строку ResultSet в объект Product
    private  Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCreatedAt(rs.getTimestamp("created_at"));
        product.setUpdatedAt(rs.getTimestamp("updated_at"));
        return product;
    }
}
