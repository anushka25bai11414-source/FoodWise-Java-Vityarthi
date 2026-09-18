package foodwise.dao;

import foodwise.model.MealPlan;
import foodwise.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealPlanDAO {

    // SAVE MEAL PLAN
    public boolean saveMealPlan(MealPlan plan) {

        String sql= """
                INSERT INTO meal_plans
                (meal_id, student_count, budget, total_cost,
                 remaining_budget, recommendation_score, plan_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection= DatabaseManager.getConnection();
             PreparedStatement statement=
                     connection.prepareStatement(sql)) {

            statement.setInt(1, plan.getMealId());
            statement.setInt(2, plan.getStudentCount());
            statement.setDouble(3, plan.getBudget());
            statement.setDouble(4, plan.getTotalCost());
            statement.setDouble(5, plan.getRemainingBudget());
            statement.setDouble(6, plan.getRecommendationScore());
            statement.setDate(
                    7,
                    Date.valueOf(plan.getPlanDate())
            );

            return statement.executeUpdate() > 0;

        } catch 
        (SQLException e) {

            System.out.println(
                    "error saving meal plan: " + e.getMessage()
            );

            return false;
        }
    }


    // GET ALL MEAL PLANS
    public List<MealPlan> getAllMealPlans() {

        List<MealPlan> plans=new ArrayList<>();

        String sql="""
                SELECT * FROM meal_plans
                ORDER BY plan_id DESC
                """;

        try (Connection connection= DatabaseManager.getConnection();
             Statement statement= connection.createStatement();
             ResultSet resultSet= statement.executeQuery(sql)) {

            while (resultSet.next()) {

                MealPlan plan= new MealPlan();

                plan.setPlanId(
                        resultSet.getInt("plan_id")
                );

                plan.setMealId(
                        resultSet.getInt("meal_id")
                );

                plan.setStudentCount(
                        resultSet.getInt("student_count")
                );

                plan.setBudget(
                        resultSet.getDouble("budget")
                );

                plan.setTotalCost(
                        resultSet.getDouble("total_cost")
                );

                plan.setRemainingBudget(
                        resultSet.getDouble("remaining_budget")
                );

                plan.setRecommendationScore(
                        resultSet.getDouble(
                                "recommendation_score")
                );

                plan.setPlanDate(
                        resultSet.getDate("plan_date").toLocalDate()
                );

                plans.add(plan);
            }

        } catch (SQLException e) {

            System.out.println(
                "Error retrieving meal plans: "+ e.getMessage()
            );
        }

        return plans;
    }
}