# FOODWISE
### Java-Based Intelligent Meal Planning and Resource Optimization System for Student Welfare

**Registration No:** 25BAI11414
**Name of Student:** ANUSHKA SHARMA
**Course Name:** PROGRAMMING IN JAVA
**Course Code:** CSE2006
**School Name:** SCAI
**Course:** B.Tech CSE(AIML)
**University:** VIT BHOPAL

---

## 1. Project Overview

FoodWise is a Java-based intelligent meal planning and resource optimization system developed for student welfare situations.

Planning meals for a group of students can become challenging when the number of students, available budget, ingredient stock, and meal suitability have to be considered together. FoodWise provides a structured system to manage this information and generate a suitable meal recommendation.
The system allows an administrator to manage students, ingredients, and meals. It also checks ingredient availability and recommends a meal based on budget, student suitability, and resource efficiency.

The project was developed as a Java course project to apply object-oriented programming, database connectivity, validation, decision-making logic, and file handling in a practical student welfare problem.

### Problem Statement

Manual meal planning for a group of students can take considerable time because the planner has to consider the number of students, available budget, ingredient stock, and suitable meal options simultaneously.

Manual calculations may also result in errors in estimating total cost, remaining budget, or ingredient requirements.

FoodWise addresses this problem by storing student, meal, and ingredient information in a structured database and using a recommendation process to select a suitable meal based on predefined criteria.

---

## 2. Features

### 3.1 Student Management
- Add student
- View students
- Update student details
- Delete student details
- Store student age
- Store student group/category

### 3.2 Ingredient Management
- Add ingredients
- View available ingredients
- Update ingredient quantity
- Store ingredient unit
- Store cost per unit
- Check ingredient availability

### 3.3 Meal Management
- Add meals
- View available meals
- Store meal type
- Store cost per student
- Store suitability score
- Maintain ingredient requirements for meals

### 3.4 Intelligent Meal Recommendation
- Accept number of students and available budget
- Check ingredient availability
- Filter unsuitable meal options
- Check budget requirements
- Calculate recommendation score
- Recommend the highest-scoring suitable meal

The recommendation score considers three factors:
- Budget: 30%
- Student Suitability: 40%
- Resource Efficiency: 30%

### 3.5 Meal Plan and Reporting
- Calculate total meal cost
- Calculate remaining budget
- Display recommendation score
- Save meal plans in MySQL
- View previously saved meal plans
- Generate a basic text report

---

## 4. Technologies and Tools

| Technology / Tool | Purpose |
|---|---|
| Java | Core application development |
| JDBC | Database connectivity |
| MySQL | Data storage |
| MySQL Connector/J | JDBC driver |
| VS Code | Development environment |
| Git | Version control |
| GitHub | Source code repository |
| MySQL Workbench | Database management |

---

## 5. Java Concepts Used

The project demonstrates several important Java programming concepts.

### Object-Oriented Programming
- Classes and objects
- Encapsulation
- Inheritance
- Abstraction
- Interfaces
- Polymorphism

### Other Java Concepts
- Collections such as ArrayList and HashMap
- Exception handling
- Custom exceptions
- Input validation
- File handling
- JDBC
- Packages
- Constructors
- Getters and setters
- SQL integration

### OOP Implementation in FoodWise

- **Encapsulation:** Model classes use private data fields with appropriate constructors, getters, and setters.
- **Inheritance:** The `Student` class extends the abstract `User` class.
- **Abstraction:** The `User` class is implemented as an abstract class.
- **Interface:** `RecommendationStrategy` defines the recommendation operation.
- **Polymorphism:** `RecommendationEngine` implements the `RecommendationStrategy` interface.

---

## 6. Project Structure

```
FoodWise/
│
├── src/
│   └── foodwise/
│       ├── model/
│       │   ├── User.java
│       │   ├── Student.java
│       │   ├── Ingredient.java
│       │   ├── Meal.java
│       │   └── MealPlan.java
│       │
│       ├── dao/
│       │   ├── StudentDAO.java
│       │   ├── IngredientDAO.java
│       │   ├── MealDAO.java
│       │   └── MealPlanDAO.java
│       │
│       ├── service/
│       │   ├── StudentService.java
│       │   ├── IngredientService.java
│       │   ├── MealService.java
│       │   ├── MealPlanService.java
│       │   ├── RecommendationStrategy.java
│       │   └── RecommendationEngine.java
│       │
│       ├── exception/
│       │   └── InvalidInputException.java
│       │
│       ├── util/
│       │   └── DatabaseManager.java
│       │
│       └── main/
│           └── FoodWiseApp.java
│
├── lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── sql/
│   └── foodwise_setup.sql
│
├── reports/
├── screenshots/
├── README.md
├── statement.md
└── .gitignore
```

