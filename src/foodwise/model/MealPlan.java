package foodwise.model;

import java.time.LocalDate;

public class MealPlan {
    private int planId;
    private int mealId;
    private int studentCount;
    private double budget;
    private double totalCost;
    private double remainingBudget;
    private double recommendationScore;
    private LocalDate planDate;

    public MealPlan() {
    }

    public MealPlan(int planId, int mealId, int studentCount,
                    double budget, double totalCost,
                    double remainingBudget, double recommendationScore,
                    LocalDate planDate) {
        this.planId= planId;
        this.mealId= mealId;
        this.studentCount= studentCount;
        this.budget= budget;
        this.totalCost= totalCost;
        this.remainingBudget= remainingBudget;
        this.recommendationScore= recommendationScore;
        this.planDate= planDate;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId= planId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId= mealId;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount= studentCount;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget= budget;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost= totalCost;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget= remainingBudget;
    }

    public double getRecommendationScore() {
        return recommendationScore;
    }

    public void setRecommendationScore(double recommendationScore) {
        this.recommendationScore= recommendationScore;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate= planDate;
    }

    @Override
    public String toString() {
        return "MealPlan{" +
                "planId=" + planId +
                ", studentCount=" + studentCount +
                ", totalCost=Rs." + totalCost +
                ", remainingBudget=Rs." + remainingBudget +
                ", recommendationScore=" + recommendationScore +
                ", planDate=" + planDate +
                '}';
    }
}