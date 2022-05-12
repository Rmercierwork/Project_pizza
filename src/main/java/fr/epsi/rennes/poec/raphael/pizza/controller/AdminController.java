package fr.epsi.rennes.poec.raphael.pizza.controller;

import fr.epsi.rennes.poec.raphael.pizza.domain.Ingredient;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import fr.epsi.rennes.poec.raphael.pizza.domain.Response;
import fr.epsi.rennes.poec.raphael.pizza.exception.BusinessException;
import fr.epsi.rennes.poec.raphael.pizza.service.IngredientService;
import fr.epsi.rennes.poec.raphael.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/admin/pizza/create")
    public Response<Void> createPizza(@RequestBody Pizza pizza) throws BusinessException {

        pizzaService.createPizza(pizza);
        return new Response<>();
    }

    @PostMapping("/admin/pizza/ingredient")
    public Response<Void> addIngredientToPizza(
            @RequestParam int pizzaId,
            @RequestParam int ingredientId) throws BusinessException {

        pizzaService.addIngredientToPizza(pizzaId, ingredientId);
        return new Response<>();
    }

    @GetMapping("/admin/pizzas")
    public Response<List<Pizza>> getAllPizzas() {
        List<Pizza> pizzas = pizzaService.getAllPizzas();

        Response<List<Pizza>> response = new Response<>();
        response.setData(pizzas);

        return response;
    }

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/admin/ingredients")
    public Response<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        Response<List<Ingredient>> response = new Response<>();
        response.setData(ingredients);

        return response;
    }
}
