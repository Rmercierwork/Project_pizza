package fr.epsi.rennes.poec.raphael.pizza.dao;

import fr.epsi.rennes.poec.raphael.pizza.domain.Ingredient;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import fr.epsi.rennes.poec.raphael.pizza.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PizzaDAO {

    @Autowired
    private DataSource ds;

    public int createPizza(String label) {
        String sql = "INSERT INTO pizza (label) VALUES (?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, label);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Pizza create error"));
    }

    public void addIngredientToPizza(int pizzaId, int ingredientId) {
        String sql ="INSERT INTO pizza_has_ingredient" +
                    "(pizza_id, ingredient_id) VALUES (?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pizzaId);
            ps.setInt(2, ingredientId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public List<Pizza> getAllPizzas() {
        String sql ="SELECT " +
                    "pizza.id as pizzaId, " +
                    "pizza.label as pizzaLabel, "+
                    "GROUP_CONCAT(ingredient.id) AS ingredients "+
                    "FROM pizza " +
                    "RIGHT JOIN pizza_has_ingredient ON pizza_has_ingredient.pizza_id = pizza.id " +
                    "LEFT JOIN ingredient ON pizza_has_ingredient.ingredient_id = ingredient.id " +
                    "GROUP BY pizza.id ; ";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            List<Pizza> pizzas = new ArrayList<>();
            while (rs.next()) {
                Pizza pizza = new Pizza();
                String ingredients = rs.getString("ingredients");

                List<Ingredient> ingredientsList = new ArrayList<>();
                for (String ingredient : ingredients.split(",")) {
                    Ingredient ingredientPojo = new Ingredient();
                    ingredientPojo.setId(Integer.parseInt(ingredient));
                    ingredientsList.add(ingredientPojo);

                }
                pizza.setIngredients(ingredientsList);
                pizza.setId(rs.getInt("pizzaId"));
                pizza.setLabel(rs.getString("pizzaLabel"));
                pizzas.add(pizza);
            }
            return pizzas;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}
