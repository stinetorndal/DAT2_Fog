package app.services;

import app.entities.Inquiry;
import app.entities.Quote;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.QuoteMapper;

import java.util.List;


public class QuoteService {
    private QuoteMapper quoteMapper = new QuoteMapper();

    public List<Quote> handleAllQuotes(ConnectionPool connectionPool) throws DatabaseException {
        return quoteMapper.getAllQuotes(connectionPool);
    }
}

