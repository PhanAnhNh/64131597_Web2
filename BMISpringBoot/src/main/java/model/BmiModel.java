package model;

public class BmiModel {
	private double height;
    private double weight;
    private double bmi;
    private String category;
    
    public BmiModel() {}

    public BmiModel(double height, double weight) {
        this.height = height;
        this.weight = weight;
        this.bmi = calculateBmi();
        this.category = categorizeBmi();
    }

    public double calculateBmi() {
        return weight / (height * height);
    }

    public String categorizeBmi() {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 24.9) return "Normal weight";
        else if (bmi < 29.9) return "Overweight";
        else return "Obesity";
    }
    
 // Getter & Setter
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getBmi() { return bmi; }
    public String getCategory() { return category; }
}
