package app.persistence;

import app.exceptions.DatabaseException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

import app.entities.Inquiry;



public class InquiryMapper {

    public int saveInquiry(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO inquiries (customer_id, carport_length, carport_width, shed_length, shed_width) "
                + "VALUES (?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inquiry.getCustomerId());
            ps.setInt(2, inquiry.getCarportLength());
            ps.setInt(3, inquiry.getCarportWidth());
            ps.setInt(4, inquiry.getShedLength());
            ps.setInt(5, inquiry.getShedWidth());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            throw new DatabaseException("Forespørgsel kunne ikke oprettes");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl da forespørgsel skulle gemmes: " + e.getMessage());
        }

    }

    //TODO midlertidig metode fordi der skal ændres i CustomerMapper.
    //saveCustomer skal være int ikke void, da den skal returnere kundeid, der skal bruges i CustomerService
    public int getCustomerById (String email, ConnectionPool connectionPool) throws DatabaseException{
        String sql = "SELECT customer_id FROM customers WHERE email=?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("customer_id");
            } else {
                throw new DatabaseException("FEJL! Kunde oprettet men kan ikke findes i database");
            }
        }catch (SQLException e){
                 throw new DatabaseException("Fejl da kunde_id skulle hentes");

        }
    }
}
