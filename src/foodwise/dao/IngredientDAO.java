package foodwise.dao;

import foodwise.model.Ingredient;
import foodwise.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {

    public boolean addIngredient(Ingredient ingredient) {

        String sql="""
            INSERT INTO ingredients
            (name,quantity_available,unit,cost_per_unit)
            VALUES(?,?,?,?)
            """;

        try (Connection connection=DatabaseManager.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql)) {

            statement.setString(1, ingredient.getName());
            statement.setDouble(2, ingredient.getQuantityAvailable());
            statement.setString(3, ingredient.getUnit());
            statement.setDouble(4, ingredient.getCostPerUnit());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding ingredient: "+ e.getMessage());
            return false;
        }
    }


    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredients=new ArrayList<>();

        String sql= "SELECT * FROM ingredients ORDER BY ingredient_id";

        try (Connection connection=DatabaseManager.getConnection();
             Statement statement=connection.createStatement();
             ResultSet resultSet=statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Ingredient ingredient=new Ingredient();

                ingredient.setIngredientId(
                        resultSet.getInt("ingredient_id"));

                ingredient.setName(
                        resultSet.getString("name"));

                ingredient.setQuantityAvailable(
                        resultSet.getDouble("quantity_available"));

                ingredient.setUnit(
                        resultSet.getString("unit"));

                ingredient.setCostPerUnit(
                        resultSet.getDouble("cost_per_unit"));

                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            System.out.println(
                    "error retrieving ingredients: " + e.getMessage());
        }

        return ingredients;
    }


    public boolean updateQuantity(int ingredientId, double quantity) {

        String sql= """
                UPDATE ingredients
                SET quantity_available = ?
                WHERE ingredient_id = ?
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, quantity);
            statement.setInt(2, ingredientId);

            return statement.executeUpdate() > 0;

        } catch
            (SQLException e) {
            System.out.println(
            "error updating ingredient: "+ e.getMessage());
            return false;
        }
    }
}
