package fr.epsi.rennes.poec.raphael.pizza.service;

import fr.epsi.rennes.poec.raphael.pizza.dao.PizzaDAO;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import fr.epsi.rennes.poec.raphael.pizza.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaDAO pizzaDAO;

    public void createPizza(Pizza pizza) throws BusinessException {
        if (pizza.getLabel() == null) {
            throw new BusinessException("pizza.label.null");
        }
        int pizzaId = pizzaDAO.createPizza(pizza.getLabel());
        for (int i = 0; i < pizza.getIngredients().size(); i++) {
            int ingredientId = pizza.getIngredients().get(i).getId();
            pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
        }
    }

    public void addIngredientToPizza(int pizzaId, int ingredientId) throws BusinessException {
        if (pizzaId < 0 || ingredientId < 0) {
            throw new BusinessException("pizza.ingredient.id.invalide");
        }
        pizzaDAO.addIngredientToPizza(pizzaId, ingredientId);
    }

    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = pizzaDAO.getAllPizzas();
        return pizzas;
    }
}
