package fr.epsi.rennes.poec.raphael.pizza.domain;

import java.util.List;
import java.util.Map;

public class Pizza {

    private int id;
    private String label;
    private double price;
    private List<Ingredient> ingredients;
    private int nbCalories;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public double getPrice() {
        return price;
    }

    public List< Ingredient> getIngredients() {
        return ingredients;
    }

    public int getNbCalories() {
        return nbCalories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setNbCalories(int nbCalories) {
        this.nbCalories = nbCalories;
    }
}
