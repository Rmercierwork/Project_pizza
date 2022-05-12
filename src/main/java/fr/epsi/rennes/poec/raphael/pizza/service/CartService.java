package fr.epsi.rennes.poec.raphael.pizza.service;

import fr.epsi.rennes.poec.raphael.pizza.dao.CartDAO;
import fr.epsi.rennes.poec.raphael.pizza.domain.Cart;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return cartDAO.getCartById(cartId);
    }
}
