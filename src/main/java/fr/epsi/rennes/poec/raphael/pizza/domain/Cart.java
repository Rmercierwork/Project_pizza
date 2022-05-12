package fr.epsi.rennes.poec.raphael.pizza.domain;

import java.util.List;

public class Cart {

    private int id;
    private String userEmail;
    private List<Pizza> pizzas;
    private int totalCalories;
    private double totalPrice;

    public int getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
