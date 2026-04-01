package com.shop.model;

import java.sql.Timestamp;

public class User {

    // Константа для роли пользователя
    public static final String ROLE_USER = "USER";

    // Поля, соответствующие таблице users
    private int id;
    private String login;
    private String passwordHash;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Конструктор по умолчанию
    public User() {
    }

    // Конструктор для создания нового пользователя (без id и дат)
    public User(String login, String passwordHash, String role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    // Вспомогательный метод для проверки, является ли пользователь админом
    // (на будущее, если понадобится)
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    // Переопределяем toString для удобного вывода (необязательно)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}