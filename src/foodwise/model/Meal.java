package foodwise.model;

public class Meal {
    private int mealId;
    private String mealName;
    private String mealType;
    private double costPerStudent;
    private int suitabilityScore;

    public Meal() {
    }

    public Meal(int mealId, String mealName, String mealType,
                double costPerStudent, int suitabilityScore) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealType = mealType;
        this.costPerStudent = costPerStudent;
        this.suitabilityScore = suitabilityScore;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId= mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName= mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType= mealType;
    }

    public double getCostPerStudent() {
        return costPerStudent;
    }

    public void setCostPerStudent(double costPerStudent) {
        this.costPerStudent= costPerStudent;
    }

    public int getSuitabilityScore() {
        return suitabilityScore;
    }

    public void setSuitabilityScore(int suitabilityScore) {
        this.suitabilityScore= suitabilityScore;
    }

    @Override
    public String toString() {
        return mealName +
                " | Type: " + mealType +
                " | Cost/student: Rs." + costPerStudent +
                " | Suitability: " + suitabilityScore;
    }
}

