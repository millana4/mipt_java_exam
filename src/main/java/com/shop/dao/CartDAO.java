package com.shop.dao;

import com.shop.model.Cart;
import com.shop.model.Product;
import com.shop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Метод addOrUpdate: вставка или обновление количества (ON CONFLICT)
    public void addOrUpdate(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                "ON CONFLICT (user_id, product_id) DO UPDATE SET quantity = cart.quantity + ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод findByUserId: получить все позиции корзины пользователя с JOIN на products
    public List<Cart> findByUserId(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.product_id, c.quantity, c.created_at, c.updated_at, " +
                "p.id as p_id, p.name, p.description, p.price, p.category_id, p.created_at as p_created, p.updated_at as p_updated " +
                "FROM cart c " +
                "JOIN products p ON c.product_id = p.id " +
                "WHERE c.user_id = ? " +
                "ORDER BY c.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setProductId(rs.getInt("product_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                cart.setUpdatedAt(rs.getTimestamp("updated_at"));

                // Создаём объект Product из JOIN
                Product product = new Product();
                product.setId(rs.getInt("p_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setCreatedAt(rs.getTimestamp("p_created"));
                product.setUpdatedAt(rs.getTimestamp("p_updated"));

                cart.setProduct(product);
                cartItems.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Метод delete: удалить позицию по ID
    public void delete(int cartItemId) {
        String sql = "DELETE FROM cart WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItemId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод clearByUserId: очистить корзину пользователя
    public void clearByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}