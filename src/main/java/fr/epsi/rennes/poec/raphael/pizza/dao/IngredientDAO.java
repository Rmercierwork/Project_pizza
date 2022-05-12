package fr.epsi.rennes.poec.raphael.pizza.dao;

import fr.epsi.rennes.poec.raphael.pizza.domain.Ingredient;
import fr.epsi.rennes.poec.raphael.pizza.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientDAO {

    @Autowired
    private DataSource ds;

    public List<Ingredient> getAllIngredients() {
        String sql = "SELECT * FROM ingredient";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            List<Ingredient> ingredients = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setLabel(rs.getString("label"));
                ingredient.setType(rs.getString("type"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setNbCalories(rs.getInt("nbCalories"));
                ingredients.add(ingredient);
            }
            return ingredients;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}
