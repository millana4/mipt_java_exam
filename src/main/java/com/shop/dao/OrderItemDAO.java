package com.shop.dao;

import com.shop.model.OrderItem;
import com.shop.model.Product;
import com.shop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    // Метод save: вставить элемент заказа
    public void save(OrderItem orderItem) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getPriceAtTime());
            stmt.executeUpdate();
        }
    }

    // Метод findByOrderId: получить элементы заказа
    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.quantity, oi.price_at_time, oi.created_at, " +
                "p.id as p_id, p.name, p.description, p.price, p.category_id " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.id " +
                "WHERE oi.order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPriceAtTime(rs.getBigDecimal("price_at_time"));
                item.setCreatedAt(rs.getTimestamp("created_at"));

                // Создаём объект Product из JOIN
                Product product = new Product();
                product.setId(rs.getInt("p_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getBigDecimal("p_price"));
                product.setCategoryId(rs.getInt("category_id"));

                item.setProduct(product);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}