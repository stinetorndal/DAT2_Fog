package app.persistence;

import app.entities.Quote;
import app.entities.Zipcode;
import app.exceptions.DatabaseException;
import app.entities.Customer;
import app.services.ZipcodeService;

import java.sql.*;

public class CustomerMapper {
    private ZipcodeService zipcodeService = new ZipcodeService();

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

    public Customer getCustomerById(int customerId, ConnectionPool connectionPool) throws DatabaseException {
        Customer customer = null;

        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("customer_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String address = rs.getString("address");
                int zipcode = rs.getInt("zipcode");
                String email = rs.getString("email");

                Zipcode zipcodeObject = zipcodeService.getZipcodeObject(zipcode, connectionPool);
                customer = new Customer(id, firstName, lastName, address, zipcodeObject, email);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
        return customer;
    }
}
