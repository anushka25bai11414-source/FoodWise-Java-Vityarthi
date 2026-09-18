package foodwise.main;

import foodwise.model.Ingredient;
import foodwise.model.Meal;
import foodwise.model.MealPlan;
import foodwise.service.IngredientService;
import foodwise.service.MealPlanService;
import foodwise.service.MealService;
import foodwise.util.DatabaseManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FoodWiseApp {

    private static final Scanner scanner= new Scanner(System.in);

    private static final IngredientService ingredientService=
            new IngredientService();

    private static final MealService mealService=
            new MealService();

    private static final MealPlanService mealPlanService=
            new MealPlanService();

    private static MealPlan lastGeneratedPlan= null;

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("          WELCOME TO FOODWISE");
        System.out.println("========================================");
        System.out.println("Java-Based Intelligent Meal Planning");
        System.out.println("and Resource Optimization System");
        System.out.println("========================================");

        if(!DatabaseManager.testConnection()) {
            System.out.println("\nDatabase connection failed.");
            System.out.println("Please check MySQL and try again.");
            return;
        }

        System.out.println("\nDatabase connection:SUCCESS!");

        if (!login()) {
            System.out.println("\ntoo many unsuccessful login attempts.");
            return;
        }

        mainMenu();
    }


    // =========================================================
    // LOGIN
    // =========================================================

    private static boolean login() {

        System.out.println("\n----------------------------------------");
        System.out.println("              ADMIN LOGIN");
        System.out.println("----------------------------------------");

        for(int attempt = 1; attempt <= 3; attempt++) {

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            String sql = """
                    SELECT * FROM users
                    WHERE username = ? AND password = ?
                    """;

            try 
            (Connection connection=
                         DatabaseManager.getConnection();
                 PreparedStatement statement=
                         connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, password);

                try 
                (ResultSet resultSet=
                             statement.executeQuery()) {

                    if 
                    (resultSet.next()) {

                        System.out.println("\nLogin successful!");
                        System.out.println(
                                "Welcome, "
                                        + resultSet.getString("name")
                                        + "!"
                        );

                        return true;
                    }
                }

            } catch (SQLException e) {

                System.out.println("Login error: " + e.getMessage()
                );
            }

            System.out.println(
                    "Invalid credentials. Attempts remaining: " + (3 - attempt)
            );
        }

        return false;
    }


    // =========================================================
    // MAIN MENU
    // =========================================================

    private static void mainMenu() {

        boolean running= true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("             FOODWISE MENU");
            System.out.println("========================================");
            System.out.println("1. Student Management");
            System.out.println("2. Ingredient Management");
            System.out.println("3. Meal Management");
            System.out.println("4. Generate Meal Recommendation");
            System.out.println("5. View Saved Meal Plans");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");
            System.out.println("========================================");

            int choice= readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    studentManagement();
                    break;

                case 2:
                    ingredientManagement();
                    break;

                case 3:
                    mealManagement();
                    break;

                case 4:
                    generateRecommendation();
                    break;

                case 5:
                    viewMealPlans();
                    break;

                case 6:
                    generateReport();
                    break;

                case 7:
                    running= false;
                    System.out.println(
                            "\nThank you for using FoodWise!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }


    // =========================================================
    // STUDENT MANAGEMENT
    // =========================================================

    private static void studentManagement() {

        boolean back= false;

        while 
        (!back) {

            System.out.println("\n----------------------------------------");
            System.out.println("          STUDENT MANAGEMENT");
            System.out.println("----------------------------------------");
            System.out.println("1. View Students");
            System.out.println("2. Add Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ");

            switch(choice) 
            {

                case 1:
                    viewStudents();
                    break;

                case 2:
                    addStudent();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void viewStudents() {

        String sql= """
                SELECT * FROM students
                ORDER BY student_id
                """;

        try (Connection connection =
                     DatabaseManager.getConnection();
             Statement statement =
                     connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            System.out.println("\n=================================================");
            System.out.printf(
                    "%-5s %-25s %-8s %-15s%n",
                    "ID", "NAME", "AGE", "GROUP"
            );
            System.out.println("=================================================");

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.printf(
                        "%-5d %-25s %-8d %-15s%n",
                        resultSet.getInt("student_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("group_name")
                );
            }

            if (!found) {
                System.out.println("No students found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading students: "
                            + e.getMessage()
            );
        }
    }


    private static void addStudent() {

        System.out.println("\n--- ADD STUDENT ---");

        String name = readText("Student name: ");
        int age = readInt("Age: ");
        String group = readText("Group name: ");

        if (age<= 0 || age>100) {

            System.out.println(
                    "Invalid age."
            );

            return;
        }

        String sql= """
                INSERT INTO students
                (name, age, group_name)
                VALUES (?, ?, ?)
                """;

        try (Connection connection=
                     DatabaseManager.getConnection();
             PreparedStatement statement=
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, group);

            if (statement.executeUpdate()> 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error adding student: " + e.getMessage());
        }
    }


    private static void updateStudent() {

        viewStudents();

        int id= readInt("\nEnter student ID to update: ");

        String name= readText("New name: ");
        int age= readInt("New age: ");
        String group= readText("New group: ");

        String sql= """
                UPDATE students
                SET name = ?, age = ?, group_name = ?
                WHERE student_id = ?
                """;

        try (Connection connection=
                     DatabaseManager.getConnection();
             PreparedStatement statement=
                     connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, group);
            statement.setInt(4, id);

            if (statement.executeUpdate() > 0) {
                System.out.println( "Student updated successfully!"
                );
            } 
            else {
                System.out.println(
                        "Student ID not found."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating student: " + e.getMessage()
            );
        }
    }


    private static void deleteStudent() {

        viewStudents();

        int id = readInt("\nEnter student ID to delete: ");

        String sql= "DELETE FROM students WHERE student_id = ?";

        try 
        (Connection connection =
                     DatabaseManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            if (statement.executeUpdate() > 0) {
                System.out.println(
                        "Student deleted successfully!"
                );
            } else {
                System.out.println(
                        "Student ID not found."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting student: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // INGREDIENT MANAGEMENT
    // =========================================================

    private static void ingredientManagement() {

        boolean back= false;

        while (!back) {

            System.out.println("\n----------------------------------------");
            System.out.println("         INGREDIENT MANAGEMENT");
            System.out.println("----------------------------------------");
            System.out.println("1. View Ingredients");
            System.out.println("2. Add Ingredient");
            System.out.println("3. Update Quantity");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewIngredients();
                    break;

                case 2:
                    addIngredient();
                    break;

                case 3:
                    updateIngredientQuantity();
                    break;

                case 4:
                    back= true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void viewIngredients() {

        List<Ingredient> ingredients=
                ingredientService.getAllIngredients();

        System.out.println("\n==========================================================");
        System.out.printf(
                "%-5s %-20s %-12s %-10s %-12s%n",
                "ID", "NAME", "QUANTITY", "UNIT", "COST/UNIT"
        );
        System.out.println("==========================================================");

        if (ingredients.isEmpty()) {

            System.out.println(
                    "No ingredients found."
            );

            return;
        }

        for (Ingredient ingredient : ingredients) {

            System.out.printf(
                    "%-5d %-20s %-12.2f %-10s Rs.%-10.2f%n",
                    ingredient.getIngredientId(),
                    ingredient.getName(),
                    ingredient.getQuantityAvailable(),
                    ingredient.getUnit(),
                    ingredient.getCostPerUnit()
            );
        }
    }


    private static void addIngredient() {

        System.out.println("\n--- ADD INGREDIENT ---");

        String name= readText("Ingredient name: ");
        double quantity=
                readDouble("Available quantity: ");
        String unit= readText("Unit (kg/litre/etc.): ");
        double cost=
                readDouble("Cost per unit: ");

        try {

            if (ingredientService.addIngredient(
                name,
                quantity,
                unit,
                cost)) {

                System.out.println(
                        "Ingredient added successfully!"
                );

            } else {

                System.out.println(
                        "Unable to add ingredient."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Validation error: " + e.getMessage()
                );
        }
    }


    private static void updateIngredientQuantity() {

        viewIngredients();

        int id= readInt(
                "\nIngredient ID: "
        );

        double quantity=
                readDouble("New quantity: ");

        try {

            if (ingredientService.updateQuantity(
                    id,
                    quantity)) {

                System.out.println(
                        "Quantity updated successfully!"
                );

            } else {

                System.out.println(
                        "Ingredient ID not found."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Validation error: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // MEAL MANAGEMENT
    // =========================================================

    private static void mealManagement() {

        boolean back = false;

        while (!back) {

            System.out.println("\n----------------------------------------");
            System.out.println("            MEAL MANAGEMENT");
            System.out.println("----------------------------------------");
            System.out.println("1. View Meals");
            System.out.println("2. Add Meal");
            System.out.println("3. Back");

            int choice= readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewMeals();
                    break;

                case 2:
                    addMeal();
                    break;

                case 3:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void viewMeals() {

        List<Meal> meals=
                mealService.getAllMeals();

        System.out.println(
                "\n=============================================================="
        );

        System.out.printf(
                "%-5s %-28s %-12s %-15s %-12s%n",
                "ID",
                "MEAL",
                "TYPE",
                "COST/STUDENT",
                "SUITABILITY"
        );

        System.out.println(
                "=============================================================="
        );

        if (meals.isEmpty()) {

            System.out.println(
                    "No meals found."
            );

            return;
        }

        for (Meal meal : meals) {

            System.out.printf(
                    "%-5d %-28s %-12s Rs.%-14.2f %-12d%n",
                    meal.getMealId(),
                    meal.getMealName(),
                    meal.getMealType(),
                    meal.getCostPerStudent(),
                    meal.getSuitabilityScore()
            );
        }
    }


    private static void addMeal() {

        System.out.println("\n--- ADD MEAL ---");

        String name=
                readText("Meal name: ");

        String type=
                readText("Meal type: ");

        double cost=
                readDouble("Cost per student: ");

        int suitability=
                readInt(
                        "Suitability score (0-100): "
                );

        try {

            if (mealService.addMeal(
                    name,
                    type,
                    cost,
                    suitability)) {

                System.out.println(
                        "Meal added successfully!"
                );

            } else {

                System.out.println(
                        "Unable to add meal."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Validation error: " + e.getMessage()
                );
        }
    }


    // =========================================================
    // INTELLIGENT RECOMMENDATION
    // =========================================================

    private static void generateRecommendation() {

        System.out.println("\n========================================");
        System.out.println("       INTELLIGENT RECOMMENDATION");
        System.out.println("========================================");

        int studentCount=
                readInt("Number of students: ");

        double budget=
                readDouble("Available budget (Rs.): ");

        if (studentCount<= 0 ||
                budget<= 0) {

            System.out.println(
                    "Student count and budget must be positive."
            );

            return;
        }

        List<Meal> meals=
                mealService.getAllMeals();

        if (meals.isEmpty()) {

            System.out.println(
                    "No meals available."
            );

            return;
        }

        try {

            MealPlan plan=
                    mealPlanService.generateMealPlan(
                            meals,
                            studentCount,
                            budget
                    );

            lastGeneratedPlan= plan;

            Meal recommendedMeal=
                    findMealById(
                            meals,
                            plan.getMealId()
                    );

            System.out.println("\n========================================");
            System.out.println("          RECOMMENDATION RESULT");
            System.out.println("========================================");

            System.out.println(
                    "Recommended Meal : "
                            + recommendedMeal.getMealName()
            );

            System.out.println(
                    "Meal Type        : "
                            + recommendedMeal.getMealType()
            );

            System.out.printf(
                    "Students         : %d%n",
                    plan.getStudentCount()
            );

            System.out.printf(
                    "Cost / Student   : Rs.%.2f%n",
                    recommendedMeal.getCostPerStudent()
            );

            System.out.printf(
                    "Total Cost       : Rs.%.2f%n",
                    plan.getTotalCost()
            );

            System.out.printf(
                    "Budget           : Rs.%.2f%n",
                    plan.getBudget()
            );

            System.out.printf(
                    "Remaining Budget : Rs.%.2f%n",
                    plan.getRemainingBudget()
            );

            System.out.printf(
                    "Suitability      : %d/100%n",
                    recommendedMeal.getSuitabilityScore()
            );

            System.out.printf(
                    "Recommendation Score : %.2f/100%n",
                    plan.getRecommendationScore()
            );

            System.out.println(
                    "Plan Date        : "
                            + plan.getPlanDate()
            );

            System.out.println("========================================");

            String save =
                    readText(
                            "Save this meal plan? (yes/no): "
                    );

            if (save.equalsIgnoreCase("yes")) {

                if (mealPlanService.saveMealPlan(plan)) {

                    System.out.println(
                            "Meal plan saved successfully!"
                    );

                } else {

                    System.out.println(
                            "Unable to save meal plan."
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "\nRecommendation error: "
                            + e.getMessage()
            );
        }
    }


    private static Meal findMealById(
            List<Meal> meals,
            int mealId) {

        for (Meal meal: meals) {

            if (meal.getMealId()== mealId) {
                return meal;
            }
        }

        return null;
    }


    // =========================================================
    // VIEW MEAL PLANS
    // =========================================================

    private static void viewMealPlans() {

        System.out.println("\n========================================");
        System.out.println("           SAVED MEAL PLANS");
        System.out.println("========================================");

        List<MealPlan> plans =
                mealPlanService.getAllMealPlans();

        List<Meal> meals =
                mealService.getAllMeals();

        if (plans.isEmpty()) {

            System.out.println(
                    "No saved meal plans found."
            );

            return;
        }

        for (MealPlan plan : plans) {

            Meal meal =
                    findMealById(
                            meals,
                            plan.getMealId()
                    );

            String mealName =
                    meal != null
                            ? meal.getMealName()
                            : "Unknown Meal";

            System.out.println(
                    "\n----------------------------------------"
            );

            System.out.println(
                    "Plan ID          : "
                            + plan.getPlanId()
            );

            System.out.println(
                    "Meal             : "
                            + mealName
            );

            System.out.println(
                    "Students         : "
                            + plan.getStudentCount()
            );

            System.out.printf(
                    "Budget           : Rs.%.2f%n",
                    plan.getBudget()
            );

            System.out.printf(
                    "Total Cost       : Rs.%.2f%n",
                    plan.getTotalCost()
            );

            System.out.printf(
                    "Remaining Budget : Rs.%.2f%n",
                    plan.getRemainingBudget()
            );

            System.out.printf(
                    "Score            : %.2f/100%n",
                    plan.getRecommendationScore()
            );

            System.out.println(
                    "Date             : "
                            + plan.getPlanDate()
            );
        }

        System.out.println(
                "\n----------------------------------------"
        );
    }


    // =========================================================
    // REPORT GENERATION - FILE I/O
    // =========================================================

    private static void generateReport() {

        if (lastGeneratedPlan==null) {

            System.out.println(
                    "\nNo meal plan has been generated "
                            + "during this session."
            );

            System.out.println(
                    "Generate a recommendation first."
            );

            return;
        }

        List<Meal> meals=
                mealService.getAllMeals();

        Meal meal =
                findMealById(
                        meals,
                        lastGeneratedPlan.getMealId()
                );

        if (meal==null) {

            System.out.println(
                    "Meal information unavailable."
            );

            return;
        }

        File reportsFolder=
                new File("reports");

        if (!reportsFolder.exists()) {
            reportsFolder.mkdirs();
        }

        String fileName=
                "reports/meal_plan_"
                        + LocalDate.now()
                        + ".txt";

        try (FileWriter writer =
                     new FileWriter(fileName)) {

            writer.write(
                    "========================================\n"
            );

            writer.write(
                    "              FOODWISE REPORT\n"
            );

            writer.write(
                    "========================================\n\n"
            );

            writer.write(
                    "Meal Planning and Resource "
                            + "Optimization Report\n\n"
            );

            writer.write(
                    "Plan Date: "
                            + lastGeneratedPlan.getPlanDate()
                            + "\n"
            );

            writer.write(
                    "Recommended Meal: "
                            + meal.getMealName()
                            + "\n"
            );

            writer.write(
                    "Meal Type: "
                            + meal.getMealType()
                            + "\n"
            );

            writer.write(
                    "Number of Students: "
                            + lastGeneratedPlan.getStudentCount()
                            + "\n"
            );

            writer.write(
                    String.format(
                            "Cost per Student: Rs.%.2f%n",
                            meal.getCostPerStudent()
                    )
            );

            writer.write(
                    String.format(
                            "Total Cost: Rs.%.2f%n",
                            lastGeneratedPlan.getTotalCost()
                    )
            );

            writer.write(
                    String.format(
                            "Allocated Budget: Rs.%.2f%n",
                            lastGeneratedPlan.getBudget()
                    )
            );

            writer.write(
                    String.format(
                            "Remaining Budget: Rs.%.2f%n",
                            lastGeneratedPlan
                                    .getRemainingBudget()
                    )
            );

            writer.write(
                    "Suitability Score: "
                            + meal.getSuitabilityScore()
                            + "/100\n"
            );

            writer.write(
                    String.format(
                            "Recommendation Score: %.2f/100%n",
                            lastGeneratedPlan
                                    .getRecommendationScore()
                    )
            );

            writer.write(
                    "\nStatus: Recommended within budget\n"
            );

            writer.write(
                    "\n========================================\n"
            );

            writer.write(
                    "Generated by FoodWise Java Application\n"
            );

            writer.write(
                    "========================================\n"
            );

            System.out.println(
                    "\nReport generated successfully!"
            );

            System.out.println(
                    "File: " + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "error generating report: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // INPUT HELPERS
    // =========================================================

    private static int readInt(String message) {

        while(true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch(NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    private static double readDouble(String message) {

        while(true) {

            try{
                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    private static String readText(String message) {

        while(true) 
        {
            System.out.print(message);

            String input=
                    scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "input cannot be empty."
            );
        }
    }
}