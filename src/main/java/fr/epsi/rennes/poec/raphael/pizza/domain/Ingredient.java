package fr.epsi.rennes.poec.raphael.pizza.domain;

public class Ingredient {

    private int id;
    private String type;
    private String label;
    private int nbCalories;
    private double price;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public int getNbCalories() {
        return nbCalories;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNbCalories(int nbCalories) {
        this.nbCalories = nbCalories;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
