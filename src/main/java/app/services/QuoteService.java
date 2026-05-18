package app.services;


import app.entities.Quote;
import app.persistence.QuoteMapper;

public class QuoteService {

    private QuoteMapper quoteMapper;

    public QuoteService() {

        this.quoteMapper = new QuoteMapper();
    }

/*
    public Quote updateQuote(String quoteId, String newPrice) {
        int parsedQuoteId = Integer.parseInt(quoteId);
        double parsedNewPrice = Double.parseDouble(newPrice);

        // Find gammelt tilbud
        Quote oldQuote = quoteMapper.findQuoteById(parsedQuoteId);

        // Opret ny version
        Quote newQuote = oldQuote.createNewVersion(parsedNewPrice);

        // Gem ny version
        quoteMapper.saveQuote(newQuote);

        return newQuote;
    }
*/
}
