package app.persistence;

import app.entities.Quote;
import app.exceptions.DatabaseException;

import java.sql.*;

public class QuoteMapper {

    public int createQuote(Quote quote, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO quotes (inquiry_id, salesperson_id, length, width, price, quotation_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //RETURN_GENERATED_KEYS returnerer det autogenererede id fra databasen. Det er smart.
        ) {
            ps.setInt(1, quote.getInquiryId());
            ps.setInt(2, quote.getSalespersonId());
            ps.setInt(3, quote.getLength());
            ps.setInt(4, quote.getWidth());
            ps.setDouble(5, quote.getPrice());
            ps.setInt(6, quote.getQuotationNumber());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Tilbud kunne ikke oprettes.");
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved oprettelse af tilbud.", e.getMessage());
        }
    }

    public int getNewestQuotationNumber(ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT MAX(quotation_number) FROM quotes";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Kunne ikke finde tilbudsnummer.");
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl.", e.getMessage());
        }
    }
}