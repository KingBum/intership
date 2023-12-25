-- Create the 'springproject' database
CREATE DATABASE IF NOT EXISTS springproject;
USE springproject;

-- Create the 'user_role' table with default value for 'role_name'
CREATE TABLE IF NOT EXISTS user_role (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
);

-- Insert sample data into 'user_role'
INSERT INTO user_role (role_name) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_EDITOR'), ('ROLE_GUEST');


-- Create the 'user' table
CREATE TABLE IF NOT EXISTS user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role_id INT DEFAULT 1,
    is_verified BOOLEAN DEFAULT false,
    verification_token VARCHAR(255),
    expiration_time DATETIME,
    FOREIGN KEY (role_id) REFERENCES user_role(role_id)
);

-- Insert sample data into 'user'
INSERT INTO user (username, password, email, role_id) VALUES
    ('user', '123123', 'user1@example.com', 1),
    ('admin', '123123', 'admin@example.com', 2)
;

-- Create the 'category' table
CREATE TABLE IF NOT EXISTS category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(50) NOT NULL
);
-- Insert sample data into 'category'
INSERT INTO category (category_name) VALUES ('Electronics'), ('Clothing'), ('Books'), ('Furniture');

-- Create the 'product' table
CREATE TABLE IF NOT EXISTS product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price double NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
-- Insert sample data into 'product'
INSERT INTO product (product_name, price, category_id) VALUES
    ('Smartphone', 499.99, 1),
    ('T-shirt', 19.99, 2),
    ('Programming Book', 39.99, 3),
    ('Coffee Table', 129.99, 4);



