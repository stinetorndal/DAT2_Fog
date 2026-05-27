package app.persistence;

import app.exceptions.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Inquiry> getAllInquiries(ConnectionPool connectionPool) throws DatabaseException {
        List<Inquiry> allInquiries = new ArrayList<>();

        String sql = "SELECT * FROM inquiries";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int inquiryId = rs.getInt("inquiry_id");
                int customerId = rs.getInt("customer_id");
                int carportLength = rs.getInt("carport_length");
                int carportWidth = rs.getInt("carport_width");
                int shedLength = rs.getInt("shed_length");
                int shedWidth = rs.getInt("shed_width");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                // rs.getTimestamp() returnerer en java.sql.Timestamp (gammel SQL-type).
                // .toLocalDateTime() konverterer den til en moderne LocalDateTime.

                allInquiries.add(new Inquiry(inquiryId, customerId, carportLength, carportWidth, shedLength, shedWidth, date));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af forespørgsler:", e.getMessage());
        }
        return allInquiries;
    }

    public Inquiry getInquiryById(int inquiryId, ConnectionPool connectionPool) throws DatabaseException {
        Inquiry inquiry = null;

        String sql = "SELECT * FROM inquiries WHERE inquiry_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, inquiryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("inquiry_id");
                int customerId = rs.getInt("customer_id");
                int carportLength = rs.getInt("carport_length");
                int carportWidth = rs.getInt("carport_width");
                int shedLength = rs.getInt("shed_length");
                int shedWidth = rs.getInt("shed_width");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                // rs.getTimestamp() returnerer en java.sql.Timestamp (gammel SQL-type).
                // .toLocalDateTime() konverterer den til en moderne LocalDateTime.

                inquiry = new Inquiry(id, customerId, carportLength, carportWidth, shedLength, shedWidth, date);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
        return inquiry;
    }

}
