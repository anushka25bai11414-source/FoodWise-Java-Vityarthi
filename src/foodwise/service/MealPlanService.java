package foodwise.service;

import foodwise.dao.MealDAO;
import foodwise.dao.MealPlanDAO;
import foodwise.exception.InvalidInputException;
import foodwise.model.Meal;
import foodwise.model.MealPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MealPlanService {

    private final MealPlanDAO mealPlanDAO;
    private final MealDAO mealDAO;
    private final RecommendationStrategy recommendationStrategy;

    public MealPlanService() {

        mealPlanDAO = new MealPlanDAO();
        mealDAO = new MealDAO();
        recommendationStrategy = new RecommendationEngine();
    }

    public MealPlan generateMealPlan(
            List<Meal> meals,
            int studentCount,
            double budget)
            throws InvalidInputException {

        validatePlanInput(studentCount, budget);

        if (meals == null || meals.isEmpty()) {
            throw new InvalidInputException(
                    "No meals are available for recommendation."
            );
        }

        /*
         * filter meals based on the real ingredient availability.
         */
        List<Meal> availableMeals = new ArrayList<>();

        for (Meal meal : meals) {

            boolean ingredientsAvailable =
                    mealDAO.isIngredientAvailabile(
                            meal.getMealId(),
                            studentCount
                    );

            if (ingredientsAvailable) {
                availableMeals.add(meal);
            }
        }

        if (availableMeals.isEmpty()) {
            throw new InvalidInputException(
                    "No meals have sufficient ingredients for "
                            + "the selected number of students."
            );
        }

        /*
         * Recommend best meal among resource-available meals.
         */
        Meal recommendedMeal=
                recommendationStrategy.recommendMeal(
                        availableMeals,
                        studentCount,
                        budget
                );

        if (recommendedMeal==null) {
            throw new InvalidInputException(
                    "No suitable meal found within the given budget."
            );
        }

        double totalCost=
                recommendedMeal.getCostPerStudent()
                        * studentCount;

        double remainingBudget=
                budget - totalCost;

        double recommendationScore=
                calculateScore(
                        recommendedMeal,
                        studentCount,
                        budget
                );

        return new MealPlan(
                0,
                recommendedMeal.getMealId(),
                studentCount,
                budget,
                totalCost,
                remainingBudget,
                recommendationScore,
                LocalDate.now()
        );
    }

    public boolean saveMealPlan(MealPlan plan) {
        return mealPlanDAO.saveMealPlan(plan);
    }

    public List<MealPlan> getAllMealPlans() {
        return mealPlanDAO.getAllMealPlans();
    }

    private double calculateScore(
            Meal meal,
            int studentCount,
            double budget) {

        double totalCost =
                meal.getCostPerStudent()
                        * studentCount;

        if (totalCost > budget || budget <= 0) {
            return 0;
        }

        double budgetScore;

        double usageRatio = totalCost / budget;

        if (usageRatio<=0.50) {
            budgetScore=30;
        } else if (usageRatio<=0.70) {
            budgetScore=25;
        } else if (usageRatio<=0.85) {
            budgetScore=20;
        } else {
            budgetScore=15;
        }

        double suitabilityScore=
                meal.getSuitabilityScore() * 0.40;

        double resourceScore;

        if (meal.getCostPerStudent()<=25) {
            resourceScore=30;
        } else if (meal.getCostPerStudent()<=30) {
            resourceScore= 25;
        } else if (meal.getCostPerStudent()<=35) {
            resourceScore=20;
        } else if (meal.getCostPerStudent()<=40) {
            resourceScore=15;
        } else {
            resourceScore= 10;
        }

        return Math.round(
                (budgetScore
                        + suitabilityScore
                        + resourceScore)
                        * 100.0
        ) / 100.0;
    }

    private void validatePlanInput(
            int studentCount,
            double budget)
            throws InvalidInputException {

        if (studentCount<= 0) {
            throw new InvalidInputException(
                    "Student count must be greater than zero."
            );
        }

        if (budget<=0) {
            throw new InvalidInputException(
                    "Budget must be greater than zero."
            );
        }
    }
}