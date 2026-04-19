-- Таблица users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(50) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'USER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица categories
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица products
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          description TEXT,
                          price DECIMAL(10, 2) NOT NULL,
                          category_id INTEGER REFERENCES categories(id) ON DELETE SET NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица cart
CREATE TABLE cart (
                      id SERIAL PRIMARY KEY,
                      user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                      product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                      quantity INTEGER NOT NULL DEFAULT 1 CHECK (quantity > 0),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UNIQUE(user_id, product_id)
);

-- Таблица orders
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
                        total_amount DECIMAL(10, 2) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица order_items
CREATE TABLE order_items (
                             id SERIAL PRIMARY KEY,
                             order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE SET NULL,
                             quantity INTEGER NOT NULL CHECK (quantity > 0),
                             price_at_time DECIMAL(10, 2) NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Индексы
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_cart_user_id ON cart(user_id);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);

-- Тестовые данные: категории
INSERT INTO categories (name) VALUES ('Электроника'), ('Одежда'), ('Книги');

-- Тестовые данные: товары
INSERT INTO products (name, description, price, category_id) VALUES
                                                                 ('Ноутбук Lenovo', '15.6 дюймов, 8GB RAM, 256GB SSD', 45000.00, 1),
                                                                 ('Смартфон Xiaomi', '6.67 дюймов, 128GB', 25000.00, 1),
                                                                 ('Футболка хлопковая', 'Белая, размер M', 1200.00, 2),
                                                                 ('Джинсы классические', 'Синие, размер 32', 3500.00, 2),
                                                                 ('Java. Полное руководство', 'Герберт Шилдт', 1500.00, 3);