---

## 7. Installation and Setup

### 7.1 Requirements

Before running FoodWise, install:

- Java JDK 17 or later
- MySQL Server
- MySQL Workbench
- VS Code with Extension Pack for Java, if using VS Code

### 7.2 Step 1: Download or Clone the Project

Clone the GitHub repository or download the project files.

### 7.3 Step 2: Set Up the Database

Open MySQL Workbench.

Open the SQL setup file:

```
sql/foodwise_setup.sql
```

Run the complete SQL script.

The script creates the database:

```
foodwise
```

The following tables are created:

- users
- students
- ingredients
- meals
- meal_ingredients
- meal_plans

The SQL script also inserts the sample data required for demonstration and testing, including the 25-student dataset.

### 7.4 Step 3: Configure MySQL Credentials

Open:

```
src/foodwise/util/DatabaseManager.java
```

The public project version contains a placeholder for the MySQL password:

```java
private static final String USER = "root";
private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
```

Replace `YOUR_MYSQL_PASSWORD` locally with the password of the MySQL account on the computer where the application is being run.

If a different MySQL username is being used, update the `USER` value accordingly.

> **Important:** Actual MySQL credentials should not be uploaded to GitHub.

### 7.5 Step 4: Check MySQL Configuration

FoodWise uses the following database configuration:

- **Host:** localhost
- **Port:** 3306
- **Database:** foodwise

Make sure the MySQL Server is running before starting the application.

### 7.6 Step 5: Run the Application

Open the project in VS Code.

Navigate to:

```
src/foodwise/main/FoodWiseApp.java
```

Run the Java application.

If the database connection is successful, the FoodWise welcome screen and main menu will be displayed.

---

## 8. Application Login

FoodWise provides an administrator login for accessing the application.

For demonstration purposes, use:

- **Username:** admin
- **Password:** admin123

The application login credentials are separate from the MySQL database credentials.

After successful login, the main menu provides the following options:

1. Student Management
2. Ingredient Management
3. Meal Management
4. Generate Meal Recommendation
5. View Saved Meal Plans
6. Generate Report
7. Exit

---

## 9. Testing Instructions

The following tests can be performed after starting the application.

### Test 1: Database Connection

Start the application and verify that the database connection message indicates a successful connection.

**Expected result:**
```
Database connection: SUCCESS!
```

### Test 2: Administrator Login

Enter:
- Username: admin
- Password: admin123

**Expected result:** The main FoodWise menu should be displayed.

### Test 3: Student Management

Open Student Management.

Test the following operations:
- View students
- Add a student
- Update student details
- Delete a student

**Expected result:** Student information should be added, displayed, updated, or deleted successfully.

### Test 4: Ingredient Management

Open Ingredient Management.

Test:
- View ingredients
- Add an ingredient
- Update ingredient quantity

**Expected result:** Ingredient records and quantities should be updated in the database.

### Test 5: Meal Management

Open Meal Management.

Test:
- View meals
- Add a meal

**Expected result:** Meal records should be displayed and new meals should be stored successfully.

### Test 6: Meal Recommendation

Enter:
- Number of Students: 25
- Budget: Rs. 1500

The system checks ingredient availability and evaluates the available meals.

A tested result is:

```
Recommended Meal: Vegetable Khichdi
Cost per Student: Rs. 28.00
Total Cost: Rs. 700.00
Remaining Budget: Rs. 800.00
Suitability Score: 95/100
Recommendation Score: 93/100
```

### Test 7: Save Meal Plan

After generating a recommendation, use the save option.

**Expected result:** The generated meal plan should be stored in the `meal_plans` database table.

### Test 8: View Saved Meal Plans

Open View Saved Meal Plans.

**Expected result:** Previously saved meal plans should be displayed with details such as:
- Student count
- Budget
- Meal
- Total cost
- Remaining budget
- Recommendation score
- Plan date

### Test 9: Generate Report

Use the Generate Report option after creating a meal recommendation.

The application generates a text report inside:

```
reports/
```

---

## 10. Database Design

FoodWise uses MySQL for persistent storage.

### Main Tables

**users**
Stores administrator login and role information.

**students**
Stores student information including:
- Student ID
- Name
- Age
- Group

**ingredients**
Stores:
- Ingredient ID
- Ingredient name
- Available quantity
- Unit
- Cost per unit

**meals**
Stores:
- Meal ID
- Meal name
- Meal type
- Cost per student
- Suitability score

