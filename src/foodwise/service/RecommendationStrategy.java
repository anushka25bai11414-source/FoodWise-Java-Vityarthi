package foodwise.service;

import foodwise.model.Meal;

import java.util.List;

public interface RecommendationStrategy {

    Meal recommendMeal(
    List<Meal> meals,
    int studentCount,
    double budget
    );
}
