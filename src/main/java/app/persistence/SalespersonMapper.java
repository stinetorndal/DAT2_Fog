package app.persistence;

import app.entities.Salesperson;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalespersonMapper {

    public Salesperson login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM salespersons WHERE email=? AND password=?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int salespersonId = rs.getInt("salesperson_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                return new Salesperson(salespersonId, firstName, lastName, email, password, role);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
    }
}
