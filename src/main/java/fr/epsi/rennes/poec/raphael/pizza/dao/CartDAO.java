package fr.epsi.rennes.poec.raphael.pizza.dao;

import fr.epsi.rennes.poec.raphael.pizza.domain.Cart;
import fr.epsi.rennes.poec.raphael.pizza.domain.Pizza;
import fr.epsi.rennes.poec.raphael.pizza.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Repository
public class CartDAO {

    @Autowired
    private DataSource ds;

    public void addPizza(Pizza pizza, int cartId) {
        String sql = "INSERT INTO cart_has_pizza " +
                     "(cart_id, pizza_id) VALUES (?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps =conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, pizza.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public boolean isCartExists(int panierId) {
        String sql ="SELECT id FROM cart WHERE id = ?";
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, panierId);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public int createCart() {
        String sql ="INSERT INTO cart (date) VALUES (?)";
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS)) {

            String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            ps.setString(1, date);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        throw new TechnicalException(new SQLException("Cart creation error"));
    }

    public Cart getCartById(int cartId) {
        String sql ="SELECT " +
                    "cart.id as cartId, " +
                    "cart.date as cartDate, " +
                    "GROUP_CONCAT(pizza.id) AS pizzas " +
                    "FROM cart " +
                    "RIGHT JOIN cart_has_pizza ON cart_has_pizza.cart_id = cart.id " +
                    "LEFT JOIN pizza ON cart_has_pizza.pizza_id = pizza.id " +
                    "WHERE cart.id = ? " +
                    "GROUP BY cart.id";
        try (Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("cartId"));

                cart.setPizzas(new ArrayList<>());
                String pizzas = rs.getString("pizzas");

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

    public void removePizza(int cartId, int id) {

    }
}
