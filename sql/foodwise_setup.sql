-- FOODWISE DATABASE SETUP
-- Complete reproducible setup for the FoodWise Java + JDBC + MySQL project.
-- No personal MySQL password is stored here.

CREATE DATABASE IF NOT EXISTS foodwise;
USE foodwise;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    group_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredients (
    ingredient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    quantity_available DECIMAL(10,2) NOT NULL,
    unit VARCHAR(20) NOT NULL,
    cost_per_unit DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS meals (
    meal_id INT PRIMARY KEY AUTO_INCREMENT,
    meal_name VARCHAR(100) NOT NULL,
    meal_type VARCHAR(30) NOT NULL,
    cost_per_student DECIMAL(10,2) NOT NULL,
    suitability_score INT NOT NULL
);

CREATE TABLE IF NOT EXISTS meal_ingredients (
    meal_id INT,
    ingredient_id INT,
    quantity_required DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (meal_id, ingredient_id),
    FOREIGN KEY (meal_id) REFERENCES meals(meal_id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
);

CREATE TABLE IF NOT EXISTS meal_plans (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    meal_id INT NOT NULL,
    student_count INT NOT NULL,
    budget DECIMAL(10,2) NOT NULL,
    total_cost DECIMAL(10,2) NOT NULL,
    remaining_budget DECIMAL(10,2) NOT NULL,
    recommendation_score DECIMAL(5,2) NOT NULL,
    plan_date DATE NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
);

-- Application login (separate from MySQL credentials).
INSERT IGNORE INTO users (name, username, password, role)
VALUES ('FoodWise Administrator', 'admin', 'admin123', 'ADMIN');

-- Final demo dataset: 25 students.
INSERT INTO students (name, age, group_name) VALUES
('ANUSHKA SHARMA', 20, 'General'),
('KUSH SHARMA', 20, 'General'),
('PREETI CHOUHAN', 20, 'General'),
('KANIKA TIWARI', 20, 'General'),
('BADRIKANATH PRAHARAJ', 20, 'General'),
('SREERAM MV', 20, 'General'),
('AARAV MEHTA', 20, 'General'),
('ADITYA SINGH', 21, 'General'),
('AKSHAY KUMAR', 20, 'General'),
('ANANYA GUPTA', 20, 'General'),
('ARJUN PATEL', 21, 'General'),
('DIYA SHARMA', 20, 'General'),
('HARSH VERMA', 21, 'General'),
('ISHA JAIN', 20, 'General'),
('KARAN YADAV', 21, 'General'),
('KAVYA SINGH', 20, 'General'),
('MANAV SHUKLA', 20, 'General'),
('MEERA PATEL', 20, 'General'),
('NIKHIL GUPTA', 21, 'General'),
('PALLAVI JOSHI', 20, 'General'),
('RAHUL TIWARI', 21, 'General'),
('RIYA VERMA', 20, 'General'),
('ROHAN SHARMA', 21, 'General'),
('SAKSHI JAIN', 20, 'General'),
('VIVEK SINGH', 21, 'General');

-- Final demo ingredients.
INSERT INTO ingredients (name, quantity_available, unit, cost_per_unit) VALUES
('Rice', 50.00, 'kg', 55.00),
('Dal', 25.00, 'kg', 110.00),
('Vegetables', 30.00, 'kg', 60.00),
('Potato', 25.00, 'kg', 35.00),
('Wheat Flour', 30.00, 'kg', 45.00),
('Milk', 20.00, 'litre', 60.00);

-- Final demo meals.
INSERT INTO meals (meal_name, meal_type, cost_per_student, suitability_score) VALUES
('Dal Rice with Vegetables', 'Lunch', 32.00, 90),
('Vegetable Khichdi', 'Lunch', 28.00, 95),
('Roti Potato Curry', 'Lunch', 30.00, 85);

-- Required ingredient quantities are per student.
INSERT INTO meal_ingredients (meal_id, ingredient_id, quantity_required) VALUES
(1, 1, 0.12),
(1, 2, 0.05),
(1, 3, 0.08),
(2, 1, 0.08),
(2, 2, 0.04),
(2, 3, 0.10),
(3, 5, 0.12),
(3, 4, 0.10);

SELECT COUNT(*) AS total_students FROM students;
SELECT 'FoodWise database setup completed.' AS status;
