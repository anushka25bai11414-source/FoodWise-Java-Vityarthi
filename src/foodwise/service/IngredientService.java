package foodwise.service;

import foodwise.dao.IngredientDAO;
import foodwise.exception.InvalidInputException;
import foodwise.model.Ingredient;

import java.util.List;

public class IngredientService {

    private final IngredientDAO ingredientDAO;

    public IngredientService() {
        ingredientDAO = new IngredientDAO();
    }


    public boolean addIngredient(
            String name,
            double quantity,
            String unit,
            double cost)
            throws InvalidInputException {

        validateIngredient(name, quantity, unit, cost);

        Ingredient ingredient = new Ingredient(
                0,
                name,
                quantity,
                unit,
                cost
        );

        return ingredientDAO.addIngredient(ingredient);
    }


    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }


    public boolean updateQuantity(
            int ingredientId,
            double quantity)
            throws InvalidInputException {

        if (ingredientId <= 0) {
            throw new InvalidInputException(
                "Ingredient id must be greater than zero");
        }

        if (quantity < 0) {
            throw new InvalidInputException(
                    "Quantity cannot be negative");
        }

        return ingredientDAO.updateQuantity(
                ingredientId,
                quantity);
    }


    private void validateIngredient(
            String name,
            double quantity,
            String unit,
            double cost)
            throws InvalidInputException {

        if (name==null || name.trim().isEmpty()) {
            throw new InvalidInputException(
                    "Ingredient name cannot be empty");
        }

        if (quantity < 0) {
            throw new InvalidInputException(
                    "Quantity cannot be negative");
        }

        if (unit==null || unit.trim().isEmpty()) {
            throw new InvalidInputException(
                    "Unit cannot be empty");
        }

        if (cost<0) {
            throw new InvalidInputException(
                    "Cost can't be negative");
        }
    }
}
