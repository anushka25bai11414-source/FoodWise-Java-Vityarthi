package foodwise.service;

import foodwise.model.Meal;

import java.util.List;

public class RecommendationEngine
        implements RecommendationStrategy {

    @Override
    public Meal recommendMeal(
            List<Meal> meals,
            int studentCount,
            double budget) {

        if (meals==null || meals.isEmpty()) {
            return null;
        }

        Meal bestMeal=null;
        double bestScore= -1;

        for (Meal meal: meals) {

            double totalCost =
                    meal.getCostPerStudent() * studentCount;

            // Meal can't be recommended if exceeds budget
            if (totalCost>budget) {
                continue;
            }

            // 30% budget suitability
            double budgetScore=
                    calculateBudgetScore(
                            totalCost,
                            budget);

            // 40% student suitability
            double suitabilityScore =
                    meal.getSuitabilityScore() * 0.40;

            // 30% resource efficiency(meal cost per student)
            double resourceScore =
                    calculateResourceScore(
                            meal.getCostPerStudent());

            double finalScore =
                    budgetScore
                    + suitabilityScore
                    + resourceScore;

            if (finalScore>bestScore) {

                bestScore= finalScore;
                bestMeal= meal;
            }
        }

        return bestMeal;
    }


    private double calculateBudgetScore(
            double totalCost,
            double budget) {

        if (budget<= 0) {
            return 0;
        }

        double usageRatio= totalCost/budget;

        if (usageRatio<=0.50) {
            return 30;
        }

        if (usageRatio<=0.70) {
            return 25;
        }

        if (usageRatio<=0.85) {
            return 20;
        }

        if (usageRatio<=1.00) {
            return 15;
        }

        return 0;
    }


    private double calculateResourceScore(
            double costPerStudent) {

        if (costPerStudent<=25) {
            return 30;
        }

        if (costPerStudent<=30) {
            return 25;
        }

        if (costPerStudent<=35) {
            return 20;
        }

        if (costPerStudent<=40) {
            return 15;
        }

        return 10;
    }
}