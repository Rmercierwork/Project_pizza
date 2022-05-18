package fr.epsi.rennes.poec.raphael.pizza.service;

import fr.epsi.rennes.poec.raphael.pizza.dao.CartDAO;
import fr.epsi.rennes.poec.raphael.pizza.domain.Cart;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public int addPizza(Pizza pizza, int cartId) {
        boolean exists = cartDAO.isCartExists(cartId);
        if (!exists) {
            cartId = cartDAO.createCart();
        }
        cartDAO.addPizza(pizza, cartId);
        return cartId;
    }

    public Cart getCartById(int cartId) {
        Cart cart = cartDAO.getCartById(cartId);
        List<Pizza> pizzas = cart.getPizzas();
        double totalPrice = 0;
        for (int i = 0; i < pizzas.size(); i++) {
            double pizzaPrice = 0;
            Pizza pizza = pizzas.get(i);
            if (pizza.getIngredients() == null) {
                continue;
            }
            for (int j = 0; j < pizza.getIngredients().size(); j++) {
                pizzaPrice += pizza.getIngredients().get(j).getPrice();
            }
            pizza.setPrice(pizzaPrice);
            totalPrice += pizzaPrice;

        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    @Transactional
    public void removePizza(Pizza pizza, int cartId) {
        cartDAO.removePizza(cartId, pizza.getId());
        for (int i = 0; i < pizza.getIngredients().size(); i ++) {
            // remove ingredients
            // ce qui revient à supprimer des lignes dans la table d'association
        }
    }
}
