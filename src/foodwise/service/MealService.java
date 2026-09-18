package foodwise.service;

import foodwise.dao.MealDAO;
import foodwise.exception.InvalidInputException;
import foodwise.model.Meal;

import java.util.List;

public class MealService {

    private final MealDAO mealDAO;

    public MealService() {
        mealDAO = new MealDAO();
    }


    public boolean addMeal(
            String mealName,
            String mealType,
            double costPerStudent,
            int suitabilityScore)
            throws InvalidInputException {

        validateMeal(
                mealName,
                mealType,
                costPerStudent,
                suitabilityScore);

        Meal meal = new Meal(
                0,
                mealName,
                mealType,
                costPerStudent,
                suitabilityScore
        );

        return mealDAO.addMeal(meal);
    }


    public List<Meal> getAllMeals() {
        return mealDAO.getAllMeals();
    }


    private void validateMeal(
            String mealName,
            String mealType,
            double cost,
            int suitability)
            throws InvalidInputException {

        if (mealName== null || mealName.trim().isEmpty()) {
            throw new InvalidInputException(
                    "Meal name can't be empty.");
        }

        if (mealType==null || mealType.trim().isEmpty()) {
            throw new InvalidInputException(
                    "Meal type can't be empty.");
        }

        if (cost<= 0) {
            throw new InvalidInputException(
                    "meal cost should be greater than zero.");
        }

        if (suitability<0 || suitability> 100) {
            throw new InvalidInputException(
                    "suitability score should be between 0 and 100.");
        }
    }
}