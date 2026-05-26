package app.persistence;

import app.entities.Quote;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    // Henter alle tilbud ud til sælgeren
    public List<Quote> getAllQuotes(ConnectionPool connectionPool) throws DatabaseException {
        List<Quote> quotesList = new ArrayList<>();

        // JOIN for at hente kundenavn
        String sql = "SELECT q.*, (c.first_name || ' ' || c.last_name) AS customer_name " +
                "FROM public.quotes q " +
                "JOIN public.inquiries i ON q.inquiry_id = i.inquiry_id " +
                "JOIN public.customers c ON i.customer_id = c.customer_id " +
                "ORDER BY q.quotation_id DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Quote quote = createQuoteObject(rs);
                quotesList.add(quote);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl. Kunne ikke hente tilbud: " + e.getMessage());
        }
        return quotesList;
    }

    private Quote createQuoteObject(ResultSet rs) throws SQLException {
        return new Quote(
                rs.getInt("quotation_id"),
                rs.getInt("inquiry_id"),
                rs.getInt("salesperson_id"),
                rs.getInt("length"),
                rs.getInt("width"),
                rs.getDouble("price"),
                rs.getString("status"),
                rs.getInt("quotation_number"),
                rs.getInt("version"),
                rs.getString("customer_name")
        );
    }

    public Quote getQuoteById(int quotationId, ConnectionPool connectionPool) throws DatabaseException {
        Quote quote = null;

        String sql = "SELECT * FROM quotes WHERE quotation_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, quotationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int inquiryId = rs.getInt("inquiry_id");
                int salespersonId = rs.getInt("salesperson_id");
                int length = rs.getInt("length");
                int width = rs.getInt("width");
                double price = rs.getDouble("price");

                quote = new Quote(inquiryId, salespersonId, length, width, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
        return quote;
    }
}