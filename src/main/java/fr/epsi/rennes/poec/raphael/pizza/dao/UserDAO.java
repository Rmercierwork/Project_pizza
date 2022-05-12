package fr.epsi.rennes.poec.raphael.pizza.dao;

import fr.epsi.rennes.poec.raphael.pizza.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserDAO {

    @Autowired
    private DataSource ds;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) throws SQLException {
        String sql =
                "Select email, password, userrole " +
                        "FROM user " +
                        "WHERE email = '" + email + "'";
        Statement stmt = ds.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            User user = new User();
            user.setEmail(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setRole(rs.getString(3));
            return user;
        } else {
            return null;
        }
    }

    public void addUser(User user) throws SQLException {
        String password = passwordEncoder.encode(user.getPassword());
        String sql = "INSERT INTO user (email, password) VALUES ('" +
                user.getEmail() + "', '" + password + "');";

        Statement stmt = ds.getConnection().createStatement();
        stmt.executeUpdate(sql);


    }
}
