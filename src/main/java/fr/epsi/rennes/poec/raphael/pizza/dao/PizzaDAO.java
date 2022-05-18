package fr.epsi.rennes.poec.raphael.pizza.dao;

import fr.epsi.rennes.poec.raphael.pizza.domain.Cart;
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

    public Pizza getPizzaById(int pizzaId) {
        String sql ="SELECT" +
                        "pizza.id as id, " +
                        "pizza.label as label, " +
                        "GROUP_CONCAT(ingredient.id, ':', ingredient.label, ':' ingredient.price) as ingredients " +
                    "FROM pizza " +
                    "JOIN pizza_has_ingredient " +
                    "ON pizza_has_ingredient.pizza_id = pizza.id " +
                    "JOIN ingredient " +
                    "ON ingredient.id = pizza_has_ingredient.ingredient_id " +
                    "WHERE pizza.id = ? " +
                    "GROUP BY pizza.id";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pizzaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pizza pizza = new Pizza();
                pizza.setId(rs.getInt("id"));
                pizza.setLabel(rs.getString("label"));
                pizza.setIngredients(new ArrayList<>());

                String ingredientsString = rs.getString("ingredients");
                if (ingredientsString != null && ingredientsString.length() > 0) {
                    String[] ingredientsTab = ingredientsString.split(",");
                    for (String ingredient : ingredientsTab) {
                        String[] colonnes = ingredient.split("\\:");
                        Ingredient ingredientPojo = new Ingredient();
                        ingredientPojo.setId(Integer.parseInt(colonnes[0]));
                        ingredientPojo.setLabel(colonnes[1]);
                        ingredientPojo.setPrice(Double.parseDouble(colonnes[2]));

                        pizza.getIngredients().add(ingredientPojo) ;
                    }
                } return pizza;
            } return null;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public Cart getCartById(int cartId) {
        String sql = "select " +
                "cart.id as panierId, " +
                "cart.date as panierDate, " +
                "group_concat(pizza.id) as pizzas " +
                "from cart " +
                "right join cart_has_pizza " +
                "on cart_has_pizza.cart_id = cart.id " +
                "left join pizza " +
                "on cart_has_pizza.pizza_id = pizza.id " +
                "where cart.id = ? " +
                "group by cart.id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("cartId"));

                cart.setPizzas(new ArrayList<>());
                String pizzas = rs.getString("pizzas");
                if (pizzas == null || pizzas.length() == 0) {
                    return cart;
                }

                for (String pizzaId : pizzas.split(",")) {
                    Pizza pizza = new Pizza();
                    pizza.setId(Integer.parseInt(pizzaId));

                    cart.getPizzas().add(pizza);
                }
                return cart;
            }
            return null;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}
