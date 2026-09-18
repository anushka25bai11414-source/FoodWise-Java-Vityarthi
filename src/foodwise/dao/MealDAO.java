package foodwise.dao;

import foodwise.model.Meal;
import foodwise.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDAO {

    public boolean addMeal(Meal meal) {
        String sql= """
                INSERT INTO meals
                (meal_name, meal_type, cost_per_student, suitability_score)
                VALUES (?,?,?,?)
                """;

        try 
        (Connection connection=DatabaseManager.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql)) 
             {

            statement.setString(1, meal.getMealName());
            statement.setString(2, meal.getMealType());
            statement.setDouble(3, meal.getCostPerStudent());
            statement.setInt(4, meal.getSuitabilityScore());

            return statement.executeUpdate()>0;

        } catch (SQLException e) {
            System.out.println("error in adding meal: " + e.getMessage());
            return false;
        }
    }

    public List<Meal> getAllMeals() {

        List<Meal>  meals= new ArrayList<>();

        String sql = """
                SELECT * FROM meals
                ORDER BY meal_id
                """;

        try (Connection connection=DatabaseManager.getConnection();
             Statement statement=connection.createStatement();
             ResultSet resultSet=statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Meal meal=new Meal();

                meal.setMealId(resultSet.getInt("meal_id"));
                meal.setMealName(resultSet.getString("meal_name"));
                meal.setMealType(resultSet.getString("meal_type"));
                meal.setCostPerStudent(
                    resultSet.getDouble("cost_per_student")
                );
                meal.setSuitabilityScore(
                    resultSet.getInt("suitability_score")
                );

                meals.add(meal);
            }

        } catch (SQLException e) {
            System.out.println(
                "error retrieving meals: " + e.getMessage()
            );
        }

        return meals;
    }

    /*
     * Checks if enough ingredients are there
     * for given meal and no of students
     */
    public boolean isIngredientAvailabile(
            int mealId,
            int studentCount) {

        String sql= """
                SELECT
                    i.quantity_available,
                    mi.quantity_required
                FROM meal_ingredients mi
                JOIN ingredients i
                    ON mi.ingredient_id = i.ingredient_id
                WHERE mi.meal_id = ?
                """;

        boolean foundIngredient=false;

        try (Connection connection=DatabaseManager.getConnection();
             PreparedStatement statement=
                     connection.prepareStatement(sql)) {

            statement.setInt(1, mealId);

            try (ResultSet resultSet=statement.executeQuery()) {

                while (resultSet.next()) 
                    {

                    foundIngredient=true;

                    double available=
                            resultSet.getDouble("quantity_available");

                    double requiredPerStudent=
                            resultSet.getDouble("quantity_required");

                    double totalRequired=
                            requiredPerStudent* studentCount;

                    if(available<totalRequired) 
                        {
                        return false;
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "error in checking ingredient availability: "
                            + e.getMessage()
            );

            return false;
        }

        /*
         * meal without ingredient mappings is not treated
         * as resource verified
         */
        return foundIngredient;
    }
}