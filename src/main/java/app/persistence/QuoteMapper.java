package app.persistence;
import app.entities.Inquiry;
import app.entities.Quote;
import app.enums.QuoteStatus;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuoteMapper {

    public void saveQuote(Quote quote, ConnectionPool connectionPool) {
        String sql = "INSERT INTO quotes (quotation_id, inquiry_id, salesperson_id, price, status, version) "
                + "VALUES (?,?,?,?,?,?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, quote.getQuotationId());
            ps.setInt(2, quote.getInquiryId());
            ps.setInt(3, quote.getSalespersonId());
            ps.setDouble(4, quote.getPrice());
            ps.setString(5, quote.getStatus());
            ps.setInt(6, quote.getVersion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new DatabaseException("Tilbud kunne ikke gemmes");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl da tilbud skulle gemmes: " + e.getMessage());
        }

        //Skal bruge SQL statements her for at gemme i DB


    }

    public Quote findQuoteById(int quotationId, ConnectionPool connectionPool) throws DatabaseException {
        Quote quote = null;

        String sql = "SELECT * FROM quotes WHERE quotation_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, quotationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("quotation_id");
                int inquiryId = rs.getInt("inquiry_id");
                int salespersonId = rs.getInt("salesperson_id");
                int price = rs.getInt("price");
                QuoteStatus status = rs.getString("status");
                int version = rs.getInt("version");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                // rs.getTimestamp() returnerer en java.sql.Timestamp (gammel SQL-type).
                // .toLocalDateTime() konverterer den til en moderne LocalDateTime.

                quote = new Quote(id, inquiryId, salespersonId, price, status, version, date);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl: ", e.getMessage());
        }
        return quote;
    }


    public List<Quote> getAllQuotes(ConnectionPool connectionPool) throws DatabaseException {
        List<Quote> allQuotes = new ArrayList<>();

        String sql = "SELECT * FROM quotes";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int quotationId = rs.getInt("quotation_id");
                int inquiryId = rs.getInt("inquiry_id");
                int salespersonId = rs.getInt("salesperson_id");
                int price = rs.getInt("price");
                String status = rs.getString("status");
                int version = rs.getInt("version");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                // rs.getTimestamp() returnerer en java.sql.Timestamp (gammel SQL-type).
                // .toLocalDateTime() konverterer den til en moderne LocalDateTime.

                allQuotes.add(new Quote(quotationId, inquiryId, salespersonId, price, status, version, date));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af forespørgsler:", e.getMessage());
        }
        return allQuotes;
    }
}
