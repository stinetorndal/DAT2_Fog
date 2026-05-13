package app.persistence;

import app.exceptions.DatabaseException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

import app.entities.Inquiry;



public class InquiryMapper {

    public int saveInquiry(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO inquiries (customer_id, carport_length, carport_width, roof_type," +
                "slope_roof, siding, shed_length, shed_width) " + "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inquiry.getCustomerId());
            ps.setInt(2, inquiry.getCarportLength());
            ps.setInt(3, inquiry.getCarportWidth());
            ps.setString(4, inquiry.getRoofType());
            ps.setInt(5, inquiry.getSlopeRoof());
            ps.setString(6, inquiry.getSiding());
            ps.setInt(7, inquiry.getShedLength());
            ps.setInt(8, inquiry.getShedWidth());
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
}
