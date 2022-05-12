package fr.epsi.rennes.poec.raphael.pizza.domain;

import java.util.List;

public class Order {

    private String number;
    private String clientEmail;
    private List<Pizza> pizzas;
    private double ETPrice;
    private double TIPrice;

    public String getNumber() {
        return number;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public double getETPrice() {
        return ETPrice;
    }

    public double getTIPrice() {
        return TIPrice;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void setETPrice(double ETPrice) {
        this.ETPrice = ETPrice;
    }

    public void setTIPrice(double TIPrice) {
        this.TIPrice = TIPrice;
    }
}
