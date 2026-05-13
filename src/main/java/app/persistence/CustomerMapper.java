package app.persistence;

import app.exceptions.DatabaseException;
import app.entities.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerMapper {

    public void saveCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into customers (first_name, last_name, address, zipcode, email) values (?, ?, ?, ?, ?)";

        try ( Connection connection = connectionPool.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, customer.getFirstname());
            ps.setString(2, customer.getLastname());
            ps.setString(3, customer.getAddress());
            ps.setInt(4, customer.getZipcode().getZipcode());
            ps.setString(5, customer.getEmail());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af kunde.");
            }
        } catch (SQLException e) {
            String msg = "Fejl ved ...";
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}
