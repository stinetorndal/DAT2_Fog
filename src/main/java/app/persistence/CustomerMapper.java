package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.Customer;

import java.sql.*;

public class CustomerMapper {

    public int saveCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO customers (first_name, last_name, address, zipcode, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, customer.getFirstname());
            ps.setString(2, customer.getLastname());
            ps.setString(3, customer.getAddress());
            ps.setInt(4, customer.getZipcode().getZipcode()); //Første getZipcode() henter Zipcode-objekt fra kunden, anden getZipcode() henter zipcode fra Zipcode-objekt.
            ps.setString(5, customer.getEmail());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Fejl ved oprettelse af kunde.");
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke forbinde til databasen.", e.getMessage());
        }
    }
}
