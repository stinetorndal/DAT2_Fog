package app.services;

import app.entities.Inquiry;
import app.entities.Quote;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.QuoteMapper;

import java.util.List;


public class QuoteService {
    private QuoteMapper quoteMapper = new QuoteMapper();

    public int createQuote(Quote quote, ConnectionPool connectionPool) throws DatabaseException {
        int newestQuotationNumber = quoteMapper.getNewestQuotationNumber(connectionPool);
        quote.setQuotationNumber(newestQuotationNumber+1);
        return quoteMapper.createQuote(quote, connectionPool);
    }

    public List<Quote> handleAllQuotes(ConnectionPool connectionPool) throws DatabaseException {
        return quoteMapper.getAllQuotes(connectionPool);
    }
}