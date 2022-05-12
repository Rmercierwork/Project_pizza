package fr.epsi.rennes.poec.raphael.pizza.controller;

import fr.epsi.rennes.poec.raphael.pizza.domain.Cart;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import fr.epsi.rennes.poec.raphael.pizza.domain.Response;
import fr.epsi.rennes.poec.raphael.pizza.service.CartService;
import fr.epsi.rennes.poec.raphael.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private CartService cartService;

    @GetMapping("/public/pizzas")
    public Response<List<Pizza>> getAllPizzas() {
        List<Pizza> pizzas = pizzaService.getAllPizzas();

        Response<List<Pizza>> response = new Response<>();
        response.setData(pizzas);

        return response;
    }

    @PostMapping("/public/cart/pizza")
    public Response<Integer> addPizza(
            @RequestParam int pizzaId,
            @RequestParam int cartId) {

        Pizza pizza = new Pizza();
        pizza.setId(pizzaId);

        cartId = cartService.addPizza(pizza, cartId);

        Response<Integer> response = new Response<>();
        response.setData(cartId);

        return response;
    }

    @GetMapping("/public/cart")
    public Response<Cart> getCart(
            @RequestParam int cartId) {
        Cart cart = cartService.getCartById(cartId);

        Response<Cart> response = new Response<>();
        response.setData(cart);

        return response;
    }
}
