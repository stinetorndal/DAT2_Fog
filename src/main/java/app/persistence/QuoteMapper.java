package app.persistence;

import app.entities.Quote;
import app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuoteMapper {

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
}