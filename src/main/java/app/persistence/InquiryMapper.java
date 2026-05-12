package app.persistence;

import app.app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InquiryMapper {

    public static void saveInquiry(int inquiry_id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO inquiries (customer_id, carport_length, carport_width, roof_type," +
                "slope_roof, siding, shed_length, shed_width) " + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inquiry_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af ordre: " + e.getMessage());
        }

    }
}
