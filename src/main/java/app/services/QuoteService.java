package app.services;


import app.entities.Quote;
import app.persistence.QuoteMapper;

public class QuoteService {

    private QuoteMapper quoteMapper;

    public QuoteService() {

        this.quoteMapper = new QuoteMapper();
    }


    public Quote updateQuote(int quoteId, double newPrice) {

        // Find gammelt tilbud
        Quote oldQuote = quoteMapper.findQuoteById(quoteId);

        // Opret ny version
        Quote newQuote = oldQuote.createNewVersion(newPrice);

        // Gem ny version
        quoteMapper.saveQuote(newQuote);

        return newQuote;
    }

}
