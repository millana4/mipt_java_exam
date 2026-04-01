package com.shop.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {

    // Константы для статусов заказа (совпадают со значениями ENUM в БД)
    public static final String STATUS_CREATED = "CREATED";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_SHIPPED = "SHIPPED";
    public static final String STATUS_DELIVERED = "DELIVERED";

    // Поля, соответствующие таблице orders
    private int id;
    private int userId;
    private Timestamp orderDate;
    private String status;
    private BigDecimal totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Конструктор по умолчанию
    public Order() {
    }

    // Конструктор для создания нового заказа
    public Order(int userId, BigDecimal totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = STATUS_CREATED;
        this.orderDate = new Timestamp(System.currentTimeMillis());
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // Проверка, что статус валидный (опционально)
        if (status != null && (status.equals(STATUS_CREATED) ||
                status.equals(STATUS_CONFIRMED) ||
                status.equals(STATUS_SHIPPED) ||
                status.equals(STATUS_DELIVERED))) {
            this.status = status;
        } else {
            this.status = STATUS_CREATED; // значение по умолчанию
        }
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Вспомогательные методы для проверки статуса
    public boolean isCreated() {
        return STATUS_CREATED.equals(status);
    }

    public boolean isConfirmed() {
        return STATUS_CONFIRMED.equals(status);
    }

    public boolean isShipped() {
        return STATUS_SHIPPED.equals(status);
    }

    public boolean isDelivered() {
        return STATUS_DELIVERED.equals(status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}