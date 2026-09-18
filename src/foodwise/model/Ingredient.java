package foodwise.model;

public class Ingredient {
    private int ingredientId;
    private String name;
    private double quantityAvailable;
    private String unit;
    private double costPerUnit;

    public Ingredient() {
    }

    public Ingredient(int ingredientId, String name, double quantityAvailable,
                      String unit, double costPerUnit) {
        this.ingredientId= ingredientId;
        this.name= name;
        this.quantityAvailable= quantityAvailable;
        this.unit= unit;
        this.costPerUnit= costPerUnit;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId= ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public double getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(double quantityAvailable) {
        this.quantityAvailable= quantityAvailable;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit= unit;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit= costPerUnit;
    }

    @Override
    public String toString() {
        return name+ "-" +quantityAvailable+ " " +unit;
    }
}