**meal_ingredients**
This is a junction table that connects meals with their required ingredients and quantities.

**meal_plans**
Stores generated meal planning results including:
- Plan ID
- Meal ID
- Student count
- Budget
- Total cost
- Remaining budget
- Recommendation score
- Plan date

### Database Relationship Overview

```
Users
  └── Administrator Login

Students
  └── Student Information

Meals ─────────── Meal Ingredients ─────────── Ingredients
  │                                                  │
  └──────────────── Meal Plan ──────────────────────┘
```

---

## 11. Recommendation Logic

FoodWise uses a weighted recommendation strategy instead of selecting a meal only on the basis of cost.

The recommendation score is calculated using three factors:

| Factor | Weight |
|---|---|
| Budget | 30% |
| Student Suitability | 40% |
| Resource Efficiency | 30% |

**Budget Score**
The system evaluates how well the meal fits within the available budget. Meals exceeding the available budget are filtered out.

**Student Suitability**
Each meal has a suitability score between 0 and 100. This score represents how appropriate the meal is for the intended student group.

**Resource Efficiency**
The system considers the cost per student while evaluating resource efficiency.

**Ingredient Availability**
Before recommending a meal, the system checks whether sufficient quantities of the required ingredients are available for the given number of students.

**Final Recommendation**
After filtering invalid options, the recommendation engine calculates the overall score and selects the meal with the highest score.

For example:

```
Students: 25
Budget: Rs. 1500

Recommended Meal: Vegetable Khichdi
Cost per Student: Rs. 28
Total Cost: Rs. 700
Remaining Budget: Rs. 800
Recommendation Score: 93/100
```

---

## 12. Project Purpose

FoodWise was developed to demonstrate how Java programming concepts can be applied to a practical student welfare problem.

The system combines:
- Object-oriented programming
- Inheritance
- Abstraction
- Interfaces
- Polymorphism
- Collections
- Exception handling
- Input validation
- JDBC
- MySQL
- File handling
- Decision-making logic

The project provides a structured workflow from entering student and resource information to generating and storing a meal plan.

---

## 13. Future Enhancements

The current system can be extended in several ways.

Possible future enhancements include:

- Nutrition-based meal recommendation
- Student dietary preferences
- Vegetarian and non-vegetarian meal categories
- Allergy management
- More detailed student categories
- Advanced inventory management
  - Low-stock alerts
  - Ingredient expiry-date tracking
  - Stock usage history
- Graphical user interface
- Advanced reports and analytics
  - Charts and dashboards
- Improved authentication and security
  - Multiple administrator roles
- Web-based version
- Mobile application

These features are proposed as future improvements and are not part of the current implementation.

---

## 14. Repository Contents

The GitHub repository contains the following project components:

- Complete Java source code
  - Model classes
  - DAO classes
  - Service classes
  - Utility classes
  - Custom exception
  - Main application
- MySQL database setup script
- JDBC connector
- Project report
- Project statement
- README documentation
- Generated report directory
- Git configuration files

### Complete Source Code

The complete source code is maintained in the repository.

The project is organized into separate packages for model, DAO, service, exception, utility, and main application components to keep the implementation modular and maintainable.

### Screenshots

### 1. Admin Login

The application starts by checking the MySQL database connection and then displays the administrator login screen.

![FoodWise Admin Login](OUTPUT%20SCREENSHOTS/Screenshot%202026-09-18%20101212.png)

### 2. FoodWise Main Menu

After successful login, the administrator can access student management, ingredient management, meal management, meal recommendation, saved meal plans, and report generation.

![FoodWise Main Menu](OUTPUT%20SCREENSHOTS/Screenshot%202026-09-18%20101508.png)

### 3. Generated Meal Planning Report

FoodWise generates a meal planning report containing the recommended meal, number of students, cost per student, total cost, allocated budget, remaining budget, suitability score, and recommendation score.

![FoodWise Generated Report](OUTPUT%20SCREENSHOTS/Screenshot%202026-09-18%20102930.png)


### Complete Output Screenshots

The complete set of application output screenshots is available in the `OUTPUT SCREENSHOTS` folder of this repository.

---

## License

This project was developed for academic purposes as part of the Programming in Java course.

---

## Declaration

I hereby declare that this project titled "AI-Based Inclusive Welfare Recommendation System" is an original work carried out by me as part of the course Fundamentals in AI & ML (CSA2001). The project has been developed independently using the concepts learned during the course. All logical structures, rules, and implementation have been designed and written by me for academic purposes. I confirm that this work has not been copied from any external source and reflects my own understanding and effort.
