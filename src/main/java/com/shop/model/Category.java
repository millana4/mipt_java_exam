package com.shop.model;

import java.sql.Timestamp;

public class Category {

    // Поля, соответствующие таблице categories
    private int id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Конструктор по умолчанию
    public Category() {
    }

    // Конструктор для создания новой категории (без id и дат)
    public Category(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    // Переопределяем toString для удобного вывода
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
