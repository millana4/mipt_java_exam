package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem {

    // Поля, соответствующие таблице order_items
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private BigDecimal priceAtTime;  // цена на момент заказа
    private Timestamp createdAt;

    // Временное поле для отображения (не сохраняется в БД)
    // Используется для отображения информации о товаре в истории заказов
    private Product product;

    // Конструктор по умолчанию
    public OrderItem() {
    }

    // Конструктор для создания нового элемента заказа
    public OrderItem(int orderId, int productId, int quantity, BigDecimal priceAtTime) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Временное поле product (для отображения)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Вспомогательный метод для расчета стоимости позиции
    public BigDecimal getTotalPrice() {
        if (priceAtTime != null) {
            return priceAtTime.multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    // Переопределяем toString для удобного вывода
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", priceAtTime=" + priceAtTime +
                '}';
    }
}
