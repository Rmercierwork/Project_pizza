package fr.epsi.rennes.poec.raphael.pizza.service;

import fr.epsi.rennes.poec.raphael.pizza.dao.IngredientDAO;
import fr.epsi.rennes.poec.raphael.pizza.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientDAO ingredientDAO;

    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }
}
